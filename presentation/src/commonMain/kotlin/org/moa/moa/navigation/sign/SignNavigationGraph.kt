package org.moa.moa.navigation.sign

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.moa.moa.navigation.home.HomeNavigationItem
import org.moa.moa.presentation.sign.OnBoardingScreen
import org.moa.moa.presentation.sign.SignUpScreen
import org.moa.moa.presentation.sign.SignUpViewModel

fun NavGraphBuilder.signNavigationGraph(
    navController: NavController,
    viewModel: SignUpViewModel,
) {
    composable(SignNavigationItem.OnBoarding.route) {
        OnBoardingScreen(
            viewModel = viewModel,
            onNavigateToSignUp = { navController.navigate(SignNavigationItem.SignUp.route) },
            onNavigateToHome = {
                navController.navigate(HomeNavigationItem.Home.route) {
                    popUpTo(SignNavigationItem.OnBoarding.route) { inclusive = true }
                }
            }
        )
    }
    composable(SignNavigationItem.SignUp.route) {
        SignUpScreen(
            viewModel = viewModel,
            onNavigateToHome = { navController.navigate(HomeNavigationItem.Home.route) },
            onBack = { navController.popBackStack() }
        )
    }
}