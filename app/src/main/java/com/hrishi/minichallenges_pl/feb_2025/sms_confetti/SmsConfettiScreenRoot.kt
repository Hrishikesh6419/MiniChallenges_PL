package com.hrishi.minichallenges_pl.feb_2025.sms_confetti

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SmsConfettiScreenRoot(
    modifier: Modifier = Modifier
) {
    val smsPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.all { it.value }
        if (!granted) {
            println("SMS permissions denied")
        }
    }

    LaunchedEffect(Unit) {
        smsPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS
            )
        )
    }

    val showConfetti by SmsReceiver.showConfetti.collectAsStateWithLifecycle(false)

    // Using a key here to force recomposition when triggered
    var animationKey by remember { mutableIntStateOf(0) }

    LaunchedEffect(showConfetti) {
        if (showConfetti) {
            animationKey++
            SmsReceiver.resetConfetti()
        }
    }

    key(animationKey) {
        if (animationKey > 0) {
            ConfettiAnimation(
                modifier = modifier,
                confettiCount = 500,
                groupCount = 8,
                delayBetweenGroups = 500,
                fadeOut = false
            ) {
                RewardsContent(onTestConfetti = { SmsReceiver.triggerConfettiForTesting() })
            }
        } else {
            RewardsContent(
                modifier = modifier,
                onTestConfetti = { SmsReceiver.triggerConfettiForTesting() }
            )
        }
    }
}

@Composable
private fun RewardsContent(
    modifier: Modifier = Modifier,
    onTestConfetti: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Rewards App",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp),
            text = "You'll see a cool animation when you get a rewards message.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onTestConfetti) {
            Text("Show Me Now ")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Impatient? Smash the button and get a sneak peek of the confetti magic!!!",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSmsConfettiScreen() {
    SmsConfettiScreenRoot()
}