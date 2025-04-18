package com.hrishi.minichallenges_pl.months.march_2025.space_craft_flip_cards

import com.hrishi.minichallenges_pl.core.networking.model.NetworkError
import com.hrishi.minichallenges_pl.core.networking.model.Result

class SpaceCraftRepository {
    private val apiService = AstronautApiService()

    suspend fun getSpaceCrafts(): Result<List<SpaceCraft>, NetworkError> {
        return when (val result = apiService.getAstronauts()) {
            is Result.Success -> {
                val response = result.data
                val spaceCrafts = response.people
                    .groupBy { it.craft }
                    .map { (craftName, astronauts) ->
                        SpaceCraft(
                            name = craftName,
                            crewMembers = astronauts.map { Astronaut(it.name) }
                        )
                    }
                Result.Success(spaceCrafts)
            }

            is Result.Error -> Result.Error(result.error)
        }
    }

    fun getMockSpaceCrafts(): List<SpaceCraft> {
        return listOf(
            SpaceCraft(
                name = "ISS",
                crewMembers = listOf(
                    Astronaut("Oleg Kononenko"),
                    Astronaut("Nikolai Chub"),
                    Astronaut("Tracy Caldwell Dyson"),
                    Astronaut("Matthew Dominick"),
                    Astronaut("Michael Barratt"),
                    Astronaut("Jeanette Epps")
                )
            ),
            SpaceCraft(
                name = "Tiangong",
                crewMembers = listOf(
                    Astronaut("Li Guangsu"),
                    Astronaut("Li Cong"),
                    Astronaut("Ye Guangfu")
                )
            )
        )
    }
}