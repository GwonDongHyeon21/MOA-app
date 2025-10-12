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
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.home_record_guide
import moa.presentation.generated.resources.home_record_holder
import moa.presentation.generated.resources.home_record_logo
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.moa.moa.presentation.UiState
import org.moa.moa.presentation.component.MOAErrorScreen
import org.moa.moa.presentation.component.MOALoadingScreen
import org.moa.moa.presentation.home.home.HomeDimens.buttonHeight
import org.moa.moa.presentation.home.home.HomeDimens.buttonRoundedCornerShape
import org.moa.moa.presentation.home.home.HomeDimens.buttonWidth
import org.moa.moa.presentation.home.home.HomeDimens.verticalPadding
import org.moa.moa.presentation.record.model.Record
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
    onNavigateToHomeRecord: () -> Unit,
    onNavigateToHomeDetail: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState.screenState) {
        UiState.DEFAULT -> Unit
        UiState.SUCCESS -> HomeScreen(
            record = uiState.record,
            onNavigateToHomeRecord = { onNavigateToHomeRecord() },
            onNavigateToHomeDetail = { onNavigateToHomeDetail() }
        )

        UiState.LOADING -> MOALoadingScreen(Modifier)
        UiState.ERROR -> MOAErrorScreen(Modifier)
    }
}

@Composable
private fun HomeScreen(
    record: Record?,
    onNavigateToHomeRecord: () -> Unit,
    onNavigateToHomeDetail: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = APP_HORIZONTAL_PADDING1)
            .padding(bottom = BOTTOM_PADDING_CENTER + verticalPadding * 3),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeRecordsSection(
            modifier = Modifier,
            record = record
        )

        Button(
            onClick = { onNavigateToHomeRecord() },
            modifier = Modifier.size(buttonWidth, buttonHeight),
            shape = buttonRoundedCornerShape,
            enabled = record != null
        ) {
            Text(
                text = Strings.make_record,
                fontSize = 17.sp
            )
        }
    }
}

@Composable
fun HomeRecordsSection(
    modifier: Modifier,
    record: Record?,
) {
    Column {
        if (record == null) {
            Box(modifier = Modifier.fillMaxWidth()) {
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
            Box {

            }
        }
        Image(
            painter = painterResource(Res.drawable.home_record_holder),
            contentDescription = null,
            modifier = Modifier.offset(y = (-20).dp)
        )
    }
}