package com.example.feature.login.presentation

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.R
import com.example.feature.MainActivity
import com.example.feature.MainApplication
import com.example.feature.core.network.ApiInterface
import com.example.feature.login.data.LoginLocalDataSource
import com.example.feature.login.data.LoginMapper
import com.example.feature.login.data.LoginRemoteDataSource
import com.example.feature.login.data.LoginRepository
import com.example.feature.login.domain.CheckUserLogin
import com.example.feature.login.domain.GetLoginInformation
import com.example.feature.login.domain.SignIn

class SplashFragment : Fragment() {

    private lateinit var viewModel: SplashViewModel
    private lateinit var factory: SplashViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //TODO Improve with dagger 2
        val pref = PreferenceManager.getDefaultSharedPreferences(MainApplication.appContext)
        val mapper = LoginMapper()
        val api = ApiInterface.getClient().create(
            ApiInterface::class.java)
        val repository = LoginRepository(LoginLocalDataSource(pref, mapper), LoginRemoteDataSource(api, mapper))
        factory = SplashViewModelFactory(
            SignIn(repository),
            CheckUserLogin(repository),
            GetLoginInformation(repository)
        )
        viewModel = ViewModelProvider(this, factory).get(SplashViewModel::class.java)
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            when (it.userLoginState) {
                SplashViewState.USER_NOT_LOGIN_YET -> {
                    activity?.let { activity ->
                        if (!activity.isFinishing){
                            LoginActivity.start(activity)
                            activity.finish()
                        }
                    }
                }
                SplashViewState.USER_ALREADY_LOGIN -> {
                    activity?.let { activity ->
                        if (!activity.isFinishing){
                            MainActivity.start(activity)
                            activity.finish()
                        }
                    }
                }
                else -> {
                }
            }
        })
        viewModel.checkUserLogin()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        const val DELAY = 1000L
        const val TAG = "TAG"
    }
}