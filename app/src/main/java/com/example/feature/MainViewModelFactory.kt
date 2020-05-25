package com.example.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feature.login.domain.SignOut

class MainViewModelFactory(
    private val signOut: SignOut
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(signOut) as T
    }
}