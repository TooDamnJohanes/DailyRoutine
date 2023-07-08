package com.dailyroutineapp.core.navigation


enum class DailyRoutineScreens {
    Welcome,
    Login,
    SingUp,
    Home;

    object Route {
        const val WELCOME = "welcome"
        const val LOGIN = "login"
        const val SING_UP = "singUp"
        const val HABITS_HOME = "habits_home"
        const val HABITS_CREATION = "habits_creation"
        const val HABITS_DETAIL = "habits_detail"
        const val HABITS_FAVORITES = "habits_favorites"
    }
}