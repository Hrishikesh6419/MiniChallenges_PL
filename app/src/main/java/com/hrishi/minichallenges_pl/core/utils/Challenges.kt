package com.hrishi.minichallenges_pl.core.utils

interface Challenge {
    val displayName: String
}

enum class Feb2025Challenges : Challenge {
    BATTERY_INDICATOR {
        override val displayName: String = "Battery Indicator"
    },
    CONFETTI {
        override val displayName: String = "Confetti"
    },
    AUDIO_WAVE {
        override val displayName: String = "Audio Wave Form Player"
    }
}

enum class Mars2025Challenges : Challenge {
    DAWN_DUSK_ANIMATION {
        override val displayName: String = "Dawn Dusk Animation"
    },
    MARS_WEATHER_CARD {
        override val displayName: String = "Mars Weather Card"
    },
    ORBITING_ROCKET {
        override val displayName: String = "Orbiting Rocket"
    }
}