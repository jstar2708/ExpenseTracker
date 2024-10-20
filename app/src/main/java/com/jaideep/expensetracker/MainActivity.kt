package com.jaideep.expensetracker

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.jaideep.expensetracker.presentation.navigation.RootNavigationGraph
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.viewmodel.LoginViewModel
import com.jaideep.expensetracker.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkUserPresence()
        mainViewModel.isFirstAppInitialization.observe(this) { isFirstInitialization ->
            if (isFirstInitialization) {
                mainViewModel.addDefaultCategories()
            } else {
                mainViewModel.initData()
            }
        }
        mainViewModel.checkFirstAppInitialization()
        setContent {
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            AppTheme {
                RootNavigationGraph(mainViewModel, loginViewModel)
            }
        }
    }


    private fun checkUserPresence() {
        loginViewModel.isUserPresent()
        loginViewModel.checkAccountCreated()
    }
}
