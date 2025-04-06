package com.hrishi.minichallenges_pl.months.march_2025.dawn_dusk_transition

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrishi.minichallenges_pl.R

object DawnDuskTheme {
    val StarActive = Color(0xFFFF9334)

    object Dark {
        val BackgroundStart = Color(0xFF210A41)
        val BackgroundEnd = Color(0xFF120327)
        val Card = Color(0xFF2C144D)
        val StarInactive = Color(0xFF482D6D)
        val Text = Color(0xFFE6E5FE)
    }

    object Light {
        val BackgroundStart = Color(0xFFC6ECFE)
        val BackgroundEnd = Color(0xFF6689F9)
        val Card = Color(0xFFFFFFFF)
        val StarInactive = Color(0xFFD7E4F7)
        val Text = Color(0xFF14042B)
    }

    val DarkBackgroundGradient = Brush.verticalGradient(
        colors = listOf(Dark.BackgroundEnd, Dark.BackgroundStart)
    )

    val LightBackgroundGradient = Brush.verticalGradient(
        colors = listOf(Light.BackgroundStart, Light.BackgroundEnd)
    )
}

private object Constants {
    const val STAR_PATH_FACTOR = 2.5f
    const val DEFAULT_ANIMATION_DURATION = 300
    const val TOTAL_STARS = 5

    val STAR_RATING_WIDTH = 272.dp
    val STAR_RATING_HEIGHT = 48.dp
    val TOP_SPACER_HEIGHT = 112.dp
    val ICON_SPACER_HEIGHT = 120.dp
    val TEXT_SPACER_HEIGHT = 12.dp
    val ICON_PADDING = 32.dp
    val STAR_PLATFORM_PADDING_HORIZONTAL = 28.dp
    val STAR_PLATFORM_PADDING_VERTICAL = 8.dp
    val STAR_PLATFORM_CORNER_RADIUS = 100.dp

    val QUESTION_TEXT_SIZE = 32.sp
}


private const val StarPath = "M21.077,1.22C21.418,0.399 22.582,0.399 22.923,1.22L28.114,13.699C28.257,14.045 28.583,14.281 28.957,14.311L42.429,15.392C43.315,15.462 43.675,16.569 42.999,17.148L32.735,25.94C32.451,26.184 32.326,26.567 32.413,26.931L35.549,40.078C35.755,40.943 34.814,41.627 34.055,41.163L22.521,34.118C22.201,33.923 21.799,33.923 21.479,34.118L9.945,41.163C9.186,41.627 8.245,40.943 8.451,40.078L11.587,26.931C11.674,26.567 11.55,26.184 11.265,25.94L1.001,17.148C0.325,16.569 0.685,15.462 1.571,15.392L15.043,14.311C15.417,14.281 15.743,14.045 15.887,13.699L21.077,1.22Z"

@Composable
fun DawnDuskTransitionScreenRoot(
    modifier: Modifier = Modifier
) {
    var rating by rememberSaveable { mutableFloatStateOf(1f) }
    val isDarkTheme = isSystemInDarkTheme()

    DayNightScreen(
        modifier = modifier,
        isDarkTheme = isDarkTheme,
        rating = rating,
        onRatingChanged = { rating = it }
    )
}

