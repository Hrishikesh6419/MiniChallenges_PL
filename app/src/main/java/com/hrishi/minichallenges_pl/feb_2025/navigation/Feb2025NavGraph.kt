package com.hrishi.minichallenges_pl.feb_2025.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.hrishi.minichallenges_pl.feb_2025.battery_indicator.FestiveBatteryIndicatorScreenRoot
import com.hrishi.minichallenges_pl.feb_2025.home.Feb2025HomeScreen
import com.hrishi.minichallenges_pl.feb_2025.sms_confetti.SmsConfettiScreenRoot
import com.hrishi.minichallenges_pl.utils.Feb2025Challenges

fun NavGraphBuilder.feb2025NavGraph(
    navController: NavHostController
) {
    navigation<Feb2025BaseRoute>(
        startDestination = Feb2025HomeScreenRoute
    ) {
        composable<Feb2025HomeScreenRoute> {
            Feb2025HomeScreen(
                onChallengeSelected = { challenge ->
                    when (challenge) {
                        Feb2025Challenges.BATTERY_INDICATOR -> navController.navigateToFestiveBatteryIndicatorScreenRoute()
                        Feb2025Challenges.CONFETTI -> navController.navigateToSmsConfettiScreenRouteScreenRoute()
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
    }
}