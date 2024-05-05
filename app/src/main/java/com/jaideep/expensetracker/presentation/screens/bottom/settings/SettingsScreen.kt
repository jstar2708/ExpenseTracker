package com.jaideep.expensetracker.presentation.screens.bottom.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.jaideep.expensetracker.common.MainScreen

@Composable
fun SettingsScreen(navController: NavController) {
    Text(text = MainScreen.SETTINGS)
}