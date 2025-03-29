package com.hrishi.minichallenges_pl.march_2025.mars_weather_card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrishi.minichallenges_pl.R
import com.hrishi.minichallenges_pl.core.utils.UpdateStatusBarAppearance

@Composable
fun MarsWeatherCardScreenRoot(
    modifier: Modifier = Modifier
) {
    MarsWeatherBackground {
        UpdateStatusBarAppearance(isDarkStatusBarIcons = false)
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            MarsWeatherCard()
        }
    }
}

@Composable
private fun MarsWeatherBackground(
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = MarsWeatherTheme.BackgroundGradient)
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            imageVector = ImageVector.vectorResource(R.drawable.ic_mars_surface),
            contentDescription = null,
            contentScale = ContentScale.FillHeight
        )

        content()
    }
}

@Composable
fun MarsWeatherCard() {
    val weatherInfoItems = listOf(
        WeatherInfoData("Wind speed", "27km/h NW"),
        WeatherInfoData("Pressure", "600 Pa"),
        WeatherInfoData("UV Radiation", "0.5 mSv/day"),
        WeatherInfoData("Martian date", "914 Sol")
    )

    Card(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .clip(cardWithDiagonalCut()),
        colors = CardDefaults.cardColors(containerColor = MarsWeatherTheme.Card)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            LocationHeader(location = "Olympus Mons")

            Spacer(modifier = Modifier.height(86.dp))

            WeatherCondition(condition = "Dust Storm")

            TemperatureDisplay(
                currentTemp = "-63",
                highTemp = "-52",
                lowTemp = "-73"
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(weatherInfoItems) { item ->
                    InfoBox(title = item.title, value = item.value)
                }
            }
        }
    }
}

@Composable
fun LocationHeader(location: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_map_point),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = location,
            style = TextStyle(
                color = MarsWeatherTheme.TextPurple,
                fontFamily = ChivoMonoRegular,
                fontSize = 14.sp
            )
        )
    }
}

@Composable
fun WeatherCondition(condition: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_wind),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = condition,
            style = TextStyle(
                color = MarsWeatherTheme.TextOrange,
                fontFamily = ChivoMonoRegular,
                fontSize = 14.sp
            )
        )
    }
}

@Composable
fun TemperatureDisplay(currentTemp: String, highTemp: String, lowTemp: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box {
            Row(verticalAlignment = Alignment.Top) {
                Text(
                    text = "-",
                    style = TextStyle(
                        color = MarsWeatherTheme.PrimaryText,
                        fontFamily = ChivoMonoRegular,
                        fontSize = 64.sp
                    )
                )
                Text(
                    text = currentTemp.replace("-", ""),
                    style = TextStyle(
                        color = MarsWeatherTheme.PrimaryText,
                        fontFamily = ChivoMonoRegular,
                        fontSize = 64.sp
                    )
                )
                Text(
                    text = "°C",
                    style = TextStyle(
                        color = MarsWeatherTheme.PrimaryText,
                        fontFamily = ChivoMonoRegular,
                        fontSize = 24.sp
                    ),
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.height(64.dp)
        ) {
            Text(
                text = "H:$highTemp°C",
                style = TextStyle(
                    color = MarsWeatherTheme.SecondaryText,
                    fontFamily = ChivoMonoRegular,
                    fontSize = 14.sp
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "L:$lowTemp°C",
                style = TextStyle(
                    color = MarsWeatherTheme.SecondaryText,
                    fontFamily = ChivoMonoRegular,
                    fontSize = 14.sp
                )
            )
        }
    }
}

@Composable
fun InfoBox(title: String, value: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MarsWeatherTheme.ConditionSurface)
            .padding(12.dp)
    ) {
        Column {
            Text(
                text = title,
                style = TextStyle(
                    color = MarsWeatherTheme.TextOrange,
                    fontFamily = ChivoMonoRegular,
                    fontSize = 12.sp
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = TextStyle(
                    color = MarsWeatherTheme.PrimaryText,
                    fontFamily = ChivoMonoMedium,
                    fontSize = 16.sp
                )
            )
        }
    }
}

fun cardWithDiagonalCut(cutSize: Float = 64f): Shape {
    return GenericShape { size, _ ->
        moveTo(0f, 0f)
        lineTo(size.width - cutSize, 0f)
        lineTo(size.width, cutSize)
        lineTo(size.width, size.height)
        lineTo(0f, size.height)
        close()
    }
}

private object MarsWeatherTheme {
    val BackgroundStart = Color(0xFF120327)
    val BackgroundEnd = Color(0xFF210A41)
    val Card = Color(0xFFFFFFFF)
    val PrimaryText = Color(0xFF14171E)
    val SecondaryText = Color(0xFF474F63)
    val TextOrange = Color(0xFFCD533C)
    val TextPurple = Color(0xFF9E83C5)
    val ConditionSurface = Color(0xFFF9E8E5)

    val BackgroundGradient = Brush.verticalGradient(
        colors = listOf(BackgroundStart, BackgroundEnd)
    )
}

@OptIn(ExperimentalTextApi::class)
private val ChivoMonoRegular = FontFamily(
    Font(
        R.font.chivmono_variablefont_weight,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(400)
        )
    )
)

@OptIn(ExperimentalTextApi::class)
private val ChivoMonoMedium = FontFamily(
    Font(
        R.font.chivmono_variablefont_weight,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(500)
        )
    )
)

private data class WeatherInfoData(
    val title: String,
    val value: String
)

@Preview
@Composable
fun PreviewMarsWeatherCard() {
    MarsWeatherCardScreenRoot()
}