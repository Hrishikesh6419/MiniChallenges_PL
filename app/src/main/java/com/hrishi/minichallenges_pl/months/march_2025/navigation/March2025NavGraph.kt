package com.hrishi.minichallenges_pl.months.march_2025.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.hrishi.minichallenges_pl.core.ChallengesHomeScreen
import com.hrishi.minichallenges_pl.core.utils.Mars2025Challenges
import com.hrishi.minichallenges_pl.months.march_2025.dawn_dusk_transition.DawnDuskTransitionScreenRoot
import com.hrishi.minichallenges_pl.months.march_2025.mars_weather_card.MarsWeatherCardScreenRoot
import com.hrishi.minichallenges_pl.months.march_2025.orbiting_rocket.OrbitAnimation
import com.hrishi.minichallenges_pl.months.march_2025.space_craft_flip_cards.SpaceCraftFlipCardsScreen

fun NavGraphBuilder.march2025NavGraph(
    navController: NavHostController
) {
    navigation<March2025BaseRoute>(
        startDestination = March2025HomeScreenRoute
    ) {
        composable<March2025HomeScreenRoute> {
            ChallengesHomeScreen(
                challenges = Mars2025Challenges.entries,
                onChallengeSelected = { challenge ->
                    when (challenge) {
                        Mars2025Challenges.DAWN_DUSK_ANIMATION -> navController.navigateToDawnDuskTransitionScreenRoute()
                        Mars2025Challenges.MARS_WEATHER_CARD -> navController.navigateToMarsWeatherCardScreenRoute()
                        Mars2025Challenges.ORBITING_ROCKET -> navController.navigateToOrbitingRocketScreenRoute()
                        Mars2025Challenges.SPACE_CRAFT_FLIP_CARDS -> navController.navigateToSpaceCraftFlipCardScreenRoute()
                    }
                }
            )
        }
        composable<DawnDuskTransitionScreenRoute> {
            DawnDuskTransitionScreenRoot()
        }
        composable<MarsWeatherCardScreenRoute> {
            MarsWeatherCardScreenRoot()
        }
        composable<OrbitingRocketScreenRoute> {
            OrbitAnimation()
        }
        composable<SpaceCraftFlipCardScreenRoute> {
            SpaceCraftFlipCardsScreen()
        }
    }
}