package org.moa.moa.presentation.record.recorder

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.pause
import moa.presentation.generated.resources.record_start
import moa.presentation.generated.resources.redo
import moa.presentation.generated.resources.trash_light
import org.jetbrains.compose.resources.painterResource
import org.moa.moa.presentation.component.MOABackTopBar
import org.moa.moa.presentation.record.RecordDimens.recordGuideText
import org.moa.moa.presentation.record.RecordDimens.roundedCornerShape
import org.moa.moa.presentation.record.recorder.RecorderPlayingDimens.bottomPadding
import org.moa.moa.presentation.record.recorder.RecorderPlayingDimens.playButton
import org.moa.moa.presentation.record.recorder.RecorderPlayingDimens.saveButton
import org.moa.moa.presentation.record.recorder.RecorderPlayingDimens.shadow
import org.moa.moa.presentation.record.recorder.RecorderPlayingDimens.topPadding
import org.moa.moa.presentation.record.recorder.component.RecordVisualizer
import org.moa.moa.presentation.record.recorder.component.RecorderBackgroundSection
import org.moa.moa.presentation.record.recorder.component.VisualizerDimens.MAX_BAR_HEIGHT
import org.moa.moa.presentation.record.recorder.platform.PlayerState
import org.moa.moa.presentation.record.recorder.platform.rememberPlayerController
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING2
import org.moa.moa.presentation.ui.theme.BLACK
import org.moa.moa.presentation.ui.theme.GRAY1
import org.moa.moa.presentation.ui.theme.GRAY3
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.WHITE
import org.moa.moa.presentation.ui.theme.transparent
import org.moa.moa.util.formatRecordTime
import kotlin.math.sin

object RecorderPlayingDimens {
    val playButton = 61.dp
    val saveButton = 54.dp
    val topPadding = 30.dp
    val bottomPadding = 24.dp
    val shadow = 3.dp
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecorderPlayingScreen(
    recordPath: String,
    onBack: () -> Unit,
    onSaveRecord: () -> Unit,
    onRecordState: () -> Unit,
) {
    val playerController = rememberPlayerController()
    val playerState = playerController.state.value
    val currentPlayTime = playerState.currentPlayMs
    val totalPlayTime = playerState.totalPlayMs

    var isUserSeeking by remember { mutableStateOf(false) }
    var sliderPosMs by remember { mutableStateOf(0L) }

    LaunchedEffect(currentPlayTime, totalPlayTime) {
        if (!isUserSeeking) {
            sliderPosMs = currentPlayTime
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            playerController.close()
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

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = APP_HORIZONTAL_PADDING2)
                    .padding(bottom = 180.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .padding(shadow)
                    .shadow(shadow, RoundedCornerShape(20.dp), spotColor = BLACK.copy(alpha = 0.1f))
                    .background(WHITE)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = topPadding, bottom = bottomPadding),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = Strings.playing_guidline1,
                        fontSize = recordGuideText,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = Strings.playing_guidline2,
                        fontSize = recordGuideText,
                        fontWeight = FontWeight.SemiBold,
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = formatRecordTime(currentPlayTime))
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    RecordVisualizer(
                        modifier = Modifier
                            .height(MAX_BAR_HEIGHT)
                            .padding(top = 20.dp),
                        isTicking = playerState.isPlaying,
                        currentLevel = playerState.amplitude
                    )

                    Spacer(modifier = Modifier.height(30.dp))
                    Slider(
                        value = sliderPosMs.toFloat(),
                        onValueChange = {
                            isUserSeeking = true
                            sliderPosMs = it.toLong()
                        },
                        modifier = Modifier
                            .height(0.dp)
                            .padding(horizontal = APP_HORIZONTAL_PADDING2 + 30.dp),
                        valueRange = 0f..totalPlayTime.toFloat(),
                        onValueChangeFinished = {
                            playerController.seekTo(sliderPosMs)
                            isUserSeeking = false
                        },
                        track = { sliderState ->
                            SliderDefaults.Track(
                                sliderState = sliderState,
                                modifier = Modifier.height(5.dp),
                                colors = SliderDefaults.colors(
                                    activeTrackColor = BLACK,
                                    inactiveTrackColor = GRAY3,
                                    activeTickColor = transparent,
                                    inactiveTickColor = transparent
                                )
                            )
                        },
                        thumb = {
                            SliderDefaults.Thumb(
                                interactionSource = remember { MutableInteractionSource() },
                                modifier = Modifier.size(10.dp),
                                colors = SliderDefaults.colors(thumbColor = BLACK)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = APP_HORIZONTAL_PADDING2 + 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = formatRecordTime(currentPlayTime),
                            fontSize = 12.sp,
                            color = GRAY1
                        )
                        Text(
                            text = formatRecordTime(totalPlayTime),
                            fontSize = 12.sp,
                            color = GRAY1
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        PlayButtonSection(
                            modifier = Modifier,
                            playerState = playerState,
                            onLoadPlaying = { playerController.load(recordPath, autoPlay = true) },
                            onPausePlaying = { playerController.pause() },
                            onPlay = { playerController.play() },
                        )

                        Spacer(modifier = Modifier.height(30.dp))
                        SaveButton(
                            modifier = Modifier,
                            onBack = { onBack() },
                            onSaveRecord = { onSaveRecord() },
                            onRecordState = { onRecordState() },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlayButtonSection(
    modifier: Modifier,
    playerState: PlayerState,
    onLoadPlaying: () -> Unit,
    onPausePlaying: () -> Unit,
    onPlay: () -> Unit,
) {
    val playButtonImage = when {
        playerState.isPlaying -> Res.drawable.pause
        else -> Res.drawable.record_start
    }
    Button(
        onClick = {
            if (!playerState.isLoaded) {
                onLoadPlaying()
            } else if (playerState.isPlaying) {
                onPausePlaying()
            } else {
                onPlay()
            }
        },
        modifier = Modifier.size(playButton),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = GRAY1)
    ) {
        Image(
            painter = painterResource(playButtonImage),
            contentDescription = "PlayButton",
        )
    }
}

@Composable
fun SaveButton(
    modifier: Modifier,
    onBack: () -> Unit,
    onSaveRecord: () -> Unit,
    onRecordState: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = APP_HORIZONTAL_PADDING2 + 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.clickable { onBack() },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(Res.drawable.trash_light),
                contentDescription = "delete_record"
            )
            Text(
                text = Strings.delete,
                fontSize = 12.sp
            )
        }

        Button(
            onClick = { onSaveRecord() },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(saveButton),
            shape = roundedCornerShape
        ) {
            Text(
                text = Strings.record,
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Column(
            modifier = Modifier.clickable { onRecordState() },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(Res.drawable.redo),
                contentDescription = "redo_record"
            )
            Text(
                text = Strings.redo,
                fontSize = 12.sp
            )
        }
    }
}