package org.moa.moa.presentation.record.recorder

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.mic_fill
import moa.presentation.generated.resources.pause
import moa.presentation.generated.resources.record_start
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.moa.moa.presentation.component.MOABackTopBar
import org.moa.moa.presentation.component.MOAErrorScreen
import org.moa.moa.presentation.component.MOALoadingScreen
import org.moa.moa.presentation.record.RecordDimens.RECORD_TIME_DELAY
import org.moa.moa.presentation.record.RecordDimens.bottomPadding
import org.moa.moa.presentation.record.RecordDimens.recordGuideText
import org.moa.moa.presentation.record.RecordDimens.recorderButton
import org.moa.moa.presentation.record.RecordDimens.recorderStopButton
import org.moa.moa.presentation.record.RecordDimens.topPadding
import org.moa.moa.presentation.record.component.RecordSuccessScreen
import org.moa.moa.presentation.record.recorder.component.RecordVisualizer
import org.moa.moa.presentation.record.recorder.component.RecorderBackgroundSection
import org.moa.moa.presentation.record.recorder.component.VisualizerDimens.MAX_BAR_HEIGHT
import org.moa.moa.presentation.record.recorder.model.RecordMode
import org.moa.moa.presentation.record.recorder.model.RecorderState
import org.moa.moa.presentation.record.recorder.platform.AppSetting
import org.moa.moa.presentation.record.recorder.platform.PermissionStatus
import org.moa.moa.presentation.record.recorder.platform.rememberPermissionState
import org.moa.moa.presentation.record.recorder.platform.rememberRecorderController
import org.moa.moa.presentation.ui.theme.GRAY1
import org.moa.moa.presentation.ui.theme.GRAY2
import org.moa.moa.presentation.ui.theme.RED
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.WHITE
import org.moa.moa.presentation.ui.theme.transparent
import org.moa.moa.util.formatRecordTime

@Composable
fun RecorderScreen(
    onBack: () -> Unit,
    viewModel: RecorderViewModel = koinInject(),
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState.screenState) {
        RecorderState.RECORD -> RecorderScreen(
            recordMode = uiState.recordMode,
            onBack = { onBack() },
            onStartRecord = { viewModel.startRecord() },
            onPauseRecord = { viewModel.pauseRecord() },
            onStopRecord = { viewModel.stopRecord(it) }
        )

        is RecorderState.PLAYING -> RecorderPlayingScreen(
            recordPath = state.recordFile,
            onBack = { onBack() },
            onSaveRecord = { viewModel.saveRecord() },
            onRecordState = { viewModel.resetRecord() }
        )

        RecorderState.SUCCESS -> RecordSuccessScreen { onBack() }
        RecorderState.LOADING -> MOALoadingScreen(Modifier)
        RecorderState.ERROR -> MOAErrorScreen(Modifier)
    }
}

