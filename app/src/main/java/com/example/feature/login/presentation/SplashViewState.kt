package com.example.feature.login.presentation


data class SplashViewState(
        val userLoginState: Int = USER_START_LOGIN
){
        companion object {
                const val USER_START_LOGIN = 0
                const val USER_NOT_LOGIN_YET = 1
                const val USER_ALREADY_LOGIN = 2
        }
}