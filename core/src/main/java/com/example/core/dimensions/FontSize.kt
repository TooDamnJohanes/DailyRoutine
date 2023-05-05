package com.example.core.dimensions

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class FontSize(
    val fontExtraSmall: TextUnit = 4.sp,
    val fontSmall: TextUnit = 8.sp,
    val default: TextUnit = 16.sp,
    val defaultLarge: TextUnit = 18.sp,
    val fontLarge: TextUnit = 32.sp,
    val fontExtraLarge: TextUnit = 64.sp
)

val LocalFontSize = compositionLocalOf { FontSize() }
