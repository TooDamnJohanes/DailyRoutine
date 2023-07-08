@file:OptIn(ExperimentalMaterialApi::class)
@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.dailyroutineapp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.dailyroutineapp.core.R
import com.dailyroutineapp.core.dimensions.LocalSpacing
import com.dailyroutineapp.core.models.Priority
import com.dailyroutineapp.core.ui.PriorityItem
import com.dailyroutineapp.core.ui.theme.taskBackgroundColor
import com.dailyroutineapp.core.ui.theme.taskItemTextColor
import com.dailyroutineapp.core.ui.theme.topAppBarContentColor
import com.dailyroutineapp.domain.model.Habit

@Composable
fun HabitsHomeScreenSearchAction(
    onSearchActionClicked: () -> Unit,
    onSearchActionIcon: ImageVector,
) {
    IconButton(onClick = {
        onSearchActionClicked.invoke()
    }) {
        Icon(
            imageVector = onSearchActionIcon,
            contentDescription = stringResource(id = R.string.habitsHomeScreen_searchActionAppBar_contentDescription),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun HabitsHomeScreenSortAction(
    onSortActionClicked: (Priority) -> Unit,
    onSortActionIcon: Painter,
    sortExpendedValue: Boolean,
    onSortExpendedChange: () -> Unit,
    priorityList: List<Priority>
) {

    IconButton(onClick = {
        onSortExpendedChange.invoke()
    }) {
        Icon(
            painter = onSortActionIcon,
            contentDescription = stringResource(id = R.string.habitsHomeScreen_sortActionAppBar_contentDescription),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
        DropdownMenu(
            expanded = sortExpendedValue,
            onDismissRequest = { onSortExpendedChange.invoke() }
        ) {
            HabitsHomeDropDownMenuItems(
                priorityList = priorityList,
                onSortActionClicked = onSortActionClicked,
                onExpendedChange = onSortExpendedChange
            )
        }
    }
}

@Composable
fun HabitsHomeScreenDeleteAllAction(
    deleteAllExpendedValue: Boolean,
    onDeleteAllActionIcon: ImageVector,
    onDeleteAllActionClicked: () -> Unit,
    onDeleteAllExpendedChange: () -> Unit,
) {
    IconButton(onClick = {
        onDeleteAllExpendedChange.invoke()
    }) {
        Icon(
            imageVector = onDeleteAllActionIcon,
            contentDescription = stringResource(id = R.string.habitsHomeScreen_deleteAllActionAppBar_contentDescription),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
        DropdownMenu(
            expanded = deleteAllExpendedValue,
            onDismissRequest = { onDeleteAllExpendedChange.invoke() },
        ) {
            DropdownMenuItem(onClick = {
                onDeleteAllExpendedChange.invoke()
                onDeleteAllActionClicked.invoke()
            }) {
                Text(
                    text = stringResource(id = R.string.habitsHomeScreen_deleteAll_label)
                )
            }
        }
    }
}

@Composable
private fun HabitsHomeDropDownMenuItems(
    priorityList: List<Priority>,
    onSortActionClicked: (Priority) -> Unit,
    onExpendedChange: () -> Unit,
) {
    priorityList.forEach { priority ->
        DropdownMenuItem(
            onClick = {
            onExpendedChange.invoke()
            onSortActionClicked.invoke(priority)
        }) {
            PriorityItem(priority = priority)
        }
    }
}

@Composable
fun HabitsHomeScreenListItem(
    habit: Habit,
    onHabitItemClicked: (Int) -> Unit
) {
    val localSpacing = LocalSpacing.current
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.taskBackgroundColor,
        shape = RectangleShape,
        elevation = localSpacing.spaceSExtraSmall,
        onClick = { onHabitItemClicked.invoke(habit.id) }
    ) {
        Column(
            modifier = Modifier
                .padding(localSpacing.spaceSMedium)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    text = habit.title,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.taskItemTextColor,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .weight(8f)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier
                            .width(localSpacing.spaceMedium)
                            .height(localSpacing.spaceMedium)
                    ) {
                        drawCircle(
                            color = habit.priority.color
                        )
                    }
                }
            }
            Text(
                text = habit.description,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.taskItemTextColor,
                fontWeight = FontWeight.Normal,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    }
}

@Composable
@Preview
fun HabitsHomeScreenListItemPreview() {
    val mockkHabit = Habit(
        id = 0,
        title = "Mockk Habit",
        description = "Mockk",
        habitTime = 1L,
        habitWeekFrequency = 1,
        priority = Priority.HIGH
    )
    HabitsHomeScreenListItem(
        habit = mockkHabit
    ) {

    }
}

@Composable
@Preview
fun HabitsHomeScreenSearchActionPreview() {
    HabitsHomeScreenSearchAction(
        onSearchActionClicked = { }, onSearchActionIcon = Icons.Default.Search
    )
}

@Composable
@Preview
fun HabitsHomeScreenSortActionPreview() {
    var isExpended by remember {
        mutableStateOf(false)
    }
    HabitsHomeScreenSortAction(
        onSortExpendedChange = {
            isExpended = !isExpended
        },
        onSortActionClicked = { },
        onSortActionIcon = painterResource(id = R.drawable.filter_list),
        sortExpendedValue = isExpended,
        priorityList = listOf(Priority.NONE, Priority.LOW, Priority.MEDIUM, Priority.HIGH)

    )
}
