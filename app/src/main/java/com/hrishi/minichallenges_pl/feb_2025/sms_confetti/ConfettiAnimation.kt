package com.hrishi.minichallenges_pl.feb_2025.sms_confetti

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun ConfettiAnimation(
    modifier: Modifier = Modifier,
    confettiCount: Int = 500,
    groupCount: Int = 8,
    delayBetweenGroups: Long = 500,
    fadeOut: Boolean = true,
    content: @Composable () -> Unit = {}
) {
    var canvasHeight by remember { mutableFloatStateOf(0f) }
    var canvasWidth by remember { mutableFloatStateOf(0f) }

    val confetti = remember {
        List(confettiCount) {
            ConfettiStyle(
                size = Size(
                    width = Random.nextFloat() * (MAX_SIZE - MIN_SIZE) + MIN_SIZE,
                    height = Random.nextFloat() * (MAX_SIZE - MIN_SIZE) + MIN_SIZE
                ),
                color = CONFETTI_COLORS.random(),
                shape = ConfettiShape.entries.random(),
                startX = Random.nextFloat(),
                drift = (Random.nextFloat() * 2 * MAX_DRIFT_FACTOR) - MAX_DRIFT_FACTOR,
                animDelay = Random.nextInt(MAX_INITIAL_DELAY),
                animDuration = Random.nextInt(MIN_ANIM_DURATION, MAX_ANIM_DURATION),
                rotationSpeed = Random.nextFloat() * (MAX_ROTATION_SPEED - MIN_ROTATION_SPEED) + MIN_ROTATION_SPEED
            )
        }
    }

    val confettiGroups = remember {
        confetti.chunked(confetti.size / groupCount)
    }

    val animationProgress = confetti.map {
        remember { Animatable(initialValue = -0.1f) }
    }

    LaunchedEffect(Unit) {
        confettiGroups.forEachIndexed { groupIndex, group ->
            if (groupIndex > 0) {
                delay(delayBetweenGroups)
            }

            group.forEachIndexed { confettiIndex, confettiStyle ->
                val absoluteIndex = (groupIndex * (confetti.size / groupCount)) + confettiIndex

                launch {
                    delay(confettiStyle.animDelay.toLong())
                    animationProgress[absoluteIndex].animateTo(
                        1f,
                        animationSpec = tween(
                            confettiStyle.animDuration,
                            easing = LinearEasing
                        )
                    )
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                canvasHeight = size.height
                canvasWidth = size.width

                confetti.forEachIndexed { index, confettiStyle ->
                    val progress = animationProgress[index].value
                    if (progress < 0) return@forEachIndexed

                    val startX = confettiStyle.startX * canvasWidth
                    val drift = confettiStyle.drift * canvasWidth
                    val x = (startX + drift * progress).coerceIn(
                        minimumValue = confettiStyle.size.width / 2,
                        maximumValue = canvasWidth - confettiStyle.size.width / 2
                    )

                    val y = progress * canvasHeight * 1.2f
                    val alpha = if (fadeOut) 1f - progress else 1f

                    rotate(degrees = progress * confettiStyle.rotationSpeed, pivot = Offset(x, y)) {
                        drawConfettiShape(
                            confettiStyle = confettiStyle,
                            position = Offset(x, y),
                            alpha = alpha
                        )
                    }
                }
            }
    ) {
        content()
    }
}

private fun DrawScope.drawConfettiShape(
    confettiStyle: ConfettiStyle,
    position: Offset,
    alpha: Float
) {
    val x = position.x
    val y = position.y

    when (confettiStyle.shape) {
        ConfettiShape.CIRCLE -> drawCircle(
            color = confettiStyle.color,
            radius = confettiStyle.size.width / 2,
            center = position,
            alpha = alpha
        )

        ConfettiShape.RECTANGLE -> drawRect(
            color = confettiStyle.color,
            topLeft = Offset(
                x - confettiStyle.size.width / 2f,
                y - confettiStyle.size.height / 2f
            ),
            size = confettiStyle.size,
            alpha = alpha
        )

        ConfettiShape.TRIANGLE -> {
            val path = Path().apply {
                moveTo(x, y - confettiStyle.size.height / 2f)
                lineTo(x - confettiStyle.size.width / 2f, y + confettiStyle.size.height / 2f)
                lineTo(x + confettiStyle.size.width / 2f, y + confettiStyle.size.height / 2f)
                close()
            }
            drawPath(
                path = path,
                color = confettiStyle.color,
                alpha = alpha
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewConfettiAnimation() {
    ConfettiAnimation()
}

private const val MIN_SIZE = 10f
private const val MAX_SIZE = 40f
private const val MIN_ANIM_DURATION = 1000
private const val MAX_ANIM_DURATION = 2000
private const val MIN_ROTATION_SPEED = 60f
private const val MAX_ROTATION_SPEED = 180f
private const val MAX_DRIFT_FACTOR = 0.3f
private const val MAX_INITIAL_DELAY = 500

private val CONFETTI_COLORS = listOf(
    Color(0xFFF94144),
    Color(0xFFF3722C),
    Color(0xFFF8961E),
    Color(0xFFF9C74F),
    Color(0xFF90BE6D),
    Color(0xFF43AA8B),
    Color(0xFF577590)
)

private data class ConfettiStyle(
    val size: Size,
    val color: Color,
    val shape: ConfettiShape,
    val startX: Float,
    val drift: Float,
    val animDelay: Int,
    val animDuration: Int,
    val rotationSpeed: Float
)

private enum class ConfettiShape {
    CIRCLE,
    TRIANGLE,
    RECTANGLE
}