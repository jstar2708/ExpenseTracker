package com.jaideep.expensetracker.presentation.screens.details

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import com.jaideep.expensetracker.model.dto.AccountDto
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerAppBar
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.viewmodel.MainViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate

@Preview
@Composable
private fun AccountListScreenPreview() {
    AppTheme {
        AccountListScreen(accountList = persistentListOf(
            AccountDto(0, "PNB", 3456.0, LocalDate.now()),
            AccountDto(0, "PNB", 3456.0, LocalDate.now()),
            AccountDto(0, "PNB", 3456.0, LocalDate.now()),
            AccountDto(0, "PNB", 3456.0, LocalDate.now())
        ), savedStateHandle = SavedStateHandle(), onAccountClick = {}, onBackPress = {})
    }
}


@Composable
fun AccountListScreenRoot(
    mainViewModel: MainViewModel,
    savedStateHandle: SavedStateHandle?,
    onBackPress: () -> Unit,
    navigateToEditAccountScreen: (Int) -> Unit

) {
    AccountListScreen(
        accountList = mainViewModel.accounts.collectAsState().value.toImmutableList(),
        onAccountClick = navigateToEditAccountScreen,
        onBackPress = onBackPress,
        savedStateHandle = savedStateHandle
    )
}

@Composable
fun AccountListScreen(
    accountList: ImmutableList<AccountDto>,
    savedStateHandle: SavedStateHandle?,
    onAccountClick: (Int) -> Unit,
    onBackPress: () -> Unit
) {
    val result = savedStateHandle?.get<Boolean?>("isAccountSaved")
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState) {
            Snackbar(snackbarData = it, containerColor = Color.DarkGray)
        }
    }, topBar = {
        ExpenseTrackerAppBar(title = "All Accounts",
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            navigationDescription = "Back icon",
            onNavigationIconClick = onBackPress,
            actionIcon = null,
            actionDescription = "",
            onActionIconClick = {})
    }) {
        Column(modifier = Modifier.padding(it)) {
            if (accountList.isEmpty()) {
                SimpleText(text = "No accounts created")
            } else {
                LazyColumn {
                    items(accountList.size) { idx ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)
                                .padding(start = .5.dp, end = .5.dp)
                                .border(width = 1.dp, color = Color.Gray)
                                .clickable {
                                    onAccountClick(accountList[idx].id)
                                }, contentAlignment = Alignment.CenterStart
                        ) {
                            SimpleText(
                                text = accountList[idx].accountName,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = result) {
        if (result != null) {
            snackBarHostState.showSnackbar(message = "Account updated successfully")
            savedStateHandle["isAccountSaved"] = null
        }
    }
}