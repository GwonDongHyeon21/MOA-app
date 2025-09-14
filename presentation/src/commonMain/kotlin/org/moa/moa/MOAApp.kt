package org.moa.moa

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.calendar_button_icon
import moa.presentation.generated.resources.home_button_icon
import moa.presentation.generated.resources.main_button_icon
import moa.presentation.generated.resources.my_button_icon
import moa.presentation.generated.resources.todo_button_icon
import org.moa.moa.navigation.Navigator
import org.moa.moa.navigation.screen.Calendar
import org.moa.moa.navigation.screen.Home
import org.moa.moa.navigation.screen.MainButton
import org.moa.moa.navigation.screen.OnBoarding
import org.moa.moa.navigation.screen.SignIn
import org.moa.moa.navigation.screen.SignUp
import org.moa.moa.navigation.screen.Todo
import org.moa.moa.navigation.screen.User
import org.moa.moa.presentation.calendar.CalendarScreen
import org.moa.moa.presentation.component.BottomBar
import org.moa.moa.presentation.home.HomeScreen
import org.moa.moa.presentation.sign.OnBoardingScreen
import org.moa.moa.presentation.sign.SignUpScreen
import org.moa.moa.presentation.todo.TodoScreen
import org.moa.moa.presentation.ui.theme.MOAColorScheme
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.user.UserScreen
import org.moa.moa.platform.backhandler.PopBackStackHandler

@Composable
fun MOAApp() {
    MaterialTheme(
        colorScheme = MOAColorScheme(),
//        typography = MOATypography()
    ) {
        val navigator = remember { Navigator() }
        val currentScreen by navigator.currentScreen
        val currentScreenSize by navigator.currentScreenSize

        val bottomItems = listOf(
            Triple(Strings.home, Res.drawable.home_button_icon, Home),
            Triple(Strings.calendar, Res.drawable.calendar_button_icon, Calendar),
            Triple(Strings.moa, Res.drawable.main_button_icon, MainButton),
            Triple(Strings.todo, Res.drawable.todo_button_icon, Todo),
            Triple(Strings.user, Res.drawable.my_button_icon, User),
        )
        var tabScreen by remember { mutableStateOf(bottomItems.first().third.route) }

        val isBottomBar = currentScreen !in listOf(
            OnBoarding.route,
            SignIn.route,
            SignUp.route
        )

        LaunchedEffect(currentScreen) {
            if (bottomItems.any { it.third.route == currentScreen })
                tabScreen = currentScreen
        }

        if (currentScreenSize > 1) PopBackStackHandler { navigator.goBack() }

        Scaffold(
            bottomBar = { if (isBottomBar) BottomBar(bottomItems, navigator, tabScreen) }
        ) { innerPadding ->
            when (currentScreen) {
                OnBoarding.route -> OnBoardingScreen(navigator)
                SignUp.route -> SignUpScreen(navigator, innerPadding)
                Home.route -> HomeScreen(navigator, innerPadding)
                Calendar.route -> CalendarScreen(navigator, innerPadding)
                Todo.route -> TodoScreen(navigator, innerPadding)
                User.route -> UserScreen(navigator, innerPadding)
            }
        }
    }
}