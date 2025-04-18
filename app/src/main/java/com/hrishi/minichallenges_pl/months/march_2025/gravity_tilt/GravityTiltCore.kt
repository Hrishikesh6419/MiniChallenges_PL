package com.hrishi.minichallenges_pl.months.march_2025.gravity_tilt

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.math.sqrt

object MarsGravityTheme {
    private val BackgroundStart = Color(0xFF210A41)
    private val BackgroundEnd = Color(0xFF120327)
    val ToggleBackground = Color(0xFFFFFFFF)
    val ToggleBorder = Color(0xFFD8BEFF)
    val BackgroundGradient = Brush.verticalGradient(
        colors = listOf(BackgroundStart, BackgroundEnd)
    )
}

object MarsGravityConstants {
    const val EARTH_GRAVITY = 98.1f
    const val MARS_GRAVITY = 37.2f
    const val EARTH_DAMPING = 0.1f
    const val MARS_DAMPING = 0.01f
    const val EARTH_MAX_SPEED = 500f
    const val MARS_MAX_SPEED = Float.MAX_VALUE
    const val TILT_FACTOR = 0.1f

    const val TOGGLE_WIDTH = 128
    const val TOGGLE_HEIGHT = 60
    const val TOGGLE_TOP_PADDING = 60
    const val INDICATOR_SIZE = 52
    const val TOGGLE_ANIMATION_DURATION = 300
    const val SPACECRAFT_SIZE = 150

    const val ENABLE_BOUNCING = true
    const val BOUNCE_ENERGY_RETENTION = 0.7f
}

data class PhysicsParams(
    val gravity: Float,
    val damping: Float,
    val maxSpeed: Float
)

data class MotionState(
    val position: Offset = Offset(0f, 0f),
    val velocity: Offset = Offset(0f, 0f)
)

object PhysicsEngine {
    fun updateMotion(
        currentState: MotionState,
        tilt: Offset,
        deltaTime: Float,
        physicsParams: PhysicsParams,
        screenSize: Offset,
        spacecraftSize: Float
    ): MotionState {
        val ax = physicsParams.gravity * MarsGravityConstants.TILT_FACTOR * tilt.x
        val ay = physicsParams.gravity * MarsGravityConstants.TILT_FACTOR * tilt.y

        val frictionX = physicsParams.damping * currentState.velocity.x
        val frictionY = physicsParams.damping * currentState.velocity.y

        var newVelocityX = currentState.velocity.x + (ax - frictionX) * deltaTime
        var newVelocityY = currentState.velocity.y + (ay - frictionY) * deltaTime

        val currentSpeed = sqrt(newVelocityX * newVelocityX + newVelocityY * newVelocityY)
        if (currentSpeed > physicsParams.maxSpeed) {
            val speedFactor = physicsParams.maxSpeed / currentSpeed
            newVelocityX *= speedFactor
            newVelocityY *= speedFactor
        }

        val halfSpacecraftSize = spacecraftSize / 2
        val minX = -(screenSize.x / 2) + halfSpacecraftSize
        val maxX = (screenSize.x / 2) - halfSpacecraftSize
        val minY = -(screenSize.y / 2) + halfSpacecraftSize
        val maxY = (screenSize.y / 2) - halfSpacecraftSize

        var newPositionX = currentState.position.x + newVelocityX * deltaTime
        var newPositionY = currentState.position.y + newVelocityY * deltaTime

        if (newPositionX < minX) {
            newPositionX = minX
            if (MarsGravityConstants.ENABLE_BOUNCING) {
                newVelocityX *= -MarsGravityConstants.BOUNCE_ENERGY_RETENTION
            } else {
                newVelocityX = 0f
            }
        } else if (newPositionX > maxX) {
            newPositionX = maxX
            if (MarsGravityConstants.ENABLE_BOUNCING) {
                newVelocityX *= -MarsGravityConstants.BOUNCE_ENERGY_RETENTION
            } else {
                newVelocityX = 0f
            }
        }

        if (newPositionY < minY) {
            newPositionY = minY
            if (MarsGravityConstants.ENABLE_BOUNCING) {
                newVelocityY *= -MarsGravityConstants.BOUNCE_ENERGY_RETENTION
            } else {
                newVelocityY = 0f
            }
        } else if (newPositionY > maxY) {
            newPositionY = maxY
            if (MarsGravityConstants.ENABLE_BOUNCING) {
                newVelocityY *= -MarsGravityConstants.BOUNCE_ENERGY_RETENTION
            } else {
                newVelocityY = 0f
            }
        }

        return MotionState(
            position = Offset(newPositionX, newPositionY),
            velocity = Offset(newVelocityX, newVelocityY)
        )
    }
}

class TiltSensorManager(
    context: Context,
    private val onTiltChanged: (Offset, Long) -> Unit
) {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    private var lastTimestamp = 0L

    private val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            event.values?.let { values ->
                val rotationMatrix = FloatArray(9)
                val orientationValues = FloatArray(3)

                SensorManager.getRotationMatrixFromVector(rotationMatrix, values)
                SensorManager.getOrientation(rotationMatrix, orientationValues)

                val tiltX = Math.toDegrees(orientationValues[2].toDouble()).toFloat()
                val tiltY = Math.toDegrees(orientationValues[1].toDouble()).toFloat()

                val currentTime = System.currentTimeMillis()

                if (lastTimestamp != 0L) {
                    onTiltChanged(Offset(tiltX, tiltY), currentTime)
                }

                lastTimestamp = currentTime
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
    }

    fun startListening() {
        sensorManager.registerListener(
            sensorListener,
            rotationSensor,
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    fun stopListening() {
        sensorManager.unregisterListener(sensorListener)
    }
}