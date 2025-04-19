package com.hrishi.minichallenges_pl.months.april_2025
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import com.hrishi.minichallenges_pl.R

@OptIn(ExperimentalTextApi::class)
object AprilTypography {
    val ChivoMonoExtraBold = FontFamily(
        Font(
            R.font.nunito_variablefont_wght,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(800)
            )
        )
    )

    val ChivoMonoMaxBold = FontFamily(
        Font(
            R.font.nunito_variablefont_wght,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(900)
            )
        )
    )
}