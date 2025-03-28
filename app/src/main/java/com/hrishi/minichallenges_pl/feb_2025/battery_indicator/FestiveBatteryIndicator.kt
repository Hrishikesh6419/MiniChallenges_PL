package com.hrishi.minichallenges_pl.feb_2025.battery_indicator

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hrishi.minichallenges_pl.R
import kotlinx.coroutines.delay

@Composable
fun FestiveBatteryIndicatorScreen(
    batteryPercentage: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BatteryIndicatorColors.Surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            HeartIndicator(batteryPercentage)
            BatteryIndicator(batteryPercentage)
            CloverIndicator(batteryPercentage)
        }
    }
}

@Composable
private fun HeartIndicator(batteryPercentage: Float) {
    val heartAnimator = rememberInfiniteTransition()
    val pulseAnimation = heartAnimator.animateFloat(
        initialValue = 1f,
        targetValue = 1.467f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val isLowBattery = batteryPercentage <= 0.2f
    val transitionFactor by animateFloatAsState(
        targetValue = if (isLowBattery) 1f else 0f,
        animationSpec = tween(1000)
    )

    val currentScale = if (transitionFactor > 0f) {
        1f + ((pulseAnimation.value - 1f) * transitionFactor)
    } else {
        1f
    }

    val heartColor = if (transitionFactor > 0f) {
        if (pulseAnimation.value >= 1.3f) {
            BatteryIndicatorColors.RedAlt
        } else {
            BatteryIndicatorColors.Red
        }
    } else {
        BatteryIndicatorColors.SurfaceLow
    }

    Icon(
        tint = heartColor,
        modifier = Modifier.scale(currentScale),
        imageVector = ImageVector.vectorResource(R.drawable.ic_heart),
        contentDescription = null
    )
}

@Composable
private fun BatteryIndicator(batteryPercentage: Float) {
    Box(
        modifier = Modifier
            .height(BatteryIndicatorStyle.height)
            .width(BatteryIndicatorStyle.width)
            .drawBehind {
                val batteryWidth = size.width
                val batteryHeight = size.height
                val cornerRadiusPx = BatteryIndicatorStyle.cornerRadius.toPx()
                val paddingPx = BatteryIndicatorStyle.padding.toPx()
                val snubWidthPx = BatteryIndicatorStyle.snubWidth.toPx()
                val snubHeightPx = BatteryIndicatorStyle.snubHeight.toPx()

                drawRoundRect(
                    color = Color.White,
                    topLeft = Offset(
                        batteryWidth - snubWidthPx * 2,
                        (batteryHeight / 2) - (snubHeightPx / 2)
                    ),
                    size = Size(snubWidthPx * 2, snubHeightPx),
                    cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                )

                drawRoundRect(
                    color = Color.White,
                    size = Size(
                        width = batteryWidth - snubWidthPx,
                        height = batteryHeight
                    ),
                    cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                )

                val fillWidth = (batteryWidth - snubWidthPx - paddingPx * 2) * batteryPercentage
                val batteryColor = when {
                    batteryPercentage <= 0.2f -> BatteryIndicatorColors.Red
                    batteryPercentage >= 0.8f -> BatteryIndicatorColors.Green
                    else -> BatteryIndicatorColors.Yellow
                }

                drawRoundRect(
                    color = batteryColor,
                    topLeft = Offset(paddingPx, paddingPx),
                    size = Size(
                        width = fillWidth,
                        height = batteryHeight - paddingPx * 2
                    ),
                    cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                )

                for (i in 1..<5) {
                    val block = (batteryWidth - paddingPx * 2 - snubWidthPx) / 5
                    drawRect(
                        color = Color.White,
                        topLeft = Offset(block * i, paddingPx),
                        size = Size(2.dp.toPx(), batteryHeight - paddingPx * 2),
                    )
                }
            }
    )
}

@Composable
private fun CloverIndicator(batteryPercentage: Float) {
    val image = ImageVector.vectorResource(R.drawable.ic_clover)
    val animateCloverSize by animateFloatAsState(
        targetValue = if (batteryPercentage >= 0.8f) 1.31f else 1f,
        animationSpec = tween(durationMillis = 1000)
    )

    val cloverColor = if (animateCloverSize > 1f) {
        BatteryIndicatorColors.Green
    } else {
        BatteryIndicatorColors.SurfaceLow
    }

    Icon(
        modifier = Modifier.scale(animateCloverSize),
        tint = cloverColor,
        imageVector = image,
        contentDescription = null
    )
}

private object BatteryIndicatorStyle {
    val height = 68.dp
    val width = 224.dp
    val cornerRadius = 16.dp
    val snubWidth = 5.dp
    val snubHeight = 25.dp
    val padding = 4.dp
}

private object BatteryIndicatorColors {
    val Red = Color(0xFFFF4E51)
    val RedAlt = Color(0xFFFE6264)
    val Yellow = Color(0xFFFCB966)
    val Green = Color(0xFF19D181)
    val Surface = Color(0xFFE7E9EF)
    val SurfaceLow = Color(0xFFC1C5D2)
}

@Preview(showBackground = true)
@Composable
private fun PreviewFestiveBatteryIndicator() {
    val animateValue = remember {
        Animatable(0f)
    }

    LaunchedEffect(Unit) {
        animateValue.animateTo(
            1f,
            animationSpec = tween(10000)
        )
        delay(2000)
        animateValue.animateTo(
            0f,
            animationSpec = tween(10000)
        )
    }

    FestiveBatteryIndicatorScreen(batteryPercentage = animateValue.value)
}