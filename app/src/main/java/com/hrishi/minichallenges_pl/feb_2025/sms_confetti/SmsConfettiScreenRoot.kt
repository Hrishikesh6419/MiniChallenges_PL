package com.hrishi.minichallenges_pl.feb_2025.sms_confetti

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SmsConfettiScreenRoot(
    modifier: Modifier = Modifier
) {
    Text("Sms Confetti")
}

@Preview
@Composable
private fun PreviewSmsConfettiScreen() {
    SmsConfettiScreenRoot()
}