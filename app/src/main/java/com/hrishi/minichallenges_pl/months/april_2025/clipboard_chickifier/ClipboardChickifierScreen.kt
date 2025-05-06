package com.hrishi.minichallenges_pl.months.april_2025.clipboard_chickifier

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrishi.minichallenges_pl.core.utils.ObserveAsEvents
import com.hrishi.minichallenges_pl.core.utils.UpdateStatusBarAppearance
import com.hrishi.minichallenges_pl.months.april_2025.April2025Theme.AprilYellowGradient
import com.hrishi.minichallenges_pl.months.april_2025.April2025Theme.GrayGradient
import com.hrishi.minichallenges_pl.months.april_2025.April2025Theme.SubtleBackgroundGradient
import com.hrishi.minichallenges_pl.months.april_2025.April2025Theme.chickifyEasterColors
import com.hrishi.minichallenges_pl.months.april_2025.AprilTypography.ChivoMonoExtraBold
import com.hrishi.minichallenges_pl.months.april_2025.AprilTypography.ChivoMonoMedium
import org.koin.androidx.compose.koinViewModel

@Composable
fun ClipboardChickifierScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: ClipboardChickifierViewModel = koinViewModel()
) {
    UpdateStatusBarAppearance(isDarkStatusBarIcons = false)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ClipboardChickifierEvent.TextCopied -> {
                val text = AnnotatedString(event.text)
                clipboardManager.setText(text)
                Toast.makeText(
                    context,
                    "Copied $text",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    ClipboardChickifierScreen(
        modifier = modifier,
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
private fun ClipboardChickifierScreen(
    modifier: Modifier = Modifier,
    uiState: ClipboardChickifierViewState,
    onAction: (ClipboardChickifierAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(brush = SubtleBackgroundGradient)
            .systemBarsPadding()
            .navigationBarsPadding()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                focusManager.clearFocus()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Easter Messages Hub",
                style = TextStyle(
                    color = Color.White,
                    fontFamily = ChivoMonoExtraBold,
                    textAlign = TextAlign.Center
                ),
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
            )

            ClipboardTextField(
                modifier = Modifier.padding(horizontal = 16.dp),
                state = uiState.enteredText,
                hint = "Type something to Chickify!"
            )

            if (uiState.enteredText.text.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Preview: 🐣🐰 ${uiState.enteredText.text} 🐰🐣",
                    style = TextStyle(
                        color = Color.White.copy(alpha = 0.8f),
                        fontFamily = ChivoMonoMedium,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    focusManager.clearFocus()
                    onAction(ClipboardChickifierAction.OnCopyClick(uiState.enteredText.text.toString()))
                },
                enabled = uiState.isCopyEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                ),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 74.dp),
                modifier = Modifier.background(
                    brush = if (uiState.isCopyEnabled) {
                        AprilYellowGradient
                    } else {
                        GrayGradient
                    },
                    shape = RoundedCornerShape(8.dp)
                )
            ) {
                Text(
                    "Copy",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Try these amazing samples",
                style = TextStyle(
                    color = Color.White,
                    fontFamily = ChivoMonoExtraBold,
                    textAlign = TextAlign.Start
                ),
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        items(uiState.exampleTexts.size) { index ->
            val exampleTextRes = uiState.exampleTexts[index]
            val colorBrush = chickifyEasterColors[index % chickifyEasterColors.size]

            SuggestedTextItem(
                text = stringResource(exampleTextRes),
                onClick = {
                    focusManager.clearFocus()
                    onAction(ClipboardChickifierAction.OnCopyClick(it))
                },
                colorBrush = colorBrush
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun SuggestedTextItem(
    modifier: Modifier = Modifier,
    text: String,
    colorBrush: Brush,
    onClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .background(
                brush = colorBrush,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .clickable {
                onClick(text)
            }
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🐰",
                fontSize = 24.sp,
                modifier = Modifier.padding(end = 12.dp)
            )

            Text(
                text = text,
                style = TextStyle(
                    color = Color.White,
                    fontFamily = ChivoMonoExtraBold,
                    textAlign = TextAlign.Start
                ),
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ClipboardTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    hint: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    enabled: Boolean = true,
) {
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = Color.White
            ),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            state = state,
            enabled = enabled,
            textStyle = TextStyle(
                fontFamily = ChivoMonoMedium,
                fontSize = 24.sp,
                lineHeight = 24.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            cursorBrush = SolidColor(Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused },
            decorator = { innerBox ->
                HeadlineTextFieldContent(
                    value = state.text.toString(),
                    hint = hint,
                    isFocused = isFocused,
                    innerTextField = innerBox
                )
            }
        )
    }
}

@Composable
private fun HeadlineTextFieldContent(
    value: String,
    hint: String,
    isFocused: Boolean,
    innerTextField: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        if (value.isEmpty() && !isFocused) {
            Text(
                text = hint,
                style = TextStyle(
                    fontFamily = ChivoMonoMedium,
                    fontSize = 24.sp,
                    lineHeight = 24.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
                color = Color.Black.copy(
                    alpha = 0.40f
                ),
                textAlign = TextAlign.Center
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            innerTextField()
        }
    }
}

@Preview
@Composable
private fun PreviewClipboardChickifierScreen() {
    ClipboardChickifierScreen(
        uiState = ClipboardChickifierViewState(),
        onAction = {

        }
    )
}