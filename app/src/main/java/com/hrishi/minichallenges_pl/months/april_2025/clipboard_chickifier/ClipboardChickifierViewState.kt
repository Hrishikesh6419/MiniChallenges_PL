package com.hrishi.minichallenges_pl.months.april_2025.clipboard_chickifier

import androidx.annotation.StringRes
import androidx.compose.foundation.text.input.TextFieldState
import com.hrishi.minichallenges_pl.R

data class ClipboardChickifierViewState(
    val enteredText: TextFieldState = TextFieldState(),
    val isCopyEnabled: Boolean = false,
    @StringRes val exampleTexts: List<Int> = listOf(
        R.string.easter_message_1,
        R.string.easter_message_2,
        R.string.easter_message_3,
        R.string.easter_message_4,
        R.string.easter_message_5,
        R.string.easter_message_6,
        R.string.easter_message_7,
        R.string.easter_message_8,
        R.string.easter_message_9,
        R.string.easter_message_10,
        R.string.easter_message_11,
        R.string.easter_message_12,
        R.string.easter_message_13,
        R.string.easter_message_14
    )
)