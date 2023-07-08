package com.dailyroutineapp.core.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val LowPriorityColor = Color(0xFF00C980)
val MediumPriorityColor = Color(0xFFFFC114)
val HighPriorityColor = Color(0xFFFF4646)
val NonePriorityColor = Color(0xFFFFFFFF)

val LightGray = Color(0XFFFCFCFC)
val MediumGray = Color(0XFF9C9C9C)
val DarkGray = Color(0XFF141414)

val Colors.taskBackgroundColor: Color
    @Composable
    get() = if (isLight) LightGray else DarkGray

val Colors.taskItemTextColor: Color
    @Composable
    get() = if (isLight) DarkGray else LightGray

val Colors.fabBackgroundColor: Color
    @Composable
    get() = if (isLight) Teal200 else Purple700

val Colors.topAppBarContentColor: Color
    @Composable
    get() = if (isLight) Color.White else LightGray

val Colors.topAppBarBackgroundColor: Color
    @Composable
    get() = if (isLight) Purple500 else Color.Black