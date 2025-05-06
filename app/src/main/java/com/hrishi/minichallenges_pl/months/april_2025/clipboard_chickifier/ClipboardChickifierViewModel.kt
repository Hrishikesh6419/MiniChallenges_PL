package com.hrishi.minichallenges_pl.months.april_2025.clipboard_chickifier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrishi.minichallenges_pl.core.utils.textAsFlow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ClipboardChickifierViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ClipboardChickifierViewState())
    val uiState = _uiState.asStateFlow()

    private val _eventChannel = Channel<ClipboardChickifierEvent>()
    val events = _eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            uiState.value.enteredText.textAsFlow()
                .onEach { text ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            isCopyEnabled = text.isNotEmpty()
                        )
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    fun onAction(action: ClipboardChickifierAction) {
        when (action) {
            is ClipboardChickifierAction.OnCopyClick -> {
                val originalText = action.text
                if (originalText.isNotEmpty()) {
                    val decoratedText = "🐣🐰 $originalText 🐰🐣"

                    viewModelScope.launch {
                        _eventChannel.send(ClipboardChickifierEvent.TextCopied(decoratedText))
                    }
                }
            }
        }
    }
}