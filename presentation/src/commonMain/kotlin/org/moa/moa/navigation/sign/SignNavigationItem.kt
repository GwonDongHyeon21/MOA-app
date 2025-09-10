package org.moa.moa.navigation.sign

sealed class SignNavigationItem(val route: String) {
    data object OnBoarding : SignNavigationItem("onboarding")
    data object SignIn : SignNavigationItem("sign_in")
    data object SignUp : SignNavigationItem("sign_up")
}

