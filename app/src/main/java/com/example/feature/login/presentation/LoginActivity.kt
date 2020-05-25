package com.example.feature.login.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.R
import com.example.base.BaseActivity

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportFragmentManager.beginTransaction()
            .replace(R.id.flContainer, LoginFragment(), LoginFragment.TAG)
            .commitAllowingStateLoss()
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}
