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
import com.example.feature.login.domain.SignIn
import com.example.feature.login.domain.ValidateCredentialsUser
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var factory: LoginViewModelFactory

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
        factory = LoginViewModelFactory(
            SignIn(repository),
            ValidateCredentialsUser(repository)
        )
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            if (it.isLoginSuccess) {
                context?.let {
                    MainActivity.start(it)
                }
            }
            progressBar.visibility = if (it.isLoading) View.VISIBLE else View.GONE
        })
        btnLogin.setOnClickListener {
            val username = edtUserName.text.toString()
            val password = edtPassword.text.toString()
            viewModel.login(username, password)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        const val TAG = "TAG"
    }
}