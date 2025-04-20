package com.hrishi.minichallenges_pl.months.april_2025.egg_hunt_checklist

import android.content.Context
import android.content.SharedPreferences

class EggHuntPreferenceManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PREFERENCES_NAME, Context.MODE_PRIVATE
    )

    fun saveEggLocationsState(eggLocations: List<EggLocationData>) {
        val editor = sharedPreferences.edit()

        eggLocations.forEach { locationData ->
            editor.putBoolean(
                getLocationKey(locationData.location),
                locationData.isSelected
            )
        }

        editor.apply()
    }

    fun loadEggLocationsState(defaultLocations: List<EggLocationData>): List<EggLocationData> {
        return defaultLocations.map { locationData ->
            locationData.copy(
                isSelected = sharedPreferences.getBoolean(
                    getLocationKey(locationData.location),
                    false
                )
            )
        }
    }

    fun clearSavedState() {
        sharedPreferences.edit().clear().apply()
    }

    private fun getLocationKey(location: EggLocation): String {
        return "${KEY_LOCATION_PREFIX}${location.name}"
    }

    companion object {
        private const val PREFERENCES_NAME = "egg_hunt_preferences"
        private const val KEY_LOCATION_PREFIX = "location_"
    }
}