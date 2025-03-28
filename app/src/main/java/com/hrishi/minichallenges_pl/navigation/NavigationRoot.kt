package com.hrishi.minichallenges_pl.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hrishi.minichallenges_pl.home_screen.navigation.HomScreenBaseRoute
import com.hrishi.minichallenges_pl.home_screen.navigation.homeNavGraph

@Composable
fun NavigationRoot(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomScreenBaseRoute,
        modifier = modifier,
    ) {
        homeNavGraph(navController)
    }
}
