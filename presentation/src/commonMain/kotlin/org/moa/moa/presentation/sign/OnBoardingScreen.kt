package org.moa.moa.presentation.sign

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.google_logo
import moa.presentation.generated.resources.onboarding_image1
import moa.presentation.generated.resources.onboarding_image2
import moa.presentation.generated.resources.onboarding_image3
import moa.presentation.generated.resources.onboarding_text1
import moa.presentation.generated.resources.onboarding_text2
import org.jetbrains.compose.resources.painterResource
import org.moa.moa.presentation.component.MOAErrorScreen
import org.moa.moa.presentation.component.MOALoadingScreen
import org.moa.moa.presentation.sign.component.SignButton
import org.moa.moa.presentation.ui.theme.Strings

@Composable
fun OnBoardingScreen(
    viewModel: SignUpViewModel,
    onNavigateToSignUp: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    val uiState by viewModel.onBoardingState.collectAsState()

    DisposableEffect(Unit) {
        onDispose { viewModel.setupOnBoarding() }
    }

    when (uiState) {
        OnBoardingScreenState.ONBOARDING -> OnBoardingScreen(
            onGoogleLoginClick = { viewModel.googleLogin() },
        )

        OnBoardingScreenState.SIGNUP -> onNavigateToSignUp()
        OnBoardingScreenState.HOME -> onNavigateToHome()
        OnBoardingScreenState.LOADING -> MOALoadingScreen(modifier = Modifier)
        OnBoardingScreenState.ERROR -> MOAErrorScreen(modifier = Modifier)
    }
}

@Composable
private fun OnBoardingScreen(onGoogleLoginClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 80.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Image(
                    painter = painterResource(Res.drawable.onboarding_image1),
                    contentDescription = "onboarding_image1",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.weight(1f)
                )
                Row(modifier = Modifier.weight(1f)) {
                    Spacer(modifier = Modifier.width(20.dp))
                    Image(
                        painter = painterResource(Res.drawable.onboarding_text1),
                        contentDescription = "onboarding_text1",
                        contentScale = ContentScale.Fit
                    )
                }
                Row(modifier = Modifier.weight(1f)) {
                    Spacer(modifier = Modifier.width(30.dp))
                    Image(
                        painter = painterResource(Res.drawable.onboarding_image3),
                        contentDescription = "onboarding_image3",
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Column {
                Spacer(modifier = Modifier.height(50.dp))
                Image(
                    painter = painterResource(Res.drawable.onboarding_image2),
                    contentDescription = "onboarding_image2",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.weight(1f)
                )
                Image(
                    painter = painterResource(Res.drawable.onboarding_text2),
                    contentDescription = "onboarding_text2",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(50.dp))
        Column(
            modifier = Modifier.padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            SignButton(
                loginOptionText = "${Strings.google} ${Strings.login}",
                imageRes = Res.drawable.google_logo,
                onClick = { onGoogleLoginClick() }
            )
        }
    }
}