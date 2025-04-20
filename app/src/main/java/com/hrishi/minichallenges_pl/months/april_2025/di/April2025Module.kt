package com.hrishi.minichallenges_pl.months.april_2025.di

import com.hrishi.minichallenges_pl.months.april_2025.egg_hunt_checklist.EggHuntPreferenceManager
import com.hrishi.minichallenges_pl.months.april_2025.egg_hunt_checklist.EggHuntViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val april2025Module = module {
    single { EggHuntPreferenceManager(androidContext()) }
    viewModelOf(::EggHuntViewModel)
}