package org.moa.moa.navigation.sign

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.moa.moa.navigation.home.HomeNavigationItem
import org.moa.moa.presentation.sign.OnBoardingScreen
import org.moa.moa.presentation.sign.SignUpScreen

fun NavGraphBuilder.signNavigationGraph(
    navController: NavController,
    signUpTabIndex: Int,
    onTabIndexPlus: () -> Int,
) {
    composable(SignNavigationItem.OnBoarding.route) {
        OnBoardingScreen(
            onNavigateToSignUp = { navController.navigate(SignNavigationItem.SignUp.route) },
            onNavigateToHome = { navController.navigate(HomeNavigationItem.Home.route) }
        )
    }
    composable(SignNavigationItem.SignUp.route) {
        SignUpScreen(
            signUpTabIndex = signUpTabIndex,
            onTabIndexPlus = { onTabIndexPlus() }
        )
    }
    composable(SignNavigationItem.SignIn.route) {
        //
    }
}