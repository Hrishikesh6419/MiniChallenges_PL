package com.hrishi.minichallenges_pl.months.april_2025.shaky_egg

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.hrishi.minichallenges_pl.R
import com.hrishi.minichallenges_pl.months.april_2025.AprilTypography.ChivoMonoExtraBold
import com.hrishi.minichallenges_pl.months.april_2025.AprilTypography.ChivoMonoMaxBold
import androidx.compose.material3.MaterialTheme
import com.hrishi.minichallenges_pl.months.april_2025.april2025
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun ShakyEggScreenRoot(modifier: Modifier = Modifier) {
    var isShaking by remember { mutableStateOf(true) }

    var showSnackbar by remember { mutableStateOf(false) }

    var isReady by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val infiniteTransition = rememberInfiniteTransition()

    val rotationZ by infiniteTransition.animateFloat(
        initialValue = -4f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(180, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val rotationX by infiniteTransition.animateFloat(
        initialValue = -2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(220, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val translateX by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(160, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val translateY by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    fun restartShakingAfterDelay() {
        coroutineScope.launch {
            val delayTime = Random.nextInt(4000, 6001)
            delay(delayTime.toLong())
            isShaking = true
        }
    }

    fun showSnackbarWithDelay(ready: Boolean) {
        isReady = ready
        showSnackbar = true

        coroutineScope.launch {
            delay(3000)
            showSnackbar = false
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = MaterialTheme.colorScheme.april2025.BackgroundGradient)
        ) {
            Spacer(modifier = Modifier.height(72.dp))

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Shaky Egg",
                style = TextStyle(
                    brush = MaterialTheme.colorScheme.april2025.TextColorGradient,
                    fontFamily = ChivoMonoMaxBold,
                ),
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .graphicsLayer {
                        if (isShaking) {
                            this.rotationZ = rotationZ
                            this.rotationX = rotationX
                            this.translationX = translateX
                            this.translationY = translateY
                        }
                    }
                    .clickable {
                        if (isShaking) {
                            isShaking = false
                            showSnackbarWithDelay(true)
                            restartShakingAfterDelay()
                        } else {
                            showSnackbarWithDelay(false)
                        }
                    },
                tint = Color.Unspecified,
                imageVector = ImageVector.vectorResource(R.drawable.ic_shaky_egg),
                contentDescription = null
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        AnimatedVisibility(
            visible = showSnackbar,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 190.dp)
                .zIndex(10f)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        brush = if (isReady) {
                            MaterialTheme.colorScheme.april2025.AprilYellowGradient
                        } else {
                            MaterialTheme.colorScheme.april2025.GrayGradient
                        },
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = if (isReady) {
                        "You've hatched it!"
                    } else {
                        "Easter egg not ready yet!"
                    },
                    color = Color.White,
                    fontFamily = ChivoMonoExtraBold,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun ShakyEggScreenRoute() {
    ShakyEggScreenRoot()
}