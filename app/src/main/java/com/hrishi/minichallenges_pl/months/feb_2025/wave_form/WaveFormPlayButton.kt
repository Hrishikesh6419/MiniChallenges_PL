package com.hrishi.minichallenges_pl.months.feb_2025.wave_form

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hrishi.minichallenges_pl.R

@Composable
fun WaveFormPlayButton(
    modifier: Modifier = Modifier,
    size: Dp = 32.dp,
    isPlaying: Boolean = false,
    @DrawableRes iconPlayRes: Int = R.drawable.ic_play,
    @DrawableRes iconPauseRes: Int = R.drawable.ic_pause,
    @ColorInt iconColor: Color = Color(0xFF00419C),
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(size)
            .shadow(elevation = 2.dp, shape = CircleShape)
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .background(
                color = Color.White,
                shape = CircleShape
            )
    ) {
        Icon(
            tint = iconColor,
            modifier = Modifier
                .align(Alignment.Center)
                .then(
                    if (isPlaying) {
                        Modifier.padding(start = 0.dp)
                    } else {
                        Modifier.padding(start = 3.dp)
                    }
                ),
            imageVector = ImageVector.vectorResource(
                if (isPlaying) {
                    iconPauseRes
                } else {
                    iconPlayRes
                }
            ),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWaveFormPlayButton() {
    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        WaveFormPlayButton {

        }
        Spacer(modifier = Modifier.height(4.dp))
        WaveFormPlayButton(isPlaying = true) {

        }
    }
}