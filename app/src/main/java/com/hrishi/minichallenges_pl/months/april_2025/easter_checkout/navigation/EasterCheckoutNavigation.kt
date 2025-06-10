package com.hrishi.minichallenges_pl.months.april_2025.easter_checkout.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.hrishi.minichallenges_pl.months.april_2025.easter_checkout.presentation.home.EasterCheckoutHome

fun NavGraphBuilder.easterCheckoutNavGraph(
    navController: NavHostController
) {
    navigation<EasterCheckoutBaseRoute>(
        startDestination = EasterCheckoutHomeRoute
    ) {
        composable<EasterCheckoutHomeRoute> {
            EasterCheckoutHome()
        }
    }
}