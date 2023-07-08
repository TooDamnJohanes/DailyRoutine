package com.dailyroutineapp.core.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.dailyroutineapp.core.R
import com.dailyroutineapp.core.dimensions.LocalSpacing
import com.dailyroutineapp.core.models.Priority
import com.dailyroutineapp.core.ui.theme.MediumGray
import com.dailyroutineapp.core.ui.theme.fabBackgroundColor
import com.dailyroutineapp.core.ui.theme.topAppBarBackgroundColor
import com.dailyroutineapp.core.ui.theme.topAppBarContentColor

@Composable
fun DefaultFloatingActionButton(
    modifier: Modifier = Modifier,
    floatingActionButtonIcon: ImageVector,
    floatingActionButtonContentDescription: String,
    onFloatingActionButtonAction: () -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFloatingActionButtonAction.invoke()
        },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = floatingActionButtonIcon,
            contentDescription = floatingActionButtonContentDescription,
            modifier = modifier,
            tint = Color.White
        )
    }
}

@Composable
fun DefaultTopAppBar(appBarText: String, actions: @Composable (() -> Unit)) {
    TopAppBar(
        title = {
            Text(
                text = appBarText,
                style = TextStyle(
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Normal,
                ),
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = { actions.invoke() }
    )
}

@Composable
fun DefaultSearchAppBar(
    newSearchValue: String,
    onSearchTextChanged: (newSearchValue: String) -> Unit,
    onSearchClosedClicked: () -> Unit,
    onSearchClicked: () -> Unit
) {
    val localSpacing = LocalSpacing.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(localSpacing.spaceSLarge),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.topAppBarBackgroundColor
    ) {
        TextField(
            value = newSearchValue,
            onValueChange = { newSearchValue ->
                onSearchTextChanged.invoke(newSearchValue)
            },
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                Text(
                    text = stringResource(R.string.coreComponents_search),
                    style = TextStyle(
                        color = Color.White,
                        fontStyle = FontStyle.Italic
                    ),
                    modifier = Modifier
                        .alpha(ContentAlpha.disabled)
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colors.topAppBarContentColor,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = { onSearchClicked.invoke() },
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.habitsHomeScreen_searchActionAppBar_contentDescription),
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = { onSearchClosedClicked.invoke() },
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.habitsHomeScreen_closeSearchActionAppBar_contentDescription),
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked.invoke()
                }
            )
        )
    }
}

@Composable
fun EmptyContent() {
    val localSpacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_sentiment_dissatisfied_24),
            contentDescription = stringResource(id = R.string.habitsHomeScreen_emptyHomeScreen_contentDescription),
            modifier = Modifier
                .size(localSpacing.emptyScreenIconSize),
            tint = MediumGray
        )
        Text(
            text = stringResource(id = R.string.coreComponents_noTaskFound),
            color = MediumGray,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h6.fontSize
        )
    }
}

@Composable
fun PriorityItem(
    priority: Priority
) {
    val spacing = LocalSpacing.current
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier.size(spacing.spaceMedium)
        ) {
            drawCircle(
                color = priority.color
            )
        }
        Text(
            text = priority.name,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colors.onSurface
            ),
            modifier = Modifier
                .padding(start = spacing.spaceSmall)
        )
    }
}

@Composable
@Preview
fun PriorityItemHighPreview() {
    PriorityItem(priority = Priority.HIGH)
}

@Composable
@Preview
fun PriorityItemMediumPreview() {
    PriorityItem(priority = Priority.MEDIUM)
}

@Composable
@Preview
fun PriorityItemLowPreview() {
    PriorityItem(priority = Priority.LOW)
}


@Composable
@Preview
fun DefaultTopAppBarPreview() {
    DefaultTopAppBar("Daily Routine") {

    }
}


@Composable
@Preview
fun FloatingActionButtonPreview() {
    DefaultFloatingActionButton(
        floatingActionButtonIcon = Icons.Default.Add,
        floatingActionButtonContentDescription = stringResource(
            id = R.string.habitsHomeScreen_floatingActionButton_contentDescription
        ),
    ) {

    }
}

@Composable
@Preview
fun DefaultSearchAppBarPreview() {
    DefaultSearchAppBar(
        newSearchValue = "",
        onSearchTextChanged = { },
        onSearchClosedClicked = { }
    ) {

    }
}
