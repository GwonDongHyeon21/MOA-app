package org.moa.moa.navigation

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import org.moa.moa.navigation.screen.OnBoarding
import org.moa.moa.navigation.screen.Screen

class Navigator {
    private val _navigationStack = mutableStateListOf("")
    val currentScreen get() = derivedStateOf { _navigationStack.last() }
    val currentScreenSize get() = derivedStateOf { _navigationStack.size }

    init {
        replaceTo(OnBoarding)
//        replaceTo(if (token.isEmpty()) Screen.SignIn else Screen.College)
    }

    fun navigateTo(screen: Screen) {
        _navigationStack.add(screen.route)
    }

    fun goBack() {
        if (_navigationStack.size > 1)
            _navigationStack.removeAt(_navigationStack.size - 1)
    }

    fun replaceTo(screen: Screen) {
        _navigationStack[_navigationStack.size - 1] = screen.route
    }

    fun resetTo(screen: Screen) {
        _navigationStack.clear()
        _navigationStack.add(screen.route)
    }

    fun resetAndNavigateTo(popUpScreen: Screen, screen: Screen) {
        _navigationStack.clear()
        _navigationStack.add((popUpScreen.route))
        if (popUpScreen != screen)
            _navigationStack.add(screen.route)
    }
}