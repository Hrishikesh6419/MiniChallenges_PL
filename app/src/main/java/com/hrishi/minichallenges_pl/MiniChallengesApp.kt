package com.hrishi.minichallenges_pl

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.hrishi.minichallenges_pl.months.april_2025.di.april2025Module
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MiniChallengesApp : Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MiniChallengesApp)
            modules(
                april2025Module
            )
        }
    }
}