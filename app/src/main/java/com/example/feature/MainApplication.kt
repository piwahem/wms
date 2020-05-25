package com.example.feature

import android.app.Application
import android.content.Context
import com.example.BuildConfig
import com.google.android.gms.security.ProviderInstaller
import com.google.android.play.core.missingsplits.MissingSplitsManagerFactory
import timber.log.Timber
import timber.log.Timber.DebugTree


class MainApplication: Application() {
    override fun onCreate() {
        if (MissingSplitsManagerFactory.create(this).disableAppIfMissingRequiredSplits()) {
            // Skip app initialization.
            return
        }
        super.onCreate()
        try {
            ProviderInstaller.installIfNeeded(this)
        } catch (ignored: Exception) {
        }
        appContext = this
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    companion object {
        lateinit var appContext: Context
    }
}