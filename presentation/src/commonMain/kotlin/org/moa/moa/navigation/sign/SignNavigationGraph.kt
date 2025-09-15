package org.moa.moa.navigation.sign

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.moa.moa.navigation.home.HomeNavigationItem
import org.moa.moa.presentation.sign.OnBoardingScreen
import org.moa.moa.presentation.sign.SignUpScreen

fun NavGraphBuilder.signNavigationGraph(navController: NavController) {
    composable(SignNavigationItem.OnBoarding.route) {
        OnBoardingScreen(
            onNavigateToSignUp = { navController.navigate(SignNavigationItem.SignUp.route) },
            onNavigateToHome = { navController.navigate(HomeNavigationItem.Home.route) }
        )
    }
    composable(SignNavigationItem.SignUp.route) {
        SignUpScreen(
            onClickPopBack = { navController.popBackStack() },
            onNavigateToHome = { navController.navigate(HomeNavigationItem.Home.route) }
        )
    }
    composable(SignNavigationItem.SignIn.route) {
        //
    }
}