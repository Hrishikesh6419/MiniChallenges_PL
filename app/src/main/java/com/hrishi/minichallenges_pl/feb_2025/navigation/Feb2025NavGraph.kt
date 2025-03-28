package com.hrishi.minichallenges_pl.feb_2025.navigation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.hrishi.minichallenges_pl.feb_2025.battery_indicator.FestiveBatteryIndicatorScreen
import com.hrishi.minichallenges_pl.feb_2025.home.Feb2025HomeScreen
import com.hrishi.minichallenges_pl.utils.Feb2025Challenges
import kotlinx.coroutines.delay

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
                        Feb2025Challenges.BATTERY_INDICATOR -> navController.navigateFestiveBatteryIndicatorScreenRoute()
                        Feb2025Challenges.CONFETTI -> Unit
                    }
                }
            )
        }
        composable<FestiveBatteryIndicatorScreenRoute> {
            val animateValue = remember {
                Animatable(0f)
            }

            LaunchedEffect(Unit) {
                animateValue.animateTo(
                    1f,
                    animationSpec = tween(10000)
                )
                delay(2000)
                animateValue.animateTo(
                    0f,
                    animationSpec = tween(10000)
                )
            }
            // TODO: Remove the above code
            FestiveBatteryIndicatorScreen(batteryPercentage = animateValue.value)
        }
    }
}