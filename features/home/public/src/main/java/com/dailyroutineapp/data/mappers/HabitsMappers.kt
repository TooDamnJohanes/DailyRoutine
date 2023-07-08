package com.dailyroutineapp.data.mappers

import com.dailyroutineapp.entity.Habit as HabitsEntity
import com.dailyroutineapp.domain.model.Habit as HabitsDomain

val HabitsEntity.asDomain
    get() = HabitsDomain(
        id = this.id,
        title = this.title,
        description = this.description,
        habitTime = this.habitTime,
        habitWeekFrequency = this.habitWeekFrequency,
        priority = this.priority
    )

val HabitsDomain.asEntity
    get() = HabitsEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        habitTime = this.habitTime,
        habitWeekFrequency = this.habitWeekFrequency,
        priority = this.priority
    )