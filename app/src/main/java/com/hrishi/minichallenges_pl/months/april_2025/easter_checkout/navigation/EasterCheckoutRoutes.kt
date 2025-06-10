package com.hrishi.minichallenges_pl.months.april_2025.easter_checkout.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import kotlinx.serialization.Serializable

@Serializable
data object EasterCheckoutBaseRoute

@Serializable
data object EasterCheckoutHomeRoute

fun NavController.navigateToEasterCheckoutHomeRoute(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(EasterCheckoutHomeRoute, navOptions)