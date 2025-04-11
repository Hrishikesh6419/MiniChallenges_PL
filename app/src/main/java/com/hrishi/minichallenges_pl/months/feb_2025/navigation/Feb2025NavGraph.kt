package com.hrishi.minichallenges_pl.months.feb_2025.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.hrishi.minichallenges_pl.core.ChallengesHomeScreen
import com.hrishi.minichallenges_pl.core.utils.Feb2025Challenges
import com.hrishi.minichallenges_pl.months.feb_2025.battery_indicator.FestiveBatteryIndicatorScreenRoot
import com.hrishi.minichallenges_pl.months.feb_2025.sms_confetti.SmsConfettiScreenRoot
import com.hrishi.minichallenges_pl.months.feb_2025.wave_form.AudioWaveFormScreen

fun NavGraphBuilder.feb2025NavGraph(
    navController: NavHostController
) {
    navigation<Feb2025BaseRoute>(
        startDestination = Feb2025HomeScreenRoute
    ) {
        composable<Feb2025HomeScreenRoute> {
            ChallengesHomeScreen(
                challenges = Feb2025Challenges.entries,
                onChallengeSelected = { challenge ->
                    when (challenge) {
                        Feb2025Challenges.BATTERY_INDICATOR -> navController.navigateToFestiveBatteryIndicatorScreenRoute()
                        Feb2025Challenges.CONFETTI -> navController.navigateToSmsConfettiScreenRouteScreenRoute()
                        Feb2025Challenges.AUDIO_WAVE -> navController.navigateToAudioWaveFormScreenRoute()
                    }
                }
            )
        }
        composable<FestiveBatteryIndicatorScreenRoute> {
            FestiveBatteryIndicatorScreenRoot()
        }
        composable<SmsConfettiScreenRoute> {
            SmsConfettiScreenRoot()
        }
        composable<AudioWaveFormScreenRoute> {
            AudioWaveFormScreen()
        }
    }
}