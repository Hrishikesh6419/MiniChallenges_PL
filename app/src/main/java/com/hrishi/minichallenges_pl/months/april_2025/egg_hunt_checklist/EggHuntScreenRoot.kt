package com.hrishi.minichallenges_pl.months.april_2025.egg_hunt_checklist

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrishi.minichallenges_pl.R
import com.hrishi.minichallenges_pl.core.utils.UpdateStatusBarAppearance
import com.hrishi.minichallenges_pl.months.april_2025.April2025Theme
import com.hrishi.minichallenges_pl.months.april_2025.April2025Theme.AprilYellowGradient
import com.hrishi.minichallenges_pl.months.april_2025.April2025Theme.BackgroundGradient
import com.hrishi.minichallenges_pl.months.april_2025.April2025Theme.BrownGradient
import com.hrishi.minichallenges_pl.months.april_2025.April2025Theme.DarkBlue
import com.hrishi.minichallenges_pl.months.april_2025.April2025Theme.TextColorGradient
import com.hrishi.minichallenges_pl.months.april_2025.AprilTypography.ChivoMonoExtraBold
import com.hrishi.minichallenges_pl.months.april_2025.AprilTypography.ChivoMonoMaxBold
import com.hrishi.minichallenges_pl.months.april_2025.AprilTypography.ChivoMonoMedium
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import kotlin.math.ceil

