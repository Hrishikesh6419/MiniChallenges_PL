package com.hrishi.minichallenges_pl.months.april_2025.egg_hunt_checklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrishi.minichallenges_pl.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EggHuntViewModel(
    private val preferenceManager: EggHuntPreferenceManager
) : ViewModel() {

    private val shuffledFactResourceIds = mutableListOf(
        R.string.easter_fact_1,
        R.string.easter_fact_2,
        R.string.easter_fact_3,
        R.string.easter_fact_4,
        R.string.easter_fact_5,
        R.string.easter_fact_6,
        R.string.easter_fact_7,
        R.string.easter_fact_8,
        R.string.easter_fact_9,
        R.string.easter_fact_10,
        R.string.easter_fact_11,
        R.string.easter_fact_12,
        R.string.easter_fact_13,
        R.string.easter_fact_14
    ).apply {
        shuffle()
    }

    private var currentFactIndex = 0

    private val defaultEggLocations = EggLocation.entries.map {
        EggLocationData(
            isSelected = false,
            location = it
        )
    }

    private val savedEggLocations = preferenceManager.loadEggLocationsState(defaultEggLocations)

    private val _uiState = MutableStateFlow(
        EggHuntViewState(
            eggLocationData = savedEggLocations,
            numberOfEggsSelected = savedEggLocations.count { it.isSelected },
            currentEasterFactResourceId = shuffledFactResourceIds[0]
        )
    )
    val uiState = _uiState.asStateFlow()

    private fun getNextEasterFact(): Int {
        val factResourceId = shuffledFactResourceIds[currentFactIndex]
        currentFactIndex = (currentFactIndex + 1) % shuffledFactResourceIds.size
        return factResourceId
    }

    fun onAction(action: EggHuntAction) {
        when (action) {
            EggHuntAction.OnResetClicked -> handleReset()
            is EggHuntAction.OnLocationChecked -> handleLocationChecked(action.eggLocationData)
            is EggHuntAction.OnDialogDismiss -> handleDialogDismiss()
        }
    }

    private fun handleReset() {
        val updatedLocations = defaultEggLocations

        viewModelScope.launch {
            preferenceManager.clearSavedState()
        }

        shuffledFactResourceIds.shuffle()
        currentFactIndex = 0

        _uiState.update { currentState ->
            currentState.copy(
                eggLocationData = updatedLocations,
                numberOfEggsSelected = 0,
                currentEasterFactResourceId = shuffledFactResourceIds[0]
            )
        }
    }

    private fun handleLocationChecked(eggLocationData: EggLocationData) {
        val updatedLocations = _uiState.value.eggLocationData.map { locationData ->
            if (locationData.location == eggLocationData.location) {
                locationData.copy(isSelected = eggLocationData.isSelected)
            } else {
                locationData
            }
        }

        val eggsSelected = updatedLocations.count { it.isSelected }

        viewModelScope.launch {
            preferenceManager.saveEggLocationsState(updatedLocations)
        }

        val newFactResourceId = if (eggLocationData.isSelected) {
            getNextEasterFact()
        } else {
            _uiState.value.currentEasterFactResourceId
        }

        _uiState.update { currentState ->
            currentState.copy(
                showEasterDialog = true,
                showSelectedEasterContent = eggLocationData.isSelected,
                eggLocationData = updatedLocations,
                numberOfEggsSelected = eggsSelected,
                currentEasterFactResourceId = newFactResourceId
            )
        }
    }

    private fun handleDialogDismiss() {
        _uiState.update { currentState ->
            currentState.copy(
                showEasterDialog = false,
                showSelectedEasterContent = false
            )
        }
    }
}