package com.hrishi.minichallenges_pl.months.march_2025.gravity_tilt

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.hrishi.minichallenges_pl.R
import com.hrishi.minichallenges_pl.core.utils.UpdateStatusBarAppearance

@Composable
fun GravityTiltScreenRoot(
    modifier: Modifier = Modifier
) {
    UpdateStatusBarAppearance(isDarkStatusBarIcons = false)

    var isEarthMode by remember { mutableStateOf(true) }
    var motionState by remember { mutableStateOf(MotionState()) }
    var previousTime by remember { mutableLongStateOf(0L) }
    var screenSize by remember { mutableStateOf(Offset(0f, 0f)) }

    val physicsParams = remember(isEarthMode) {
        PhysicsParams(
            gravity = if (isEarthMode) MarsGravityConstants.EARTH_GRAVITY else MarsGravityConstants.MARS_GRAVITY,
            damping = if (isEarthMode) MarsGravityConstants.EARTH_DAMPING else MarsGravityConstants.MARS_DAMPING,
            maxSpeed = if (isEarthMode) MarsGravityConstants.EARTH_MAX_SPEED else MarsGravityConstants.MARS_MAX_SPEED
        )
    }

    val density = LocalDensity.current
    val context = LocalContext.current

    val tiltSensorManager = remember {
        TiltSensorManager(context) { tilt, currentTime ->
            if (previousTime == 0L) {
                previousTime = currentTime
                return@TiltSensorManager
            }

            val deltaTime = (currentTime - previousTime) / 1000f
            previousTime = currentTime

            val spacecraftSizeInPixels = with(density) {
                MarsGravityConstants.SPACECRAFT_SIZE.dp.toPx()
            }

            motionState = PhysicsEngine.updateMotion(
                currentState = motionState,
                tilt = tilt,
                deltaTime = deltaTime,
                physicsParams = physicsParams,
                screenSize = screenSize,
                spacecraftSize = spacecraftSizeInPixels
            )
        }
    }

    DisposableEffect(tiltSensorManager, isEarthMode) {
        tiltSensorManager.startListening()
        onDispose {
            tiltSensorManager.stopListening()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MarsGravityTheme.BackgroundGradient)
            .onSizeChanged { size ->
                screenSize = Offset(size.width.toFloat(), size.height.toFloat())
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_mars_surface),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        GravityToggle(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = MarsGravityConstants.TOGGLE_TOP_PADDING.dp),
            isEarthMode = isEarthMode,
            onToggle = {
                isEarthMode = !isEarthMode
                motionState = motionState.copy(velocity = Offset(0f, 0f))
            }
        )

        Spacecraft(
            modifier = Modifier
                .align(Alignment.Center)
                .offset {
                    IntOffset(
                        motionState.position.x.toInt(),
                        motionState.position.y.toInt()
                    )
                }
        )
    }
}

@Composable
private fun GravityToggle(
    modifier: Modifier = Modifier,
    isEarthMode: Boolean,
    onToggle: () -> Unit
) {
    val indicatorOffset by animateFloatAsState(
        targetValue = if (isEarthMode) 0f else (MarsGravityConstants.TOGGLE_WIDTH - MarsGravityConstants.INDICATOR_SIZE - 8).toFloat(),
        animationSpec = tween(
            durationMillis = MarsGravityConstants.TOGGLE_ANIMATION_DURATION,
            easing = LinearEasing
        )
    )

    Box(
        modifier = modifier
            .size(
                width = MarsGravityConstants.TOGGLE_WIDTH.dp,
                height = MarsGravityConstants.TOGGLE_HEIGHT.dp
            )
            .clip(CircleShape)
            .background(MarsGravityTheme.ToggleBackground)
            .clickable(onClick = onToggle)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRoundRect(
                color = MarsGravityTheme.ToggleBorder.copy(alpha = 0.3f),
                style = Stroke(width = 2.dp.toPx()),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(MarsGravityConstants.TOGGLE_HEIGHT.dp.toPx() / 2)
            )
        }

        Box(
            modifier = Modifier
                .padding(4.dp)
                .size(MarsGravityConstants.INDICATOR_SIZE.dp)
                .offset { IntOffset(indicatorOffset.dp.roundToPx(), 0) }
        ) {
            Icon(
                painter = painterResource(
                    id = if (isEarthMode) R.drawable.ic_earth else R.drawable.ic_gravity_mars
                ),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun Spacecraft(
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_ufo),
        contentDescription = null,
        tint = Color.Unspecified,
        modifier = modifier.size(MarsGravityConstants.SPACECRAFT_SIZE.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun MarsGravitySimulatorPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MarsGravityTheme.BackgroundGradient)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_mars_surface),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        GravityToggle(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = MarsGravityConstants.TOGGLE_TOP_PADDING.dp),
            isEarthMode = true,
            onToggle = {}
        )

        Spacecraft(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}