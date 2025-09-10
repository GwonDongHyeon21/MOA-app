package org.moa.moa

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.moa.moa.navigation.calendar.calendarNavigationGraph
import org.moa.moa.navigation.home.homeNavigationGraph
import org.moa.moa.navigation.record.recordNavigationGraph
import org.moa.moa.navigation.sign.SignNavigationItem
import org.moa.moa.navigation.sign.signNavigationGraph
import org.moa.moa.navigation.todo.todoNavigationGraph
import org.moa.moa.navigation.user.userNavigationGraph
import org.moa.moa.platform.backhandler.BackStackHandler
import org.moa.moa.presentation.component.MOABottomBar
import org.moa.moa.presentation.component.MOATopBar
import org.moa.moa.presentation.ui.theme.MOAColorScheme

@Composable
fun MOAApp() {
    MaterialTheme(colorScheme = MOAColorScheme()) {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = navBackStackEntry?.destination?.route

        var signUpTabIndex by remember { mutableIntStateOf(1) }

        BackStackHandler(currentScreen != null) { navController.popBackStack() }

        Scaffold(
            topBar = {
                MOATopBar(
                    currentScreen = currentScreen,
                    selectedTabIndex = signUpTabIndex,
                    onTabIndexMinus = { signUpTabIndex -= 1 },
                    onBack = { navController.popBackStack() }
                )
            },
            bottomBar = { MOABottomBar(navController, currentScreen) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = SignNavigationItem.OnBoarding.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                signNavigationGraph(navController, signUpTabIndex) { signUpTabIndex++ }
                homeNavigationGraph(navController)
                calendarNavigationGraph(navController)
                recordNavigationGraph(navController)
                todoNavigationGraph(navController)
                userNavigationGraph(navController)
            }
        }
    }
}