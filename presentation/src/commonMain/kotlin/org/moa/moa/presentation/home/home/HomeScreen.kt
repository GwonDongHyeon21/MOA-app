package org.moa.moa.presentation.home.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import org.koin.compose.koinInject
import org.moa.moa.presentation.UiState
import org.moa.moa.presentation.component.MOAErrorScreen
import org.moa.moa.presentation.component.MOALoadingScreen
import org.moa.moa.presentation.home.HomeDimens.buttonHeight
import org.moa.moa.presentation.home.HomeDimens.buttonRoundedCornerShape
import org.moa.moa.presentation.home.HomeDimens.buttonWidth
import org.moa.moa.presentation.home.HomeDimens.verticalPadding
import org.moa.moa.presentation.record.model.Record
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING1
import org.moa.moa.presentation.ui.theme.BOTTOM_PADDING_CENTER
import org.moa.moa.presentation.ui.theme.Strings

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
            .padding(bottom = BOTTOM_PADDING_CENTER + verticalPadding * 2),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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