package com.hrishi.minichallenges_pl.months.april_2025.egg_hunt_checklist

import com.hrishi.minichallenges_pl.R

data class EggHuntViewState(
    val eggLocationData: List<EggLocationData> = EggLocation.entries.map {
        EggLocationData(
            isSelected = false,
            location = it
        )
    },
    val showEasterDialog: Boolean = false,
    val showSelectedEasterContent: Boolean = false,
    val numberOfEggsSelected: Int = 0,
    val totalNumberOfEggs: Int = EggLocation.entries.size,
    val currentEasterFactResourceId: Int = R.string.easter_fact_1
)

data class EggLocationData(
    val isSelected: Boolean,
    val location: EggLocation
)

enum class EggLocation(val displayString: Int) {
    INSIDE_MOBILE_DEV_CAMPUS(R.string.egg_location_inside_mobile_dev_campus),
    BEHIND_THE_TV(R.string.egg_location_behind_tv),
    IN_THE_GARDEN_SHED(R.string.egg_location_garden_shed),
    UNDER_THE_PICNIC_TABLE(R.string.egg_location_picnic_table),
    INSIDE_THE_BIRDHOUSE(R.string.egg_location_birdhouse),
    BEHIND_THE_GARDEN_BUSHES(R.string.egg_location_garden_bushes),
    INSIDE_THE_FLOWER_POT(R.string.egg_location_flower_pot),
    IN_THE_MAILBOX(R.string.egg_location_mailbox),
    IN_THE_KITCHEN_PANTRY(R.string.egg_location_kitchen_pantry),
    INSIDE_THE_FLOWER_POT_DUPLICATE(R.string.egg_location_flower_pot_duplicate)
}