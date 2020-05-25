package com.example.feature.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feature.login.domain.SignIn
import com.example.feature.login.domain.ValidateCredentialsUser

class LoginViewModelFactory(
    private val authenticate: SignIn,
    private val validate: ValidateCredentialsUser
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(
            authenticate,
            validate
        ) as T
    }
}