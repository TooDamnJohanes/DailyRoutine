package com.dailyroutineapp.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.dailyroutineapp.core.R
import com.dailyroutineapp.core.models.Priority
import com.dailyroutineapp.core.models.Resource
import com.dailyroutineapp.core.ui.DefaultFloatingActionButton
import com.dailyroutineapp.core.ui.DefaultSearchAppBar
import com.dailyroutineapp.core.ui.DefaultTopAppBar
import com.dailyroutineapp.core.ui.EmptyContent
import com.dailyroutineapp.core.util.UiEvent
import com.dailyroutineapp.domain.model.Habit
import com.dailyroutineapp.domain.models.HomeScreenActions
import com.dailyroutineapp.domain.models.HomeScreenEditions
import com.dailyroutineapp.ui.components.HabitsHomeScreenDeleteAllAction
import com.dailyroutineapp.ui.components.HabitsHomeScreenListItem
import com.dailyroutineapp.ui.components.HabitsHomeScreenSearchAction
import com.dailyroutineapp.ui.components.HabitsHomeScreenSortAction
import com.dailyroutineapp.ui.viewmodels.HomeSharedViewModel

@Composable
fun HabitsHomeMainScreen(
    homeSharedViewModel: HomeSharedViewModel = hiltViewModel(),
    onNavigate: (Int) -> Unit,
    onNavigateToHabitsCreation: (UiEvent.Navigate) -> Unit
) {

    val expendedSortMenuDropDown = homeSharedViewModel.expendedSortMenuDropDown.value
    val expendedDeleteAllMenuDropDown = homeSharedViewModel.expendedDeleteAllMenuDropDown.value
    val currentHabitSearchQuery = homeSharedViewModel.currentHabitSearchQuery.value
    val userPressSearchIcon = homeSharedViewModel.userPressSearchIcon.value
    val habitsList = homeSharedViewModel.allHabits.collectAsState().value

    LaunchedEffect(key1 = true) {
        homeSharedViewModel.onUiEvent(HomeScreenActions.GetAllHabits())
    }

    Scaffold(
        topBar = {
            HabitsHomeScreenTopAppBar(
                userPressSearchIcon = userPressSearchIcon,
                homeSharedViewModel = homeSharedViewModel,
                currentHabitSearchQuery = currentHabitSearchQuery,
                expendedSortMenuDropDown = expendedSortMenuDropDown,
                expendedDeleteAllMenuDropDown = expendedDeleteAllMenuDropDown,
            )
        },
        content = {
            HabitHomeScreenContent(
                habitsList = habitsList,
                onNavigate = onNavigate
            )
        },
        floatingActionButton = {
            DefaultFloatingActionButton(
                onFloatingActionButtonAction = {
                    // onNavigateToHabitsCreation(UiEvent.Navigate(DailyRoutineScreens.Route.HABITS_CREATION))
                    homeSharedViewModel.onUiEvent(HomeScreenActions.Add())
                },
                floatingActionButtonIcon = Icons.Default.Add,
                floatingActionButtonContentDescription = stringResource(
                    id = R.string.habitsHomeScreen_floatingActionButton_contentDescription
                ),
            )
        }
    )
}

@Composable
fun HabitHomeScreenContent(
    habitsList: Resource<List<Habit>>,
    onNavigate: (Int) -> Unit,
) {
    when (habitsList) {
        is Resource.Success -> {
            habitsList.data?.let { habitsList ->
                HomeScreenHabitsList(
                    habitsList = habitsList
                ) { habitId ->
                    onNavigate.invoke(habitId)
                }
            }

        }

        is Resource.Error,
        is Resource.Loading -> {
            EmptyContent()
        }
    }

}

