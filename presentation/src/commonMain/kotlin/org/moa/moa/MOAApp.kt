package org.moa.moa

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.moa.moa.navigation.calendar.calendarNavigationGraph
import org.moa.moa.navigation.home.HomeNavigationItem
import org.moa.moa.navigation.home.homeNavigationGraph
import org.moa.moa.navigation.record.recordNavigationGraph
import org.moa.moa.navigation.sign.SignNavigationItem
import org.moa.moa.navigation.sign.signNavigationGraph
import org.moa.moa.navigation.todo.todoNavigationGraph
import org.moa.moa.navigation.user.userNavigationGraph
import org.moa.moa.presentation.component.MOABottomBar
import org.moa.moa.presentation.component.MOALoadingScreen
import org.moa.moa.presentation.sign.SignUpViewModel
import org.moa.moa.presentation.ui.theme.MOAColorScheme
import org.moa.moa.util.TokenUtils

@Composable
fun MOAApp(viewModel: SignUpViewModel) {
    MaterialTheme(colorScheme = MOAColorScheme()) {
        val startDestination by produceState<String?>(initialValue = null) {
            value = if (TokenUtils.getAccessToken().isNullOrBlank()) {
                SignNavigationItem.OnBoarding.route
            } else {
                HomeNavigationItem.Home.route
            }
        }

        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = navBackStackEntry?.destination?.route

        startDestination?.let {
            Scaffold(
                bottomBar = { MOABottomBar(navController, currentScreen) }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = it,
                    modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
                ) {
                    signNavigationGraph(navController, viewModel)
                    homeNavigationGraph(navController)
                    calendarNavigationGraph(navController)
                    recordNavigationGraph(navController)
                    todoNavigationGraph(navController)
                    userNavigationGraph(navController)
                }
            }
        } ?: run {
            MOALoadingScreen(modifier = Modifier)
        }
    }
}