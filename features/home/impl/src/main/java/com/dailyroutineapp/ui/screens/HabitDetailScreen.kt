package com.dailyroutineapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.dailyroutineapp.core.dimensions.LocalFontSize
import com.dailyroutineapp.core.navigation.DailyRoutineScreens
import com.dailyroutineapp.core.util.UiEvent
import com.dailyroutineapp.domain.models.HomeScreenActions
import com.dailyroutineapp.ui.viewmodels.HomeSharedViewModel

@Composable
fun HabitDetailScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    habitId: Int = 999,
    homeSharedViewModel: HomeSharedViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        homeSharedViewModel.onUiEvent(HomeScreenActions.GetHabit(habitId))
    }
    val currentHabit = homeSharedViewModel.currentHabit.collectAsState()
    Text(
        text = currentHabit.value.title,
        style = TextStyle(
            fontSize = LocalFontSize.current.fontExtraLarge,
            textAlign = TextAlign.Center,
            color = Color.DarkGray,
        ),
        modifier = Modifier
            .clickable { onNavigate(UiEvent.Navigate(DailyRoutineScreens.Route.HABITS_HOME)) }
    )
}