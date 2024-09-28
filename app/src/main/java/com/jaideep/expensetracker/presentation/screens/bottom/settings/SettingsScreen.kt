package com.jaideep.expensetracker.presentation.screens.bottom.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.common.MainScreen
import com.jaideep.expensetracker.presentation.component.MediumText
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.SimpleTextBold
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerAppBar
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.viewmodel.SettingsViewModel

@Preview
@Composable
fun SettingsScreenPreview() {
    AppTheme {
        SettingsScreen(
            userName = "Jaideep Kumar Singh"
        )
    }
}


@Composable
fun SettingsScreenRoot(
    navController: NavController,
    settingsViewModel: SettingsViewModel,
    onBackPress: () -> Unit
) {
    SettingsScreen(
        userName = settingsViewModel.userName.collectAsState().value
    )
}

@Composable
fun SettingsScreen(
    userName: String
) {
    Scaffold(
        topBar = {
            ExpenseTrackerAppBar(
                title = "Settings",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                navigationDescription = "Navigation Icon",
                onNavigationIconClick = { /*TODO*/ },
                actionIcon = null,
                actionDescription = ""
            ) {

            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(70.dp),
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile image"
            )
            Spacer(modifier = Modifier.size(20.dp))
            SimpleTextBold(text = userName)
            Spacer(modifier = Modifier.size(20.dp))

                MediumText(text = "Profile")
                SimpleText(text = "Profile")
                SimpleText(text = "Profile")
                SimpleText(text = "Profile")

        }
    }
}