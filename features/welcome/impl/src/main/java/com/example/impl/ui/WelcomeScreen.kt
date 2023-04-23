package com.example.impl.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.R
import com.example.core.dimensions.LocalSpacing
import com.example.core.navigation.DailyRoutineScreens
import com.example.core.util.UiEvent
import com.example.impl.components.WelcomeActionButton

@Composable
fun WelcomeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Box(
            modifier = Modifier.padding(bottom = spacing.spaceSmall)
        ) {
            Text(
                text = stringResource(id = R.string.welcomeScreen_welcomeMessage),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
            )
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        WelcomeActionButton(
            text = stringResource(id = R.string.welcomeScreen_next),
            onClick = { onNavigate(UiEvent.Navigate(DailyRoutineScreens.Route.LOGIN)) },
            modifier = Modifier.align(CenterHorizontally),
            isEnable = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen() {

    }
}