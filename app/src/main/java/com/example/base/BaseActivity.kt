package com.example.base

import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.R
import com.example.feature.MainActivity
import com.example.feature.login.presentation.LoginActivity
import com.example.feature.login.presentation.SplashActivity

open class BaseActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false
    private var handler = Handler()
    var runnable = Runnable {
        doubleBackToExitPressedOnce = false
    }

    override fun onBackPressed() {
        if (!isDoubleBackToExitScreen() || doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(
            this,
            getString(R.string.message_click_back_again_to_exit),
            Toast.LENGTH_SHORT
        ).show();
        handler.postDelayed(runnable, 2000)
    }

    override fun onDestroy() {
        if (isDoubleBackToExitScreen()) {
            handler.removeCallbacks(runnable)
        }
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun isDoubleBackToExitScreen(): Boolean {
        return when (this) {
            is MainActivity -> true
            is LoginActivity -> true
            is SplashActivity -> true
            else -> false
        }
    }
}