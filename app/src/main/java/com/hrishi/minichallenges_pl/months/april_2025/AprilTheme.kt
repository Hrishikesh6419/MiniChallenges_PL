package com.hrishi.minichallenges_pl.months.april_2025

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object April2025Theme {
    private val BackgroundStart = Color(0xFF0F1241)
    private val BackgroundEnd = Color(0xFF05061E)
    val BackgroundGradient = Brush.verticalGradient(
        colors = listOf(BackgroundStart, BackgroundEnd)
    )

    private val TextColorStart = Color(0xFFFFD343)
    private val TextColorMiddle = Color(0xFFFFD64D)
    private val TextColorEnd = Color(0xFFFFB619)
    val TextColorGradient = Brush.verticalGradient(
        colors = listOf(TextColorStart, TextColorMiddle, TextColorEnd)
    )

    val GrayGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF8D8EA1),
            Color(0xFF8D8EA1)
        )
    )

    val BrownGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF894621),
            Color(0xFF894621)
        )
    )

    private val YellowGradientColorStart = Color(0xFFFFC441)
    private val YellowGradientColorMiddle1 = Color(0xFFEDA616)
    private val YellowGradientColorMiddle2 = Color(0xFFE09723)
    private val YellowGradientColorEnd = Color(0xFFCA7500)
    val AprilYellowGradient = Brush.verticalGradient(
        colors = listOf(
            YellowGradientColorStart,
            YellowGradientColorMiddle1,
            YellowGradientColorMiddle2,
            YellowGradientColorEnd
        )
    )

    val Yellow = Color(0xFFFFF583)
    val Gray = Color(0xFF8D8EA1)
    val DarkBlue = Color(0xFF10122C)
}

