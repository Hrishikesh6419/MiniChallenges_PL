package com.hrishi.minichallenges_pl.march_2025.mars_weather_card

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MarsWeatherCardScreenRoot(
    modifier: Modifier = Modifier
) {
    Text("Mars Weather Card")
}

@Preview
@Composable
fun PreviewDawnDuskTransitionScreen() {
    MarsWeatherCardScreenRoot()
}