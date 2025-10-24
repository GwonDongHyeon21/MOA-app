package org.moa.moa.presentation.home.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.moa.domain.model.response.RecordItem
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.disk_shape
import moa.presentation.generated.resources.star
import moa.presentation.generated.resources.top_logo
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.moa.moa.presentation.component.MOABackTopBar
import org.moa.moa.presentation.component.MOAButton
import org.moa.moa.presentation.component.MOADialog
import org.moa.moa.presentation.component.MOAErrorScreen
import org.moa.moa.presentation.component.MOALoadingScreen
import org.moa.moa.presentation.home.detail.HomeDetailDimens.backgroundRoundedCornerShape
import org.moa.moa.presentation.home.detail.HomeDetailDimens.bottomPadding
import org.moa.moa.presentation.home.detail.HomeDetailDimens.topPadding
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING1
import org.moa.moa.presentation.ui.theme.BOTTOM_PADDING_CENTER
import org.moa.moa.presentation.ui.theme.GRAY1
import org.moa.moa.presentation.ui.theme.GRAY3
import org.moa.moa.presentation.ui.theme.GRAY4
import org.moa.moa.presentation.ui.theme.MAIN
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.WHITE
import org.moa.moa.presentation.util.formatDateTime
import org.moa.moa.presentation.util.recordTypeToString

private object HomeDetailDimens {
    val topPadding = 30.dp
    val bottomPadding = BOTTOM_PADDING_CENTER + 20.dp
    val backgroundRoundedCornerShape = RoundedCornerShape(15.dp)
}

@Composable
fun HomeDetailScreen(
    recordNumber: Int,
    viewModel: HomeDetailViewModel = koinInject(),
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadRecord(recordNumber)
    }

    when (uiState.screenState) {
        HomeDetailScreenState.SUCCESS -> HomeDetailScreen(
            record = uiState.record,
            onBack = { onBack() },
            onEditRecord = { }, //viewModel.editRecord() },
            onDeleteRecord = { viewModel.deleteRecord() },
        )

        HomeDetailScreenState.DELETE_SUCCESS -> onBack()
        HomeDetailScreenState.LOADING -> MOALoadingScreen(modifier = Modifier)
        HomeDetailScreenState.ERROR -> MOAErrorScreen(modifier = Modifier)
    }
}

@Composable
private fun HomeDetailScreen(
    record: RecordItem?,
    onBack: () -> Unit,
    onEditRecord: () -> Unit,
    onDeleteRecord: () -> Unit,
) {
    Scaffold(
        topBar = {
            MOABackTopBar(
                modifier = Modifier,
                onBack = { onBack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = APP_HORIZONTAL_PADDING1)
                .padding(top = topPadding, bottom = bottomPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            record?.let {
                HomeDetailTimeImageSection(
                    modifier = Modifier,
                    record = it
                )

                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(WHITE, backgroundRoundedCornerShape)
                        .padding(vertical = 24.dp, horizontal = 48.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        it.imageUrl?.let { url ->
                            AsyncImage(
                                model = url,
                                contentDescription = "record_image",
                                modifier = Modifier
                                    .fillMaxWidth(0.4f)
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(15.dp))
                                    .background(GRAY4)
                                    .border(1.dp, GRAY3, RoundedCornerShape(5.dp)),
                                placeholder = painterResource(Res.drawable.top_logo),
                                error = painterResource(Res.drawable.top_logo)
                            )
                        }
                        Text(
                            text = it.content,
                            modifier = Modifier.verticalScroll(rememberScrollState())
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            HomeDetailButtonSection(
                modifier = Modifier,
                onEditRecord = { onEditRecord() },
                onDeleteRecord = { onDeleteRecord() }
            )
        }
    }
}

@Composable
fun HomeDetailTimeImageSection(
    modifier: Modifier,
    record: RecordItem,
) {
    val timeText = formatDateTime(
        dateTime = record.dataToLocalDateTime,
        pattern = Strings.date_time_format
    )

    Text(
        text = timeText,
        fontSize = 17.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .border(2.dp, MAIN, RoundedCornerShape(100.dp))
            .background(WHITE)
            .padding(vertical = 8.dp, horizontal = 24.dp)
    )

    Spacer(modifier = Modifier.height(15.dp))
    Text(
        text = recordTypeToString(record.type),
        color = GRAY1,
        fontSize = 15.sp
    )

    Spacer(modifier = Modifier.height(15.dp))
    Box(modifier = modifier) {
        Image(
            painter = painterResource(Res.drawable.disk_shape),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(100.dp)
                .align(Alignment.BottomCenter)
                .offset(y = (-3).dp)
        )
        Image(
            painter = painterResource(Res.drawable.star),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    }
}

@Composable
fun HomeDetailButtonSection(
    modifier: Modifier,
    onEditRecord: () -> Unit,
    onDeleteRecord: () -> Unit,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MOAButton(
            modifier = Modifier.weight(1f),
            text = Strings.edit_record,
            onClick = { onEditRecord() },
        )
        MOAButton(
            modifier = Modifier.weight(1f),
            text = Strings.delete_record,
            buttonColor = GRAY4,
            onClick = { showDeleteDialog = true },
        )
    }

    if (showDeleteDialog) {
        MOADialog(
            title = Strings.delete_record,
            text = Strings.delete_record_guideline,
            confirmText = Strings.delete,
            dismissText = Strings.cancel,
            onClickConfirm = { onDeleteRecord() },
            onClickDismiss = { showDeleteDialog = false }
        )
    }
}