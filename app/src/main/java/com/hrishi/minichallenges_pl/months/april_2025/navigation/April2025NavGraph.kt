package com.hrishi.minichallenges_pl.months.april_2025.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.hrishi.minichallenges_pl.core.ChallengesHomeScreen
import com.hrishi.minichallenges_pl.core.utils.April2025Challenges
import com.hrishi.minichallenges_pl.months.april_2025.clipboard_chickifier.ClipboardChickifierScreenRoot
import com.hrishi.minichallenges_pl.months.april_2025.easter_checkout.navigation.easterCheckoutNavGraph
import com.hrishi.minichallenges_pl.months.april_2025.easter_checkout.navigation.navigateToEasterCheckoutHomeRoute
import com.hrishi.minichallenges_pl.months.april_2025.egg_hunt_checklist.EggHuntChecklistScreenRoot
import com.hrishi.minichallenges_pl.months.april_2025.shaky_egg.ShakyEggScreenRoot

fun NavGraphBuilder.april2025NavGraph(
    navController: NavHostController
) {
    navigation<April2025BaseRoute>(
        startDestination = April2025HomeScreenRoute
    ) {
        composable<April2025HomeScreenRoute> {
            ChallengesHomeScreen(
                challenges = April2025Challenges.entries,
                onChallengeSelected = { challenge ->
                    when (challenge) {
                        April2025Challenges.SHAKY_EGG -> navController.navigateToShakyEggScreenRoute()
                        April2025Challenges.EGG_HUNT_CHECKLIST -> navController.navigateToEggHuntChecklistScreenRoute()
                        April2025Challenges.CLIPBOARD_CHICKIFIER -> navController.navigateToClipboardChickifierScreenRoute()
                        April2025Challenges.EASTER_CHECKOUT -> navController.navigateToEasterCheckoutHomeRoute()
                    }
                }
            )
        }
        composable<ShakyEggScreenRoute> {
            ShakyEggScreenRoot()
        }
        composable<EggHuntChecklistScreenRoute> {
            EggHuntChecklistScreenRoot()
        }
        composable<ClipboardChickifierScreenRoute> {
            ClipboardChickifierScreenRoot()
        }
        easterCheckoutNavGraph(navController)
    }
}