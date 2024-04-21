package com.example.expensetracker.presentation.screens.category_screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.expensetracker.common.Routes

@Composable
fun CategoryScreen(navController: NavController) {
    Text(text = Routes.CATEGORY)
}