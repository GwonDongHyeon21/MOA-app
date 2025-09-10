package org.moa.moa.navigation.sign

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.moa.moa.presentation.record.RecordRecordScreen
import org.moa.moa.presentation.sign.OnBoardingScreen
import org.moa.moa.presentation.sign.SignUpScreen

fun NavGraphBuilder.signNavigationGraph(
    navController: NavController,
    selectedTabIndex: Int,
    onTabIndexPlus: () -> Int,
) {
    composable(SignNavigationItem.OnBoarding.route) {
        OnBoardingScreen(navController)
    }
    composable(SignNavigationItem.SignUp.route) {
        SignUpScreen(
            navController = navController,
            selectedTabIndex = selectedTabIndex,
            onTabIndexPlus = { onTabIndexPlus() }
        )
    }
    composable(SignNavigationItem.SignIn.route) {
        //
    }
}