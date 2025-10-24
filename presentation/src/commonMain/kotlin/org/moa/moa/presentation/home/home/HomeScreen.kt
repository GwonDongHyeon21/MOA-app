package org.moa.moa.presentation.home.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moa.domain.model.response.Diary
import com.moa.domain.model.response.RecordItem
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.home_record_guide
import moa.presentation.generated.resources.home_record_holder
import moa.presentation.generated.resources.home_record_logo
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.moa.moa.presentation.UiState
import org.moa.moa.presentation.component.MOAErrorScreen
import org.moa.moa.presentation.component.MOALoadingScreen
import org.moa.moa.presentation.home.home.HomeDimens.buttonHeight
import org.moa.moa.presentation.home.home.HomeDimens.buttonRoundedCornerShape
import org.moa.moa.presentation.home.home.HomeDimens.buttonWidth
import org.moa.moa.presentation.home.home.HomeDimens.verticalPadding
import org.moa.moa.presentation.home.home.component.PixelClickImage
import org.moa.moa.presentation.home.home.model.ImageInfo
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING1
import org.moa.moa.presentation.ui.theme.BOTTOM_PADDING_CENTER
import org.moa.moa.presentation.ui.theme.Strings

private object HomeDimens {
    val verticalPadding = 20.dp
    val buttonRoundedCornerShape = RoundedCornerShape(58.dp)
    val buttonWidth = 222.dp
    val buttonHeight = 54.dp
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinInject(),
    onNavigateToHomeDiary: () -> Unit,
    onNavigateToHomeDetail: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState.screenState) {
        UiState.DEFAULT -> Unit
        UiState.SUCCESS -> HomeScreen(
            diary = uiState.diary,
            todayRecords = uiState.todayRecords,
            recordImages = uiState.recordImages,
            onNavigateToHomeDiary = { onNavigateToHomeDiary() },
            onNavigateToHomeDetail = { recordNumber -> onNavigateToHomeDetail(recordNumber) }
        )

        UiState.LOADING -> MOALoadingScreen(Modifier)
        UiState.ERROR -> MOAErrorScreen(Modifier)
    }
}

@Composable
private fun HomeScreen(
    diary: Diary?,
    todayRecords: List<RecordItem>,
    recordImages: List<ImageInfo>,
    onNavigateToHomeDiary: () -> Unit,
    onNavigateToHomeDetail: (Int) -> Unit,
) {
    diary?.let {
        onNavigateToHomeDiary()
    } ?: run {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = APP_HORIZONTAL_PADDING1)
                .padding(bottom = BOTTOM_PADDING_CENTER + verticalPadding * 3),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeRecordImagesSection(
                modifier = Modifier.fillMaxWidth(),
                todayRecords = todayRecords,
                recordImages = recordImages,
                onSelectedNumber = { recordNumber -> onNavigateToHomeDetail(recordNumber) }
            )

            Button(
                onClick = { onNavigateToHomeDiary() },
                modifier = Modifier.size(buttonWidth, buttonHeight),
                shape = buttonRoundedCornerShape,
                enabled = todayRecords.isNotEmpty()
            ) {
                Text(
                    text = Strings.make_record,
                    fontSize = 17.sp
                )
            }
        }
    }
}

@Composable
fun HomeRecordImagesSection(
    modifier: Modifier,
    todayRecords: List<RecordItem>,
    recordImages: List<ImageInfo>,
    onSelectedNumber: (Int) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (todayRecords.isEmpty()) {
            Box(modifier = modifier) {
                Image(
                    painter = painterResource(Res.drawable.home_record_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 20.dp)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .fillMaxWidth(0.5f)
                        .padding(start = 5.dp)
                ) {
                    Image(
                        painter = painterResource(Res.drawable.home_record_guide),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                }
            }
        } else {
            Box(modifier = modifier) {
                recordImages
                    .takeLast(todayRecords.size)
                    .forEachIndexed { index, image ->
                        PixelClickImage(
                            image = imageResource(image.drawableRes),
                            modifier = Modifier
                                .size(image.size)
                                .align(image.alignment)
                                .offset(image.offset.x.dp, image.offset.y.dp),
                            onClick = { onSelectedNumber(index) }
                        )
                    }
            }
        }
        Image(
            painter = painterResource(Res.drawable.home_record_holder),
            contentDescription = null,
            modifier = modifier.offset(y = (-20).dp)
        )
    }
}