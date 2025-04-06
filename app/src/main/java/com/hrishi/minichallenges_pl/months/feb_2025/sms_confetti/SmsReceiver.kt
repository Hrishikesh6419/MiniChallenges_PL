package com.hrishi.minichallenges_pl.months.feb_2025.sms_confetti

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SmsReceiver : BroadcastReceiver() {

    companion object {
        private const val TARGET_PHONE_NUMBER = "+4444555551"
        private const val TARGET_MESSAGE_TEMPLATE = "Congratulations! You've earned 500 points."

        private val _showConfetti = MutableStateFlow(false)
        val showConfetti = _showConfetti.asStateFlow()

        fun resetConfetti() {
            _showConfetti.value = false
        }

        fun triggerConfettiForTesting() {
            _showConfetti.value = true
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            Telephony.Sms.Intents.getMessagesFromIntent(intent).forEach { smsMessage ->
                processMessage(smsMessage)
            }
        }
    }

    private fun processMessage(smsMessage: SmsMessage) {
        val sender = smsMessage.originatingAddress ?: return
        val messageBody = smsMessage.messageBody ?: return

        val isMatchingSender = TARGET_PHONE_NUMBER.equals(sender, ignoreCase = true)
        val isMatchingMessage =
            TARGET_MESSAGE_TEMPLATE.equals(messageBody.trim(), ignoreCase = true)

        if (isMatchingSender && isMatchingMessage) {
            _showConfetti.value = true
        }
    }
}