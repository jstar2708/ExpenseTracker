package com.jaideep.expensetracker.presentation.viewmodel

import android.hardware.biometrics.BiometricManager.Strings
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(): ViewModel() {
    val userName: MutableStateFlow<String> = MutableStateFlow(String())

}