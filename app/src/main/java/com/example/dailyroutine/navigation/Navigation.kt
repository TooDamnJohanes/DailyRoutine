package com.example.dailyroutine.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core.navigation.DailyRoutineScreens.Route.HOME
import com.example.core.navigation.DailyRoutineScreens.Route.LOGIN
import com.example.core.navigation.DailyRoutineScreens.Route.SING_UP
import com.example.core.navigation.DailyRoutineScreens.Route.WELCOME
import com.example.core.util.UiEvent
import com.example.impl.ui.AuthenticationScreen
import com.example.impl.ui.HomeTest
import com.example.impl.ui.WelcomeScreen

@Composable
fun DailyRoutineNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WELCOME
    ) {
        composable(WELCOME) {
            WelcomeScreen(onNavigate = navController::navigateTo)
        }
        composable(LOGIN) {
            AuthenticationScreen(onNavigate = navController::navigateTo)
        }
        composable(SING_UP) {

        }
        composable(HOME) {
            HomeTest()
        }
    }
}

fun NavController.navigateTo(event: UiEvent.Navigate) {
    this.navigate(event.route)
}
