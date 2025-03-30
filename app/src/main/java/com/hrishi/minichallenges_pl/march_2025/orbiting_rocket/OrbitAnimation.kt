package com.hrishi.minichallenges_pl.march_2025.orbiting_rocket

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.res.imageResource
import com.hrishi.minichallenges_pl.R
import com.hrishi.minichallenges_pl.core.utils.UpdateStatusBarAppearance
import kotlin.math.atan2

object OrbitTheme {
    val BackgroundStart = Color(0xFF210A41)
    val BackgroundEnd = Color(0xFF120327)
    val OrbitStart = Color(0xFFD8BEFF)
    val OrbitEnd = Color(0xFFFFFFFF)

    val BackgroundGradient = Brush.verticalGradient(
        colors = listOf(BackgroundStart, BackgroundEnd)
    )

    val OrbitGradient = Brush.linearGradient(
        colors = listOf(OrbitStart, OrbitEnd)
    )
}

object OrbitConstants {
    const val ORBIT_DURATION = 2500
    const val EARTH_BOUNCE_DURATION = 700
    const val EARTH_BOUNCE_AMOUNT = 20f
    const val EARTH_SCALE = 3f
    const val ROCKET_SCALE = 3f
    const val ORBIT_STROKE_WIDTH = 8f
    const val ORBIT_TILT = -20f

    const val OUTER_CIRCLE_ALPHA = 0.02f
    const val INNER_CIRCLE_ALPHA = 0.04f
    const val OUTER_CIRCLE_RADIUS_FACTOR = 0.35f
    const val INNER_CIRCLE_RADIUS_FACTOR = 0.22f

    const val HORIZONTAL_RADIUS_FACTOR = 0.4f
    const val VERTICAL_RADIUS_FACTOR = 0.1f
}

@Composable
fun OrbitAnimation(
    modifier: Modifier = Modifier
) {
    UpdateStatusBarAppearance(isDarkStatusBarIcons = false)

    val orbitProgress = rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(OrbitConstants.ORBIT_DURATION, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val earthBouncing = rememberInfiniteTransition().animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(OrbitConstants.EARTH_BOUNCE_DURATION, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        )
    )

    val rocketImage = ImageBitmap.imageResource(id = R.drawable.ic_rocket_1)
    val earthImage = ImageBitmap.imageResource(id = R.drawable.ic_earth)

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(OrbitTheme.BackgroundGradient)
    ) {
        val center = Offset(size.width / 2f, size.height / 2)

        drawCircle(
            color = Color.White.copy(alpha = OrbitConstants.OUTER_CIRCLE_ALPHA),
            radius = size.height * OrbitConstants.OUTER_CIRCLE_RADIUS_FACTOR,
            center = center
        )

        drawCircle(
            color = Color.White.copy(alpha = OrbitConstants.INNER_CIRCLE_ALPHA),
            radius = size.height * OrbitConstants.INNER_CIRCLE_RADIUS_FACTOR,
            center = center
        )

        val earthOffset = earthBouncing.value * OrbitConstants.EARTH_BOUNCE_AMOUNT

        val horizontalRadius = size.width * OrbitConstants.HORIZONTAL_RADIUS_FACTOR
        val verticalRadius = size.height * OrbitConstants.VERTICAL_RADIUS_FACTOR

        val ovalPath = Path().apply {
            addOval(
                androidx.compose.ui.geometry.Rect(
                    center.x - horizontalRadius,
                    center.y - verticalRadius,
                    center.x + horizontalRadius,
                    center.y + verticalRadius
                )
            )
        }

        val pathMeasure = android.graphics.PathMeasure(ovalPath.asAndroidPath(), true)
        val pathLength = pathMeasure.length

        val backHalfPath = android.graphics.Path()
        pathMeasure.getSegment(0f, pathLength / 2, backHalfPath, true)

        val frontHalfPath = android.graphics.Path()
        pathMeasure.getSegment(pathLength / 2, pathLength, frontHalfPath, true)

        val pos = FloatArray(2)
        val tan = FloatArray(2)
        pathMeasure.getPosTan((1 - orbitProgress.value) * pathLength, pos, tan)

        val rotationRadians = Math.toRadians(OrbitConstants.ORBIT_TILT.toDouble())
        val x = center.x + (pos[0] - center.x) * Math.cos(rotationRadians).toFloat() -
                (pos[1] - center.y) * Math.sin(rotationRadians).toFloat()
        val y = center.y + (pos[0] - center.x) * Math.sin(rotationRadians).toFloat() +
                (pos[1] - center.y) * Math.cos(rotationRadians).toFloat()

        val degrees = Math.toDegrees(atan2(-tan[1], -tan[0]).toDouble())
            .toFloat() - 270f + OrbitConstants.ORBIT_TILT

        val rocketBehindEarth = (1 - orbitProgress.value) < 0.5

        rotate(OrbitConstants.ORBIT_TILT, center) {
            drawPath(
                path = backHalfPath.asComposePath(),
                brush = OrbitTheme.OrbitGradient,
                style = Stroke(width = OrbitConstants.ORBIT_STROKE_WIDTH)
            )
        }

        if (rocketBehindEarth) {
            rotate(degrees = degrees, pivot = Offset(x, y)) {
                scale(scale = OrbitConstants.ROCKET_SCALE, pivot = Offset(x, y)) {
                    drawImage(
                        image = rocketImage,
                        topLeft = Offset(x - rocketImage.width / 2f, y - rocketImage.height / 2f)
                    )
                }
            }
        }

        scale(scale = OrbitConstants.EARTH_SCALE, pivot = center) {
            drawImage(
                image = earthImage,
                topLeft = Offset(
                    center.x - earthImage.width / 2f,
                    center.y - earthImage.height / 2f + earthOffset / OrbitConstants.EARTH_SCALE
                )
            )
        }

        rotate(OrbitConstants.ORBIT_TILT, center) {
            drawPath(
                path = frontHalfPath.asComposePath(),
                brush = OrbitTheme.OrbitGradient,
                style = Stroke(width = OrbitConstants.ORBIT_STROKE_WIDTH)
            )
        }

        if (!rocketBehindEarth) {
            rotate(degrees = degrees, pivot = Offset(x, y)) {
                scale(scale = OrbitConstants.ROCKET_SCALE, pivot = Offset(x, y)) {
                    drawImage(
                        image = rocketImage,
                        topLeft = Offset(x - rocketImage.width / 2f, y - rocketImage.height / 2f)
                    )
                }
            }
        }
    }
}
