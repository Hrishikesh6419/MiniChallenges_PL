package com.hrishi.minichallenges_pl.months.april_2025.clipboard_chickifier

sealed interface ClipboardChickifierEvent {
    data class TextCopied(val text: String) : ClipboardChickifierEvent
}