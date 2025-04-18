package com.hrishi.minichallenges_pl.months.march_2025.space_craft_flip_cards

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hrishi.minichallenges_pl.R
import com.hrishi.minichallenges_pl.core.utils.UpdateStatusBarAppearance

@Composable
fun SpaceCraftFlipCardsScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: SpaceCraftViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    UpdateStatusBarAppearance(isDarkStatusBarIcons = false)

    SpaceCraftFlipCardsScreen(
        spaceCrafts = uiState.spaceCrafts,
        isLoading = uiState.isLoading,
        modifier = modifier
    )
}

@Composable
fun SpaceCraftFlipCardsScreen(
    spaceCrafts: List<SpaceCraft>,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    var selectedSpacecraftIndex by remember { mutableIntStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .background(brush = SpaceCraftTheme.BackgroundGradient)
            .fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = SpaceCraftTheme.FrontSidePrimary
            )
        } else if (spaceCrafts.isNotEmpty()) {
            val currentSpacecraft = spaceCrafts[selectedSpacecraftIndex]

            SpaceCraftCard(
                spacecraft = currentSpacecraft,
                isFlipped = isFlipped,
                onFlip = { isFlipped = !isFlipped },
                modifier = Modifier.align(Alignment.Center)
            )

            if (spaceCrafts.size > 1) {
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = SpaceCraftConstants.PAGINATION_BOTTOM_PADDING_DP.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        SpaceCraftConstants.PAGINATION_DOT_SPACING_DP.dp
                    )
                ) {
                    spaceCrafts.forEachIndexed { index, _ ->
                        PaginationDot(
                            isSelected = index == selectedSpacecraftIndex,
                            onClick = {
                                selectedSpacecraftIndex = index
                                isFlipped = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PaginationDot(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(SpaceCraftConstants.PAGINATION_DOT_SIZE_DP.dp)
            .background(
                color = if (isSelected) {
                    SpaceCraftTheme.FrontSidePrimary
                } else {
                    SpaceCraftTheme.FrontSideSecondary.copy(alpha = 0.5f)
                },
                shape = CircleShape
            )
            .clickable(onClick = onClick)
    )
}

@Composable
fun CardFront(
    spacecraftName: String,
    crewCount: Int,
    modifier: Modifier = Modifier
) {
    val cutSizePx = with(LocalDensity.current) {
        SpaceCraftConstants.CARD_CORNER_CUT_SIZE_DP.dp.toPx()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(SpaceCraftShapes.cardWithDiagonalCutFront(cutSizePx))
            .background(color = SpaceCraftTheme.FrontSide)
            .padding(
                vertical = SpaceCraftConstants.CONTENT_PADDING_VERTICAL_DP.dp,
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_spacecraft_front),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        Icon(
            tint = Color.Unspecified,
            imageVector = ImageVector.vectorResource(R.drawable.ic_spacecraft_front_union),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    top = SpaceCraftConstants.ICON_PADDING_TOP_DP.dp,
                    start = SpaceCraftConstants.ICON_PADDING_SIDE_DP.dp
                )
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = spacecraftName,
                    style = TextStyle(
                        fontFamily = SpaceCraftTypography.ChivoMonoMedium,
                        fontSize = SpaceCraftConstants.TITLE_FONT_SIZE_SP.sp,
                        color = SpaceCraftTheme.FrontSidePrimary
                    )
                )

                Text(
                    text = "$crewCount crew members",
                    style = TextStyle(
                        fontFamily = SpaceCraftTypography.ChivoMonoRegular,
                        fontSize = SpaceCraftConstants.SUBTITLE_FONT_SIZE_SP.sp,
                        color = SpaceCraftTheme.FrontSideSecondary
                    )
                )
            }
        }
    }
}

@Composable
fun CardBack(
    crewMembers: List<String>,
    modifier: Modifier = Modifier
) {
    val cutSizePx = with(LocalDensity.current) {
        SpaceCraftConstants.CARD_CORNER_CUT_SIZE_DP.dp.toPx()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer { rotationY = 180f }
            .clip(SpaceCraftShapes.cardWithDiagonalCutBack(cutSizePx))
            .background(color = SpaceCraftTheme.BackSide)
            .padding(
                vertical = SpaceCraftConstants.CONTENT_PADDING_VERTICAL_DP.dp,
                horizontal = SpaceCraftConstants.CONTENT_PADDING_HORIZONTAL_DP.dp
            )
    ) {
        Icon(
            tint = Color.Unspecified,
            imageVector = ImageVector.vectorResource(R.drawable.ic_spacecraft_back_union),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    top = SpaceCraftConstants.ICON_PADDING_TOP_DP.dp,
                    end = SpaceCraftConstants.ICON_PADDING_SIDE_DP.dp
                )
        )

        Image(
            painter = painterResource(id = R.drawable.ic_spacecraft_back),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(
                    start = SpaceCraftConstants.CREW_LIST_PADDING_START_DP.dp,
                    top = SpaceCraftConstants.CREW_LIST_PADDING_TOP_DP.dp
                )
        ) {
            crewMembers.forEachIndexed { index, name ->
                Text(
                    text = "${index + 1}. $name",
                    style = TextStyle(
                        fontFamily = SpaceCraftTypography.ChivoMonoMedium,
                        fontSize = SpaceCraftConstants.CREW_MEMBER_FONT_SIZE_SP.sp,
                        color = SpaceCraftTheme.BackSideText
                    ),
                    modifier = Modifier.padding(
                        vertical = SpaceCraftConstants.CREW_ITEM_PADDING_VERTICAL_DP.dp
                    )
                )
            }
        }
    }
}

