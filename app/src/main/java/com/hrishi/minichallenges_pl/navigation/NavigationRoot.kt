package com.hrishi.minichallenges_pl.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hrishi.minichallenges_pl.home_screen.navigation.HomScreenBaseRoute
import com.hrishi.minichallenges_pl.home_screen.navigation.homeNavGraph
import com.hrishi.minichallenges_pl.months.april_2025.navigation.april2025NavGraph
import com.hrishi.minichallenges_pl.months.feb_2025.navigation.feb2025NavGraph
import com.hrishi.minichallenges_pl.months.march_2025.navigation.march2025NavGraph

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
        feb2025NavGraph(navController)
        march2025NavGraph(navController)
        april2025NavGraph(navController)
    }
}