@Composable
private fun DayNightScreen(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    rating: Float,
    onRatingChanged: (Float) -> Unit
) {
    val backgroundGradient = if (isDarkTheme) {
        DawnDuskTheme.DarkBackgroundGradient
    } else {
        DawnDuskTheme.LightBackgroundGradient
    }

    val textColor = if (isDarkTheme) {
        DawnDuskTheme.Dark.Text
    } else {
        DawnDuskTheme.Light.Text
    }

    val iconRes = if (isDarkTheme) {
        R.drawable.ic_moon
    } else {
        R.drawable.ic_sun
    }

    val iconAlignment = if (isDarkTheme) {
        Alignment.End
    } else {
        Alignment.Start
    }

    val iconPadding = if (isDarkTheme) {
        Modifier.padding(end = Constants.ICON_PADDING)
    } else {
        Modifier.padding(start = Constants.ICON_PADDING)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundGradient)
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        Spacer(modifier = Modifier.height(Constants.TOP_SPACER_HEIGHT))

        Icon(
            modifier = Modifier
                .align(iconAlignment)
                .then(iconPadding),
            tint = Color.Unspecified,
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(Constants.ICON_SPACER_HEIGHT))

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "How was your day?",
            style = TextStyle(
                fontSize = Constants.QUESTION_TEXT_SIZE,
                fontWeight = FontWeight.SemiBold,
                color = textColor
            )
        )

        Spacer(modifier = Modifier.height(Constants.TEXT_SPACER_HEIGHT))

        StarRating(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(Constants.STAR_RATING_WIDTH)
                .height(Constants.STAR_RATING_HEIGHT),
            rating = rating,
            onRatingChanged = onRatingChanged,
            isDarkTheme = isDarkTheme
        )
    }
}

@Composable
fun ColumnScope.StarRating(
    modifier: Modifier = Modifier,
    rating: Float = 1f,
    onRatingChanged: (Float) -> Unit,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    animationDuration: Int = Constants.DEFAULT_ANIMATION_DURATION
) {
    val animatedRating = remember { Animatable(1f) }

    LaunchedEffect(rating) {
        animatedRating.animateTo(
            targetValue = rating,
            animationSpec = tween(durationMillis = animationDuration)
        )
    }

    val activeColor = DawnDuskTheme.StarActive
    val inactiveColor = if (isDarkTheme) {
        DawnDuskTheme.Dark.StarInactive
    } else {
        DawnDuskTheme.Light.StarInactive
    }
    val cardBackgroundColor = if (isDarkTheme) {
        DawnDuskTheme.Dark.Card
    } else {
        DawnDuskTheme.Light.Card
    }

    Row(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .background(
                cardBackgroundColor,
                RoundedCornerShape(Constants.STAR_PLATFORM_CORNER_RADIUS)
            )
            .padding(
                horizontal = Constants.STAR_PLATFORM_PADDING_HORIZONTAL,
                vertical = Constants.STAR_PLATFORM_PADDING_VERTICAL
            ),
        horizontalArrangement = Arrangement.Center,
    ) {
        Canvas(
            modifier = modifier
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val starWidth = size.width / Constants.TOTAL_STARS.toFloat()
                        val selectedStar = (offset.x / starWidth).toInt() + 1
                        onRatingChanged(selectedStar.toFloat())
                    }
                }
        ) {
            drawStarRating(
                totalStars = Constants.TOTAL_STARS,
                rating = animatedRating.value,
                activeColor = activeColor,
                inactiveColor = inactiveColor
            )
        }
    }
}

private fun DrawScope.drawStarRating(
    totalStars: Int,
    rating: Float,
    activeColor: Color,
    inactiveColor: Color
) {
    val starPath = PathParser().parsePathString(StarPath).toPath()
    val pathBounds = starPath.getBounds()
    val boxWidth = size.width / totalStars

    for (i in 0 until totalStars) {
        val centerX = i * boxWidth + (boxWidth / 2)
        val centerY = size.height / 2

        translate(
            left = centerX - (pathBounds.width * Constants.STAR_PATH_FACTOR / 2),
            top = centerY - (pathBounds.height * Constants.STAR_PATH_FACTOR / 2)
        ) {
            scale(scale = Constants.STAR_PATH_FACTOR, pivot = pathBounds.topLeft) {
                drawPath(
                    path = starPath,
                    color = inactiveColor
                )
            }

            if (i < rating) {
                scale(scale = Constants.STAR_PATH_FACTOR, pivot = pathBounds.topLeft) {
                    drawPath(
                        path = starPath,
                        color = activeColor
                    )
                }
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewDarkTheme() {
    DawnDuskTransitionScreenRoot()
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun PreviewLightTheme() {
    DawnDuskTransitionScreenRoot()
}