@Composable
private fun RecorderScreen(
    recordMode: RecordMode,
    onBack: () -> Unit,
    onStartRecord: () -> Unit,
    onPauseRecord: () -> Unit,
    onStopRecord: (String) -> Unit,
) {
    val micPermission = rememberPermissionState()
    val recorderController = rememberRecorderController()
    val recordState = recorderController.state.value

    var showPermissionDialog by remember { mutableStateOf(false) }
    var isAppSetting by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        when (micPermission.status) {
            PermissionStatus.Granted -> Unit
            PermissionStatus.Denied, PermissionStatus.ShouldShowRationale -> {
                micPermission.launchPermissionRequest {
                    showPermissionDialog = it
                }
            }
        }
    }

    if (showPermissionDialog) {
        PermissionDialog(
            onAppSetting = { isAppSetting = true },
            onBack = {
                showPermissionDialog = false
                onBack()
            }
        )
    }

    AppSetting(isAppSetting) { isAppSetting = false }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_STOP -> {
                    recorderController.pause()
                    onPauseRecord()
                }

                Lifecycle.Event.ON_DESTROY -> recorderController.release()
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            recorderController.release()
        }
    }

    Scaffold(
        topBar = {
            MOABackTopBar(
                modifier = Modifier,
                onBack = { onBack() }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            RecorderBackgroundSection(modifier = Modifier)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = topPadding, bottom = bottomPadding),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = Strings.recording_guidline1,
                        fontSize = recordGuideText,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = Strings.recording_guidline2,
                        fontSize = recordGuideText,
                        fontWeight = FontWeight.SemiBold,
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    RecordTime(
                        isRecording = recordState.isRecording,
                        recordTime = recordState.totalRecordMs
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RecordVisualizer(
                        modifier = Modifier.height(MAX_BAR_HEIGHT),
                        isTicking = recordState.isRecording && !recordState.isPaused,
                        currentLevel = recordState.amplitude
                    )

                    Spacer(modifier = Modifier.height(90.dp))
                    RecorderButtonSection(
                        modifier = Modifier,
                        recordMode = recordMode,
                        onStartRecord = {
                            recorderController.start()
                            onStartRecord()
                        },
                        onPauseRecord = {
                            recorderController.pause()
                            onPauseRecord()
                        },
                        onResumeRecord = {
                            recorderController.resume()
                            onStartRecord()
                        },
                        onStopRecord = {
                            recorderController.stop()
                            recordState.filePath?.let { onStopRecord(it) }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RecordTime(
    isRecording: Boolean,
    recordTime: Long,
) {
    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(isRecording) {
        if (isRecording) {
            while (isActive) {
                delay(RECORD_TIME_DELAY)
                visible = !visible
            }
        } else {
            visible = false
        }
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(if (visible && isRecording) RED else transparent)
        )

        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = formatRecordTime(recordTime),
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            color = if (isRecording) GRAY1 else transparent
        )
    }
}

@Composable
fun RecorderButtonSection(
    modifier: Modifier,
    recordMode: RecordMode,
    onStartRecord: () -> Unit,
    onPauseRecord: () -> Unit,
    onResumeRecord: () -> Unit,
    onStopRecord: () -> Unit,
) {
    val (text, textColor) = when (recordMode) {
        RecordMode.DEFAULT -> Pair(Strings.start, GRAY1)
        RecordMode.START -> Pair(Strings.pause, GRAY2)
        RecordMode.PAUSE -> Pair(Strings.start, GRAY1)
    }
    val (onClickListener, recordButtonImage) = when (recordMode) {
        RecordMode.DEFAULT -> Pair(onStartRecord, Res.drawable.mic_fill)
        RecordMode.START -> Pair(onPauseRecord, Res.drawable.pause)
        RecordMode.PAUSE -> Pair(onResumeRecord, Res.drawable.record_start)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text, fontSize = 15.sp, color = textColor)

        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.fillMaxWidth(0.8f)) {
            Button(
                onClick = { onClickListener() },
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(recorderButton),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = GRAY1)
            ) {
                Image(
                    painter = painterResource(recordButtonImage),
                    contentDescription = "RecordButton",
                )
            }

            if (recordMode != RecordMode.DEFAULT) {
                Button(
                    onClick = { onStopRecord() },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(recorderStopButton),
                    colors = ButtonDefaults.buttonColors(containerColor = WHITE),
                    border = BorderStroke(1.dp, GRAY2)
                ) {
                    Box(
                        modifier = Modifier
                            .size(19.dp)
                            .aspectRatio(1f)
                            .background(GRAY2, RectangleShape)
                    )
                }
            }
        }
    }
}

@Composable
fun PermissionDialog(
    onAppSetting: () -> Unit,
    onBack: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onBack() },
        title = { Text(text = Strings.mic_permission) },
        text = { Text(text = Strings.mic_permission_guideline) },
        confirmButton = {
            TextButton(onClick = {
                onAppSetting()
                onBack()
            }) {
                Text(text = Strings.setting)
            }
        },
        dismissButton = {
            TextButton(onClick = { onBack() }) {
                Text(text = Strings.cancel)
            }
        }
    )
}