@Composable
fun EggHuntChecklistScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: EggHuntViewModel = koinViewModel()
) {
    UpdateStatusBarAppearance(isDarkStatusBarIcons = false)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    EggHuntChecklistScreen(
        modifier = modifier,
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
fun EggHuntChecklistScreen(
    modifier: Modifier = Modifier,
    uiState: EggHuntViewState,
    onAction: (EggHuntAction) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(brush = BackgroundGradient)
            .systemBarsPadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            EasterDialog(
                isSelected = uiState.showSelectedEasterContent,
                showDialog = uiState.showEasterDialog,
                easterFactResourceId = uiState.currentEasterFactResourceId,
                onDismissRequest = { onAction(EggHuntAction.OnDialogDismiss) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Egg Hunt Checklist",
                style = TextStyle(
                    brush = TextColorGradient,
                    fontFamily = ChivoMonoMaxBold,
                ),
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                modifier = Modifier.padding(horizontal = 48.dp),
                text = "Pick locations, where you've found eggs",
                style = TextStyle(
                    color = Color.White,
                    fontFamily = ChivoMonoMaxBold,
                    textAlign = TextAlign.Center
                ),
                fontSize = 20.sp
            )

            Text(
                text = "${uiState.numberOfEggsSelected}/${uiState.totalNumberOfEggs} eggs found",
                style = TextStyle(
                    color = April2025Theme.Yellow,
                    fontFamily = ChivoMonoMaxBold,
                    textAlign = TextAlign.Center
                ),
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        items(uiState.eggLocationData) { item ->
            EggCardItem(
                eggLocationData = item,
                onLocationSelected = { onAction(EggHuntAction.OnLocationChecked(it)) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Spacer(modifier = Modifier.height(18.dp))

            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(
                        brush = AprilYellowGradient,
                        shape = RoundedCornerShape(percent = 50)
                    )
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
                    .clickable { onAction(EggHuntAction.OnResetClicked) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Reset",
                    style = TextStyle(
                        color = Color.White,
                        fontFamily = ChivoMonoMedium,
                        textAlign = TextAlign.Center
                    ),
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun EggCardItem(
    modifier: Modifier = Modifier,
    eggLocationData: EggLocationData,
    onLocationSelected: (EggLocationData) -> Unit
) {
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .background(
                brush = if (eggLocationData.isSelected) AprilYellowGradient else April2025Theme.GrayGradient,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .clickable {
                onLocationSelected(eggLocationData.copy(isSelected = !eggLocationData.isSelected))
            }
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (eggLocationData.isSelected) {
                    Icon(
                        tint = Color.Unspecified,
                        imageVector = ImageVector.vectorResource(R.drawable.ic_selected),
                        contentDescription = null
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = stringResource(eggLocationData.location.displayString),
                style = TextStyle(
                    color = Color.White,
                    fontFamily = ChivoMonoExtraBold,
                    textAlign = TextAlign.Center
                ),
                fontSize = 18.sp
            )
        }
    }
}

@Composable
private fun EasterDialog(
    isSelected: Boolean,
    showDialog: Boolean,
    easterFactResourceId: Int,
    onDismissRequest: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismissRequest) {
            BoxWithConstraints {
                val maxDialogWidth = maxWidth - 40.dp
                Column(
                    modifier = Modifier
                        .background(
                            color = DarkBlue,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedEggTimer(
                        modifier = Modifier.width(maxDialogWidth),
                        onFinished = onDismissRequest
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = if (isSelected) "Easter fact!" else "Oops, the egg rolled away!",
                        style = TextStyle(
                            color = Color.White,
                            fontFamily = ChivoMonoExtraBold,
                            textAlign = TextAlign.Center
                        ),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(if (isSelected) 20.dp else 10.dp))

                    Icon(
                        tint = Color.Unspecified,
                        imageVector = ImageVector.vectorResource(
                            if (isSelected) R.drawable.ic_easter_bunny else R.drawable.ic_easter_egg
                        ),
                        contentDescription = null
                    )

                    if (isSelected) {
                        Text(
                            modifier = Modifier.padding(top = 12.dp),
                            text = stringResource(easterFactResourceId),
                            style = TextStyle(
                                brush = TextColorGradient,
                                fontFamily = ChivoMonoExtraBold,
                                textAlign = TextAlign.Center
                            ),
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = onDismissRequest,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 74.dp),
                        modifier = Modifier.background(
                            brush = AprilYellowGradient,
                            shape = RoundedCornerShape(8.dp)
                        )
                    ) {
                        Text("Dismiss")
                    }
                }
            }
        }
    }
}

@Composable
fun AnimatedEggTimer(
    totalTimeInMillis: Long = 4000L,
    modifier: Modifier = Modifier,
    onFinished: () -> Unit
) {
    var timeLeftMillis by remember { mutableLongStateOf(totalTimeInMillis) }
    val progress = timeLeftMillis / totalTimeInMillis.toFloat()
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 200)
    )
    val remainingSeconds = ceil(timeLeftMillis / 1000f).toInt()

    LaunchedEffect(Unit) {
        while (timeLeftMillis > 0) {
            delay(50)
            timeLeftMillis -= 50
        }
        delay(200)
        onFinished()
    }

    EggTimerProgressBar(
        progress = animatedProgress,
        remainingSeconds = remainingSeconds,
        modifier = modifier
    )
}

@Composable
fun EggTimerProgressBar(
    progress: Float,
    remainingSeconds: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(50))
                .background(brush = BrownGradient)
                .height(6.dp)
        ) {
            val fullWidthPx = constraints.maxWidth.toFloat()
            val currentWidth = fullWidthPx * progress

            Box(
                modifier = Modifier
                    .height(6.dp)
                    .width(with(LocalDensity.current) { currentWidth.toDp() })
                    .align(Alignment.CenterStart)
                    .clip(RoundedCornerShape(50))
                    .background(brush = AprilYellowGradient)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "${remainingSeconds}s",
            color = April2025Theme.Yellow,
            fontFamily = ChivoMonoExtraBold,
            fontSize = 18.sp
        )
    }
}

@Preview
@Composable
private fun PreviewEggHuntChecklistScreen() {
    val eggLocations = EggLocation.entries.map {
        EggLocationData(isSelected = false, location = it)
    }
    EggHuntChecklistScreen(
        uiState = EggHuntViewState(
            numberOfEggsSelected = 0,
            eggLocationData = eggLocations,
        ),
        onAction = {}
    )
}

@Preview
@Composable
private fun PreviewEggHuntChecklistScreenWithSelectedDialog() {
    val eggLocations = EggLocation.entries.map {
        EggLocationData(isSelected = false, location = it)
    }
    EggHuntChecklistScreen(
        uiState = EggHuntViewState(
            numberOfEggsSelected = 0,
            eggLocationData = eggLocations,
            showEasterDialog = true
        ),
        onAction = {}
    )
}

@Preview
@Composable
private fun PreviewEggHuntChecklistScreenWithDeselectedDialog() {
    val eggLocations = EggLocation.entries.map {
        EggLocationData(isSelected = false, location = it)
    }
    EggHuntChecklistScreen(
        uiState = EggHuntViewState(
            numberOfEggsSelected = 0,
            eggLocationData = eggLocations,
            showEasterDialog = true,
            showSelectedEasterContent = true
        ),
        onAction = {}
    )
}