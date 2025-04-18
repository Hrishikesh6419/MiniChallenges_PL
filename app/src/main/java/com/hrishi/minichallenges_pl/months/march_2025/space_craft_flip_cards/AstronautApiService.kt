package com.hrishi.minichallenges_pl.months.march_2025.space_craft_flip_cards

import com.hrishi.minichallenges_pl.core.networking.HttpClientProvider
import com.hrishi.minichallenges_pl.core.networking.getRequest
import com.hrishi.minichallenges_pl.core.networking.model.NetworkError
import com.hrishi.minichallenges_pl.core.networking.model.Result
import kotlinx.serialization.Serializable

class AstronautApiService {
    private val baseUrl = "http://api.open-notify.org"
    private val client = HttpClientProvider.client

    suspend fun getAstronauts(): Result<AstronautResponse, NetworkError> {
        return client.getRequest("$baseUrl/astros.json")
    }
}

@Serializable
data class AstronautResponse(
    val people: List<PersonInfo>,
    val number: Int,
    val message: String
)

@Serializable
data class PersonInfo(
    val name: String,
    val craft: String
)