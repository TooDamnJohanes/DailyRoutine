package com.example.core.navigation


enum class DailyRoutineScreens {
    Welcome,
    Login,
    SingUp,
    Home;

    object Route {
        const val WELCOME = "welcome"
        const val LOGIN = "login"
        const val SING_UP = "singUp"
        const val HOME = "home"
    }
}