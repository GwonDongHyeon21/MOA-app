package org.moa.moa.presentation.record

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.cancel
import moa.presentation.generated.resources.record_text_background_left
import moa.presentation.generated.resources.record_text_background_right
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.moa.moa.presentation.component.MOABackTopBar
import org.moa.moa.presentation.component.MOAButton
import org.moa.moa.presentation.component.MOAFloatingActionButton
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING1
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING2
import org.moa.moa.presentation.ui.theme.CORNER_RADIUS
import org.moa.moa.presentation.ui.theme.GRAY2
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.WHITE
import org.moa.moa.presentation.ui.theme.transparent
import org.moa.moa.util.rememberImagePicker

@Composable
fun RecordScreen(
    onClickBack: () -> Unit,
    viewModel: RecordViewModel = koinInject(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val imagePicker = rememberImagePicker {
        viewModel.changeImageBytes(it)
    }

    Scaffold(
        topBar = {
            MOABackTopBar(
                modifier = Modifier,
                onClickBack = { onClickBack() }
            )
        },
        bottomBar = {
            MOAButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = APP_HORIZONTAL_PADDING1),
                text = Strings.record,
                enabled = uiState.recordText.isNotBlank()
            ) {
                viewModel.addRecordData()
            }
        },
        floatingActionButton = {
            MOAFloatingActionButton(
                modifier = Modifier,
                imageBytes = uiState.imageBytes
            ) {
                imagePicker.pickImage()
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            RecordBackgroundSection(
                modifier = Modifier,
                innerPadding = innerPadding
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter)
                    .padding(top = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = Strings.record_text_guidline,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
                )

                Spacer(modifier = Modifier.height(50.dp))
                RecordInputSection(
                    modifier = Modifier,
                    uiState = uiState,
                    imageBytes = uiState.imageBytes,
                    onValueChange = { viewModel.changeRecordText(it) },
                    onImageClick = { imagePicker.pickImage() },
                    onImageCancel = { viewModel.changeImageBytes(null) }
                )
            }
        }
    }
}

@Composable
fun RecordBackgroundSection(
    modifier: Modifier,
    innerPadding: PaddingValues,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = innerPadding.calculateTopPadding())
    ) {
        Image(
            painter = painterResource(Res.drawable.record_text_background_left),
            contentDescription = null,
            modifier = modifier.align(Alignment.TopStart)
        )
        Image(
            painter = painterResource(Res.drawable.record_text_background_right),
            contentDescription = null,
            modifier = modifier.align(Alignment.TopEnd)
        )
    }
}

@Composable
fun RecordInputSection(
    modifier: Modifier,
    uiState: RecordUiState,
    imageBytes: ByteArray?,
    onValueChange: (String) -> Unit,
    onImageClick: () -> Unit,
    onImageCancel: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .border(
                0.dp,
                transparent,
                RoundedCornerShape(
                    topStart = CORNER_RADIUS,
                    topEnd = CORNER_RADIUS
                )
            )
            .padding(horizontal = APP_HORIZONTAL_PADDING2)
            .padding(bottom = 4.dp)
            .shadow(
                4.dp,
                RoundedCornerShape(
                    topStart = CORNER_RADIUS,
                    topEnd = CORNER_RADIUS
                )
            )
            .background(WHITE)
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        imageBytes?.let {
            Box {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxHeight(0.3f)
                        .clickable { onImageClick() }
                )
                Icon(
                    painter = painterResource(Res.drawable.cancel),
                    contentDescription = "CancelImage",
                    modifier = modifier
                        .align(Alignment.TopEnd)
                        .padding(5.dp)
                        .clickable(
                            onClick = { onImageCancel() },
                            interactionSource = null,
                            indication = null
                        ),
                    tint = WHITE
                )
            }
        }
        OutlinedTextField(
            value = uiState.recordText,
            onValueChange = { if (it.length < 500) onValueChange(it) },
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(4.dp),
            placeholder = {
                Text(
                    text = Strings.text_input_placeholder,
                    color = GRAY2
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = transparent,
                unfocusedContainerColor = transparent,
                focusedIndicatorColor = transparent,
                unfocusedIndicatorColor = transparent
            )
        )
    }
}