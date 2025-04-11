package com.hrishi.minichallenges_pl.months.feb_2025.wave_form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hrishi.minichallenges_pl.R
import kotlinx.coroutines.delay

private object WaveformConstants {
    val WAVE_WIDTH = 4.dp
    val WAVE_PADDING = 2.dp
    val WAVE_HEIGHT_BASE = 14.dp
    const val HEIGHT_SCALE_FACTOR = 0.5f
    const val WAVE_BAR_CORNER_RADIUS = 16f
    const val FRAME_DELAY_MS = 16L
}

private object WaveformColors {
    val BACKGROUND = Color(0xFFEEF0FF)
    val WAVE_BASE = Color(0xFFBAC6E9)
    val WAVE_FILLED = Color(0xFF00419C)
    val ICON = Color(0xFF00419C)
    val TIME_TEXT = Color(0xFF40434F)
}

@Composable
internal fun AudioWaveformPlayer(
    modifier: Modifier = Modifier,
    durationMs: Long = 30000,
    backgroundColor: Color = WaveformColors.BACKGROUND,
    waveBaseColor: Color = WaveformColors.WAVE_BASE,
    waveFilledColor: Color = WaveformColors.WAVE_FILLED,
    iconColor: Color = WaveformColors.ICON,
    iconPlayRes: Int = R.drawable.ic_play,
    iconPauseRes: Int = R.drawable.ic_pause,
    amplitudes: List<Float>
) {

    var isPlaying by remember { mutableStateOf(false) }
    var elapsedTimeMs by remember { mutableLongStateOf(0L) }
    val progress = (elapsedTimeMs.toFloat() / durationMs).coerceIn(0f, 1f)
    val timeString = formatTimeString(elapsedTimeMs, durationMs)

    LaunchedEffect(isPlaying) {
        if (!isPlaying) return@LaunchedEffect

        val startTime = System.currentTimeMillis() - elapsedTimeMs
        while (elapsedTimeMs < durationMs) {
            elapsedTimeMs = System.currentTimeMillis() - startTime
            delay(WaveformConstants.FRAME_DELAY_MS)
        }
        isPlaying = false
        elapsedTimeMs = 0
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(percent = 50)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .padding(end = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            WaveFormPlayButton(
                isPlaying = isPlaying,
                iconColor = iconColor,
                iconPlayRes = iconPlayRes,
                iconPauseRes = iconPauseRes,
                onClick = { isPlaying = !isPlaying }
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(horizontal = 6.dp)
                    .drawBehind {
                        drawWaveform(
                            progress = progress,
                            waveBaseColor = waveBaseColor,
                            waveFilledColor = waveFilledColor,
                            waveHeightMultipliers = amplitudes
                        )
                    }
            )

            Text(text = timeString, color = WaveformColors.TIME_TEXT)
        }
    }
}

private fun DrawScope.drawWaveform(
    progress: Float,
    waveBaseColor: Color,
    waveFilledColor: Color,
    waveHeightMultipliers: List<Float>
) {
    val height = size.height
    val width = size.width

    val waveWidthPx = WaveformConstants.WAVE_WIDTH.toPx()
    val wavePaddingPx = WaveformConstants.WAVE_PADDING.toPx()
    val waveHeightBasePx = WaveformConstants.WAVE_HEIGHT_BASE.toPx()
    val totalWaveUnit = waveWidthPx + (wavePaddingPx * 2)

    val numberOfWaves = (width / totalWaveUnit).toInt().coerceAtLeast(1)

    drawWaveformBars(
        numberOfWaves = numberOfWaves,
        totalWaveUnit = totalWaveUnit,
        wavePaddingPx = wavePaddingPx,
        waveWidthPx = waveWidthPx,
        waveHeightBasePx = waveHeightBasePx,
        waveHeightMultipliers = waveHeightMultipliers,
        canvasHeight = height,
        canvasWidth = width,
        color = waveBaseColor
    )

    clipRect(right = width * progress) {
        drawWaveformBars(
            numberOfWaves = numberOfWaves,
            totalWaveUnit = totalWaveUnit,
            wavePaddingPx = wavePaddingPx,
            waveWidthPx = waveWidthPx,
            waveHeightBasePx = waveHeightBasePx,
            waveHeightMultipliers = waveHeightMultipliers,
            canvasHeight = height,
            canvasWidth = width,
            color = waveFilledColor
        )
    }
}

private fun DrawScope.drawWaveformBars(
    numberOfWaves: Int,
    totalWaveUnit: Float,
    wavePaddingPx: Float,
    waveWidthPx: Float,
    waveHeightBasePx: Float,
    waveHeightMultipliers: List<Float>,
    canvasHeight: Float,
    canvasWidth: Float,
    color: Color
) {
    for (i in 0 until numberOfWaves) {
        val multiplier = waveHeightMultipliers[i % waveHeightMultipliers.size]
        val waveHeight = waveHeightBasePx * multiplier * WaveformConstants.HEIGHT_SCALE_FACTOR
        val xPosition = (i * totalWaveUnit) + wavePaddingPx

        if (xPosition <= canvasWidth) {
            drawRoundRect(
                color = color,
                topLeft = Offset(
                    x = xPosition,
                    y = canvasHeight / 2 - waveHeight / 2
                ),
                size = Size(waveWidthPx, waveHeight),
                cornerRadius = CornerRadius(WaveformConstants.WAVE_BAR_CORNER_RADIUS)
            )
        }
    }
}

private fun formatTimeString(elapsedTimeMs: Long, totalDurationMs: Long): String {
    fun formatTime(timeMs: Long): String {
        val seconds = (timeMs / 1000).toInt()
        return "${seconds / 60}:${(seconds % 60).toString().padStart(2, '0')}"
    }

    return "${formatTime(elapsedTimeMs)}/${formatTime(totalDurationMs)}"
}

@Preview(showBackground = true)
@Composable
private fun PreviewWaveFormPlayer() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        val customAmplitudes = listOf(
            0.7f, 0.7f, 1f, 1.7f, 2f, 0.7f, 0.7f, 1f, 2.5f, 2.2f,
            1f, 0.7f, 0.7f, 0.7f, 1f, 2.2f, 2.5f, 1f, 0.7f, 0.7f,
            1.7f, 0.7f, 1f, 0.7f, 2.5f, 1f, 2.2f, 1.7f, 1f, 0.7f
        )

        AudioWaveformPlayer(amplitudes = customAmplitudes)
    }
}