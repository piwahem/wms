package com.example.feature

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.login.domain.SignOut
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(
    private val signOut: SignOut
) : ViewModel() {

    var viewState: MutableLiveData<MainViewState> = MutableLiveData()

    init {
        val viewState = MainViewState(null, false, false)
        this.viewState.value = viewState
    }

    fun signOut() {
        viewModelScope.launch {
            viewState.value = viewState.value?.copy(
                isLoading = true
            )
            delay(2000)

            val isSignOutSuccess = signOut.signOut().success() == true
            viewState.value = viewState.value?.copy(
                error = null,
                isLoading = false,
                isSignOutSuccess = isSignOutSuccess
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}