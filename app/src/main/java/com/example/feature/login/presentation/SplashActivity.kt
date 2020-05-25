package com.example.feature.login.presentation

import android.os.Bundle
import com.example.R
import com.example.base.BaseActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportFragmentManager.beginTransaction()
            .replace(R.id.flContainer, SplashFragment(), SplashFragment.TAG)
            .commitAllowingStateLoss()
    }
}
