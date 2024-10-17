package com.jaideep.expensetracker.presentation.screens


import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.jaideep.expensetracker.presentation.screens.auth.biometric.BiometricAuthManager
import com.jaideep.expensetracker.presentation.utility.showToast

@Composable
fun BiometricDialog(
    onBiometricAuthSuccess: () -> Unit
) {
    val context = LocalContext.current
    val activity = LocalContext.current as? FragmentActivity
    val resultCode = remember {
        mutableIntStateOf(Int.MIN_VALUE)
    }
    val launcherIntent =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                1 -> {
                    context.showToast("Enrollment done")
                    resultCode.intValue = 1
                }

                else -> {
                    context.showToast("Enrollment canceled")
                    resultCode.intValue = 2
                }
            }
        }
    val biometricAuthManager = BiometricAuthManager(context)
    val executor = ContextCompat.getMainExecutor(context)

    LaunchedEffect(key1 = resultCode.intValue) {
        biometricAuthManager.showBiometricPrompt(title = "",
            description = "",
            onSuccess = { authenticators ->
                val biometricPromptInfo =
                    BiometricPrompt.PromptInfo.Builder().setTitle("Biometric Authentication")
                        .setSubtitle("Please put your finger on the sensor to login")
                        .setNegativeButtonText("Cancel").setAllowedAuthenticators(authenticators)
                        .build()
                val biometricPrompt = activity?.let {
                    BiometricPrompt(
                        it,
                        executor,
                        object : BiometricPrompt.AuthenticationCallback() {
                            override fun onAuthenticationFailed() {
                                super.onAuthenticationFailed()
                                context.showToast("Failed to authenticate")
                            }

                            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                super.onAuthenticationSucceeded(result)
                                onBiometricAuthSuccess()
                                context.showToast("Success")

                            }

                            override fun onAuthenticationError(
                                errorCode: Int, errString: CharSequence
                            ) {
                                super.onAuthenticationError(errorCode, errString)
                                context.showToast("Error")
                            }
                        })
                }
                biometricPrompt?.authenticate(biometricPromptInfo)
            },
            onError = {

            },
            openSettings = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    // Greater than Android 9
                    val intent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED, BIOMETRIC_STRONG)
                    }
                    launcherIntent.launch(intent)
                } else {
                    val intent = Intent(Settings.ACTION_SECURITY_SETTINGS)
                    context.startActivity(intent)
                }
            })
    }
}
