package com.hrishi.minichallenges_pl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.hrishi.minichallenges_pl.navigation.NavigationRoot
import com.hrishi.minichallenges_pl.ui.theme.MiniChallenges_PLTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniChallenges_PLTheme {
                NavigationRoot(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
