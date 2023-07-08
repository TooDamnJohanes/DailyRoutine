package com.dailyroutineapp.dailyroutine.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dailyroutineapp.core.navigation.DailyRoutineScreens.Route.HABITS_CREATION
import com.dailyroutineapp.core.navigation.DailyRoutineScreens.Route.HABITS_DETAIL
import com.dailyroutineapp.core.navigation.DailyRoutineScreens.Route.HABITS_HOME
import com.dailyroutineapp.core.navigation.DailyRoutineScreens.Route.LOGIN
import com.dailyroutineapp.core.navigation.DailyRoutineScreens.Route.SING_UP
import com.dailyroutineapp.core.navigation.DailyRoutineScreens.Route.WELCOME
import com.dailyroutineapp.core.util.UiEvent
import com.dailyroutineapp.impl.ui.WelcomeScreen
import com.dailyroutineapp.impl.ui.screens.AuthenticationScreen
import com.dailyroutineapp.ui.screens.HabitDetailScreen
import com.dailyroutineapp.ui.screens.HabitsHomeMainScreen
import com.dailyroutineapp.ui.screens.HabitsCreationScreen

@Composable
fun DailyRoutineNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HABITS_HOME
    ) {
        composable(WELCOME) {
            WelcomeScreen(onNavigate = navController::navigateTo)
        }
        composable(LOGIN) {
            AuthenticationScreen(onNavigate = navController::navigateTo)
        }
        composable(SING_UP) {

        }
        composable(HABITS_HOME) {
            HabitsHomeMainScreen(
                onNavigate = { habitId ->
                    navController.navigate(
                        route = "$HABITS_DETAIL/$habitId"
                    )
                },
                onNavigateToHabitsCreation = navController::navigateTo
            )
        }
        composable(HABITS_CREATION) {
            HabitsCreationScreen(onNavigate = navController::navigateTo)
        }
        composable(
            route = "$HABITS_DETAIL/{habitId}",
            arguments = listOf(
                navArgument("habitId") {
                    type = NavType.IntType
                },
            )
        ) {
            val habitId = it.arguments?.getInt("habitId") ?: -1
            HabitDetailScreen(habitId = habitId, onNavigate = navController::navigateTo)
        }

    }
}

fun NavController.navigateTo(event: UiEvent.Navigate) {
    this.navigate(event.route)
}
