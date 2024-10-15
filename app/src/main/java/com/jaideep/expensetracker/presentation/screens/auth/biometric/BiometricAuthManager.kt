package com.jaideep.expensetracker.presentation.screens.auth.biometric

import android.content.Context
import android.os.Build
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK

class BiometricAuthManager(
    private val context: Context
) {

    private inline fun authenticators(aboveVersion9: () -> Int, belowVersion10: () -> Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            aboveVersion9.invoke()
        } else {
            belowVersion10.invoke()
        }
    }

    fun showBiometricPrompt(
        title: String,
        description: String,
        onSuccess: (Int) -> Unit,
        onError: (String) -> Unit,
        openSettings: () -> Unit
    ) {
        val manager = BiometricManager.from(context)
        checkExistence(
            manager,
            onSuccess = onSuccess,
            onError = onError,
            openSettings = openSettings
        )
    }

    private fun checkExistence(
        manager: BiometricManager,
        onSuccess: (Int) -> Unit,
        onError: (String) -> Unit,
        openSettings: () -> Unit
    ) {
        val authenticators = authenticators(aboveVersion9 = {
            BIOMETRIC_STRONG
        }, belowVersion10 = {
            BIOMETRIC_WEAK
        })
        when (manager.canAuthenticate(authenticators)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                onSuccess.invoke(authenticators)
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                onError.invoke("BIOMETRIC_ERROR_HW_UNAVAILABLE")
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                openSettings.invoke()
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                onError.invoke("BIOMETRIC_ERROR_NO_HARDWARE")
            }

            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                onError.invoke("BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED")
            }

            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                onError.invoke("BIOMETRIC_STATUS_UNKNOWN")
            }

            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                onError.invoke("BIOMETRIC_ERROR_UNSUPPORTED")
            }
        }
    }
}