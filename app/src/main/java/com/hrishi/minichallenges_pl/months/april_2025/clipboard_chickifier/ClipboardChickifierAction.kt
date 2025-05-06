package com.hrishi.minichallenges_pl.months.april_2025.clipboard_chickifier

sealed interface ClipboardChickifierAction {
    data class OnCopyClick(val text: String) : ClipboardChickifierAction
}