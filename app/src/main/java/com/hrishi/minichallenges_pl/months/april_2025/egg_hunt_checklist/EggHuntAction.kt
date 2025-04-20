package com.hrishi.minichallenges_pl.months.april_2025.egg_hunt_checklist

sealed interface EggHuntAction {
    data class OnLocationChecked(val eggLocationData: EggLocationData) : EggHuntAction
    data object OnResetClicked : EggHuntAction
    data object OnDialogDismiss : EggHuntAction
}