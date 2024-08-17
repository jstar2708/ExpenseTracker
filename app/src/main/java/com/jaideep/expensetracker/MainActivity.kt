package com.jaideep.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.jaideep.expensetracker.presentation.navigation.RootNavigationGraph
import com.jaideep.expensetracker.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val defaultViewModel: DefaultViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        defaultViewModel.isFirstAppInitialization.observe(this) { isFirstInitialization ->
            if (isFirstInitialization) {
                defaultViewModel.addDefaultCategories()
            }
        }
        defaultViewModel.checkFirstAppInitialization()
        setContent {
            AppTheme {
                RootNavigationGraph()

            }
        }
    }
}
