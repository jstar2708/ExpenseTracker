package com.jaideep.expensetracker.presentation.screens.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.jaideep.expensetracker.common.Routes

@Composable
fun SettingsScreen(navController: NavController) {
    Text(text = Routes.SETTINGS)
}