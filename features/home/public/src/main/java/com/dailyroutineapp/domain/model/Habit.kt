package com.dailyroutineapp.domain.model

import com.dailyroutineapp.core.models.Priority


data class Habit(
    val id: Int = 0,
    val title: String,
    val description: String,
    val habitTime: Long,
    val habitWeekFrequency: Int,
    val priority: Priority
)
