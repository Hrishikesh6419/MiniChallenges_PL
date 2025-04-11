package com.hrishi.minichallenges_pl.months.feb_2025.wave_form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AudioWaveFormScreen(
    modifier: Modifier = Modifier
) {
    val customAmplitudes = listOf(
        0.7f, 0.7f, 1f, 1.7f, 2f, 0.7f, 0.7f, 1f, 2.5f, 2.2f,
        1f, 0.7f, 0.7f, 0.7f, 1f, 2.2f, 2.5f, 1f, 0.7f, 0.7f,
        1.7f, 0.7f, 1f, 0.7f, 2.5f, 1f, 2.2f, 1.7f, 1f, 0.7f
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        AudioWaveformPlayer(
            amplitudes = customAmplitudes
        )
    }
}

@Preview
@Composable
private fun PreviewAudioWaveFormScreen() {
    AudioWaveFormScreen()
}