@Composable
fun SpaceCraftCard(
    spacecraft: SpaceCraft,
    isFlipped: Boolean,
    onFlip: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = SpaceCraftConstants.FLIP_ANIMATION_DURATION_MS)
    )

    Box(
        modifier = modifier
            .height(SpaceCraftConstants.CARD_HEIGHT_DP.dp)
            .width(SpaceCraftConstants.CARD_WIDTH_DP.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = SpaceCraftConstants.CAMERA_DISTANCE_MULTIPLIER * density
            }
            .clickable { onFlip() }
    ) {
        if (rotation < SpaceCraftConstants.FLIP_MIDPOINT_DEGREES) {
            CardFront(
                spacecraftName = spacecraft.name,
                crewCount = spacecraft.crewMembers.size
            )
        } else {
            CardBack(
                crewMembers = spacecraft.crewMembers.map { it.name }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSpaceCraftFlipCardsScreen() {
    val mockSpaceCrafts = listOf(
        SpaceCraft(
            name = "ISS Spacecraft",
            crewMembers = listOf(
                Astronaut("Oleg Kononenko"),
                Astronaut("Nikolai Chub"),
                Astronaut("Tracy Caldwell Dyson"),
                Astronaut("Matthew Dominick"),
                Astronaut("Michael Barratt"),
                Astronaut("Jeanette Epps")
            )
        ),
        SpaceCraft(
            name = "Tiangong",
            crewMembers = listOf(
                Astronaut("Li Guangsu"),
                Astronaut("Li Cong"),
                Astronaut("Ye Guangfu")
            )
        )
    )

    SpaceCraftFlipCardsScreen(spaceCrafts = mockSpaceCrafts)
}

object SpaceCraftConstants {
    const val FLIP_ANIMATION_DURATION_MS = 500
    const val CAMERA_DISTANCE_MULTIPLIER = 12f
    const val FLIP_MIDPOINT_DEGREES = 90f

    const val CARD_HEIGHT_DP = 380
    const val CARD_WIDTH_DP = 310
    const val CARD_CORNER_CUT_SIZE_DP = 34f

    const val CONTENT_PADDING_VERTICAL_DP = 9
    const val CONTENT_PADDING_HORIZONTAL_DP = 7
    const val ICON_PADDING_TOP_DP = 12
    const val ICON_PADDING_SIDE_DP = 12
    const val CREW_LIST_PADDING_START_DP = 48
    const val CREW_LIST_PADDING_TOP_DP = 54
    const val CREW_ITEM_PADDING_VERTICAL_DP = 2
    const val PAGINATION_DOT_SIZE_DP = 12
    const val PAGINATION_DOT_SPACING_DP = 8
    const val PAGINATION_BOTTOM_PADDING_DP = 32

    const val TITLE_FONT_SIZE_SP = 28
    const val SUBTITLE_FONT_SIZE_SP = 16
    const val CREW_MEMBER_FONT_SIZE_SP = 16
}

object SpaceCraftTheme {
    val BackgroundStart = Color(0xFF210A41)
    val BackgroundEnd = Color(0xFF120327)
    val FrontSide = Color(0xFF420794)
    val FrontSidePrimary = Color(0xFFE6E5FE)
    val FrontSideSecondary = Color(0xFFB1AEFC)
    val BackSide = Color(0xFFCAD5FC)
    val BackSideText = Color(0xFF1E093B)

    val BackgroundGradient = Brush.verticalGradient(
        colors = listOf(BackgroundStart, BackgroundEnd)
    )
}

object SpaceCraftTypography {
    @OptIn(ExperimentalTextApi::class)
    val ChivoMonoRegular = FontFamily(
        Font(
            R.font.chivmono_variablefont_weight,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(400)
            )
        )
    )

    @OptIn(ExperimentalTextApi::class)
    val ChivoMonoMedium = FontFamily(
        Font(
            R.font.chivmono_variablefont_weight,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(500)
            )
        )
    )
}

object SpaceCraftShapes {
    fun cardWithDiagonalCutFront(cutSize: Float): Shape {
        return GenericShape { size, _ ->
            moveTo(0f, 0f)
            lineTo(size.width - cutSize, 0f)
            lineTo(size.width, cutSize)
            lineTo(size.width, size.height)
            lineTo(cutSize, size.height)
            lineTo(0f, size.height - cutSize)
            close()
        }
    }

    fun cardWithDiagonalCutBack(cutSize: Float): Shape {
        return GenericShape { size, _ ->
            moveTo(cutSize, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height - cutSize)
            lineTo(size.width - cutSize, size.height)
            lineTo(0f, size.height)
            lineTo(0f, cutSize)
            close()
        }
    }
}

data class Astronaut(val name: String)

data class SpaceCraft(
    val name: String,
    val crewMembers: List<Astronaut>
)