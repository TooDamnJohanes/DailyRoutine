package com.dailyroutineapp.core.models

import androidx.compose.ui.graphics.Color
import com.dailyroutineapp.core.ui.theme.HighPriorityColor
import com.dailyroutineapp.core.ui.theme.MediumPriorityColor
import com.dailyroutineapp.core.ui.theme.LowPriorityColor
import com.dailyroutineapp.core.ui.theme.NonePriorityColor


enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor),
}