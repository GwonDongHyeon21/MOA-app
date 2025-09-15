package org.moa.moa.presentation.sign

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.moa.moa.platform.backhandler.BackStackHandler
import org.moa.moa.presentation.UiState
import org.moa.moa.presentation.component.MOABackTopBar
import org.moa.moa.presentation.component.MOAErrorScreen
import org.moa.moa.presentation.component.MOALoadingScreen
import org.moa.moa.presentation.sign.component.BirthDateInput
import org.moa.moa.presentation.sign.component.GenderInput
import org.moa.moa.presentation.sign.component.UserIdInput
import org.moa.moa.presentation.ui.theme.APP_VERTICAL_PADDING2
import org.moa.moa.presentation.ui.theme.IVORY
import org.moa.moa.presentation.ui.theme.MAIN
import org.moa.moa.presentation.ui.theme.Strings

@Composable
fun SignUpScreen(
    onClickPopBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: SignUpViewModel = koinInject(),
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState.screenState) {
        UiState.SUCCESS -> SignUpScreen(
            uiState = uiState,
            onSignUp = { viewModel.signUp { onNavigateToHome() } },
            onUserIdChanged = { viewModel.userIdChange(it) },
            onBirthDateChanged = { viewModel.birthDateChange(it) },
            onGenderChanged = { viewModel.genderChange(it) },
            onClickPopBack = { onClickPopBack() }
        )

        UiState.LOADING -> MOALoadingScreen(Modifier)
        UiState.ERROR -> MOAErrorScreen(Modifier)
    }
}

@Composable
private fun SignUpScreen(
    uiState: SignUpUiState,
    onSignUp: () -> Unit,
    onUserIdChanged: (String) -> Unit,
    onBirthDateChanged: (String) -> Unit,
    onGenderChanged: (Gender) -> Unit,
    onClickPopBack: () -> Unit,
) {
    var signUpTabIndex by remember { mutableIntStateOf(1) }

    BackStackHandler { if (signUpTabIndex > 1) signUpTabIndex-- else onClickPopBack() }

    Scaffold(
        topBar = {
            MOABackTopBar(
                modifier = Modifier,
                onClickBack = { if (signUpTabIndex > 1) signUpTabIndex-- else onClickPopBack() },
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    when (signUpTabIndex) {
                        1 -> if (uiState.userId.isNotEmpty()) signUpTabIndex++
                        2 -> if (uiState.birthDate.isNotEmpty()) signUpTabIndex++
                        3 -> uiState.gender?.let { onSignUp() }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MAIN)
            ) {
                Text(text = Strings.continueText)
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LinearProgressIndicator(
                progress = { signUpTabIndex.toFloat() / 3 },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .padding(horizontal = APP_VERTICAL_PADDING2)
                    .clip(RoundedCornerShape(100.dp)),
                color = MAIN,
                trackColor = IVORY,
                gapSize = 0.dp,
                drawStopIndicator = {}
            )

            AnimatedContent(
                targetState = signUpTabIndex,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(1000)
                        ) togetherWith slideOutHorizontally(targetOffsetX = { -it })
                    } else {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(1000)
                        ) togetherWith slideOutHorizontally(targetOffsetX = { it })
                    }
                },
                label = "AnimatedContent"
            ) { targetState ->
                when (targetState) {
                    1 -> UserIdInput(uiState.userId) { onUserIdChanged(it) }
                    2 -> BirthDateInput(uiState.birthDate) { onBirthDateChanged(it) }
                    3 -> GenderInput(uiState.gender) { onGenderChanged(it) }
                }
            }
        }
    }
}