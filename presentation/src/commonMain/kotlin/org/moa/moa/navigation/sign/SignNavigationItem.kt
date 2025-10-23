package org.moa.moa.navigation.sign

sealed class SignNavigationItem(val route: String) {
    data object OnBoarding : SignNavigationItem("onboarding")
    data object SignUp : SignNavigationItem("sign_up")
}

