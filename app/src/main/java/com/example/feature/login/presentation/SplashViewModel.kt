package com.example.feature.login.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.login.domain.SignIn
import com.example.feature.login.domain.CheckUserLogin
import com.example.feature.login.domain.GetLoginInformation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val authenticate: SignIn,
    private val isUserLogin: CheckUserLogin,
    private val getLoginInformation: GetLoginInformation
) : ViewModel() {

    var viewState: MutableLiveData<SplashViewState> = MutableLiveData()

    init {
        val viewState = SplashViewState(SplashViewState.USER_START_LOGIN)
        this.viewState.value = viewState
    }

    fun checkUserLogin() {
        viewModelScope.launch {
            delay(1000)
            val info = isUserLogin.isUserLogin().success()
            val isUserLogin = info == true
            if (isUserLogin) {
                val info = getLoginInformation.getLoginInformation()
                info?.success()?.let {
                    val login = authenticate.authenticate(it.username, it.password)
                    delay(1000)
                    login?.success()?.let {
                        viewState.value = viewState.value?.copy(
                            userLoginState = SplashViewState.USER_ALREADY_LOGIN
                        )
                    } ?: run {
                        viewState.value = viewState.value?.copy(
                            userLoginState = SplashViewState.USER_NOT_LOGIN_YET
                        )
                    }
                }
            } else {
                val state = SplashViewState.USER_NOT_LOGIN_YET
                viewState.value = viewState.value?.copy(
                    userLoginState = state
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}