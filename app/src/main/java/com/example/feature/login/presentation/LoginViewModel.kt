package com.example.feature.login.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.login.domain.SignIn
import com.example.feature.login.domain.AuthenticationEntity
import com.example.feature.login.domain.ValidateCredentialsUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authenticate: SignIn,
    private val validate: ValidateCredentialsUser
) : ViewModel() {

    var viewState: MutableLiveData<LoginViewState> = MutableLiveData()


    init {
        val viewState = LoginViewState(AuthenticationEntity(), null, false)
        this.viewState.value = viewState
    }

    fun login(userName: String, password: String){
        viewModelScope.launch {
            val isValidateSuccess = validate.validate(userName).success() == true
            if (!isValidateSuccess){
                viewState.value = viewState.value?.copy(
                    isLoading = false
                )
                return@launch
            }

            viewState.value = viewState.value?.copy(
                isLoading = true
            )
            authenticate.authenticate(userName, password)
            delay(1000)
            viewState.value = viewState.value?.copy(
                isLoginSuccess = true,
                isLoading = false
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}