package org.moa.moa.presentation.sign

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.moa.moa.navigation.Navigator
import org.moa.moa.presentation.sign.component.BirthDateInput
import org.moa.moa.presentation.sign.component.GenderInput
import org.moa.moa.presentation.sign.component.SignUpTopBar
import org.moa.moa.presentation.sign.component.UserIdInput
import org.moa.moa.presentation.ui.theme.MAIN
import org.moa.moa.presentation.ui.theme.Strings

@Composable
fun SignUpScreen(
    navigator: Navigator,
    innerPadding: PaddingValues,
) {
    val viewModel = koinInject<SignUpViewModel>()
    val isLoading by viewModel.isLoading.collectAsState()

    var selectedTabIndex by remember { mutableIntStateOf(1) }
    var userId by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.padding(innerPadding).padding(bottom = 32.dp),
        topBar = {
            SignUpTopBar(selectedTabIndex) {
                if (selectedTabIndex > 1) selectedTabIndex -= 1
                else navigator.goBack()
            }
        },
        bottomBar = {
            Button(
                onClick = {
                    when (selectedTabIndex) {
                        1 -> if (userId.isNotEmpty()) selectedTabIndex += 1
                        2 -> if (birthDate.isNotEmpty()) selectedTabIndex += 1
                        3 -> if (gender != 0) viewModel.signUp(userId, birthDate, gender)
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MAIN)
            ) {
                Text(text = Strings.continueText)
            }
        }
    ) { signUpInnerPadding ->
        if (isLoading)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        else
            Column(modifier = Modifier.padding(signUpInnerPadding)) {
                AnimatedContent(
                    targetState = selectedTabIndex,
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
                        1 -> UserIdInput(userId) { userId = it }
                        2 -> BirthDateInput(birthDate) { birthDate = it }
                        3 -> GenderInput(gender) { gender = it }
                    }
                }
            }
    }
}