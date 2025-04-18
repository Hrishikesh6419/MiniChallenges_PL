package com.hrishi.minichallenges_pl.months.march_2025.space_craft_flip_cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrishi.minichallenges_pl.core.networking.model.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SpaceCraftViewModel : ViewModel() {

    private val repository = SpaceCraftRepository()

    private val _uiState = MutableStateFlow(SpaceCraftUiState())
    val uiState: StateFlow<SpaceCraftUiState> = _uiState.asStateFlow()

    init {
        fetchSpaceCrafts()
    }

    private fun fetchSpaceCrafts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = repository.getSpaceCrafts()) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            spaceCrafts = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                }

                is Result.Error -> {
                    val mockData = repository.getMockSpaceCrafts()
                    _uiState.update {
                        it.copy(
                            spaceCrafts = mockData,
                            isLoading = false,
                            error = "Failed to load data from API. Using sample data."
                        )
                    }
                }
            }
        }
    }
}

data class SpaceCraftUiState(
    val spaceCrafts: List<SpaceCraft> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)