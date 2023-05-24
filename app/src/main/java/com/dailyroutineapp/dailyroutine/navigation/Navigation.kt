package com.dailyroutineapp.dailyroutine.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dailyroutineapp.core.navigation.DailyRoutineScreens.Route.HOME
import com.dailyroutineapp.core.navigation.DailyRoutineScreens.Route.LOGIN
import com.dailyroutineapp.core.navigation.DailyRoutineScreens.Route.SING_UP
import com.dailyroutineapp.core.navigation.DailyRoutineScreens.Route.WELCOME
import com.dailyroutineapp.core.util.UiEvent
import com.dailyroutineapp.impl.ui.AuthenticationScreen
import com.dailyroutineapp.impl.ui.HomeTest
import com.dailyroutineapp.impl.ui.WelcomeScreen

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