@Composable
fun HabitsHomeScreenTopAppBar(
    userPressSearchIcon: Boolean,
    homeSharedViewModel: HomeSharedViewModel,
    currentHabitSearchQuery: String,
    expendedSortMenuDropDown: Boolean,
    expendedDeleteAllMenuDropDown: Boolean,
) {
    if (userPressSearchIcon) {
        DefaultSearchAppBar(
            newSearchValue = currentHabitSearchQuery,
            onSearchTextChanged = { newHabitSearchQuery ->
                homeSharedViewModel.onUiEditionEvents(
                    HomeScreenEditions.UserEditingHabitSearchQuery(
                        newHabitSearchQuery
                    )
                )
            },
            onSearchClosedClicked = {
                homeSharedViewModel.onUiEvent(HomeScreenActions.ClearHabitsSearchQuery())
            },
            onSearchClicked = {
                homeSharedViewModel.onUiEvent(HomeScreenActions.Search())
            }
        )
    } else {
        DefaultTopAppBar(appBarText = stringResource(id = R.string.app_name)) {
            HabitsCreationAppBarActions(
                onSearchActionIcon = Icons.Default.Search,
                onSortActionIcon = painterResource(id = R.drawable.filter_list),
                priorityList = homeSharedViewModel.getPriorityList(),
                sortMenuIsExpended = expendedSortMenuDropDown,
                deleteAllMenuIsExpended = expendedDeleteAllMenuDropDown,
                onDeleteAllActionIcon = Icons.Default.Delete,
                onDeleteAllActionClicked = { homeSharedViewModel.onUiEvent(HomeScreenActions.DeleteAll()) },
                onSortMenuExpendedValueChanged = {
                    homeSharedViewModel.onUiEvent(HomeScreenActions.SortIconPressed())
                },
                onDeleteAllMenuExpendedValueChanged = {
                    homeSharedViewModel.onUiEvent(HomeScreenActions.DeleteAllIconPressed())
                },
                onSearchActionClicked = { homeSharedViewModel.onUiEvent(HomeScreenActions.StartTypeSearchQuery()) },
                onSortActionClicked = { priority ->
                    homeSharedViewModel.onUiEvent(HomeScreenActions.SortHabits(priority))
                },
            )
        }
    }
}

@Composable
fun HabitsCreationAppBarActions(
    onSearchActionIcon: ImageVector,
    priorityList: List<Priority>,
    sortMenuIsExpended: Boolean,
    deleteAllMenuIsExpended: Boolean,
    onSortActionIcon: Painter,
    onDeleteAllActionIcon: ImageVector,
    onDeleteAllActionClicked: () -> Unit,
    onSearchActionClicked: () -> Unit,
    onSortActionClicked: (Priority) -> Unit,
    onSortMenuExpendedValueChanged: () -> Unit,
    onDeleteAllMenuExpendedValueChanged: () -> Unit
) {
    HabitsHomeScreenSearchAction(
        onSearchActionClicked = onSearchActionClicked,
        onSearchActionIcon = onSearchActionIcon,
    )

    HabitsHomeScreenSortAction(
        onSortActionClicked = onSortActionClicked,
        onSortActionIcon = onSortActionIcon,
        sortExpendedValue = sortMenuIsExpended,
        priorityList = priorityList,
        onSortExpendedChange = onSortMenuExpendedValueChanged
    )

    HabitsHomeScreenDeleteAllAction(
        deleteAllExpendedValue = deleteAllMenuIsExpended,
        onDeleteAllActionIcon = onDeleteAllActionIcon,
        onDeleteAllActionClicked = onDeleteAllActionClicked,
        onDeleteAllExpendedChange = onDeleteAllMenuExpendedValueChanged
    )
}

@Composable
fun HomeScreenHabitsList(
    habitsList: List<Habit>,
    onHabitClick: (Int) -> Unit,
) {
    LazyColumn {
        items(
            items = habitsList,
            key = { habit ->
                habit.id
            }
        ) { habit ->
            HabitsHomeScreenListItem(
                habit = habit,
                onHabitItemClicked = onHabitClick
            )
        }
    }
}

@Composable
@Preview
fun HabitsHomeMainScreenPreview() {
    HabitsHomeMainScreen(
        onNavigate = {},
        onNavigateToHabitsCreation = {}
    )
}
