package com.jaideep.expensetracker.presentation.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaideep.expensetracker.model.SnackBarState
import com.jaideep.expensetracker.model.TextFieldWithIconAndErrorPopUpState
import com.jaideep.expensetracker.model.dto.NotificationDto
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.card.ExpenseTrackerNotificationCard
import com.jaideep.expensetracker.presentation.component.dialog.NotificationDialog
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerAppBar
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerProgressBar
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerTabLayout
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.viewmodel.NotificationViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Preview
@Composable
private fun NotificationScreenPreview() {
    AppTheme {
        NotificationScreen(tabList = persistentListOf("Upcoming", "Completed"),
            dateState = TextFieldWithIconAndErrorPopUpState("",
                isError = false,
                showError = false,
                onValueChange = { _ -> },
                onErrorIconClick = {},
                errorMessage = ""
            ),
            messageState = TextFieldWithIconAndErrorPopUpState("",
                isError = false,
                showError = false,
                onValueChange = { _ -> },
                onErrorIconClick = {},
                errorMessage = ""
            ),
            upcomingNotificationList = persistentListOf(),
            completedNotificationList = persistentListOf(),
            selectedTab = 0,
            snackBarState = SnackBarState(false, "This is notification"),
            updateSelectedTab = {},
            backPress = {},
            onDeleteClick = {},
            disableSnackBarState = {},
            saveNotification = { _, _ -> },
            clearDialog = {},
            validateNotification = { true })
    }
}

@Composable
fun NotificationScreenRoot(
    backPress: () -> Unit
) {
    val notificationViewModel = hiltViewModel<NotificationViewModel>()
    LaunchedEffect(key1 = true) {
        notificationViewModel.initData()
    }
    if (notificationViewModel.isNotificationLoading) {
        ExpenseTrackerProgressBar(Modifier.size(50.dp))
    } else if (notificationViewModel.notificationRetrievalError) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Center
        ) {
            SimpleText(
                text = "Error loading notification data", color = Color.Red
            )
        }
    } else {
        NotificationScreen(
            tabList = notificationViewModel.tabList,
            dateState = notificationViewModel.dateState,
            messageState = notificationViewModel.messageState,
            upcomingNotificationList = notificationViewModel.upcomingNotifications.collectAsState().value.toImmutableList(),
            completedNotificationList = notificationViewModel.completedNotifications.collectAsState().value.toImmutableList(),
            selectedTab = notificationViewModel.selectedTab,
            snackBarState = notificationViewModel.snackBarState,
            updateSelectedTab = notificationViewModel::updateSelectedTab,
            backPress = backPress,
            saveNotification = notificationViewModel::saveNotification,
            onDeleteClick = notificationViewModel::onNotificationDelete,
            disableSnackBarState = notificationViewModel::disableSnackBarState,
            clearDialog = notificationViewModel::clearDialogState,
            validateNotification = notificationViewModel::isNotificationValid
        )
    }
}

@Composable
fun NotificationScreen(
    tabList: ImmutableList<String>,
    dateState: TextFieldWithIconAndErrorPopUpState,
    messageState: TextFieldWithIconAndErrorPopUpState,
    upcomingNotificationList: ImmutableList<NotificationDto>,
    completedNotificationList: ImmutableList<NotificationDto>,
    selectedTab: Int,
    snackBarState: SnackBarState,
    updateSelectedTab: (pos: Int) -> Unit,
    backPress: () -> Unit,
    disableSnackBarState: () -> Unit,
    saveNotification: (message: String, date: String) -> Unit,
    validateNotification: () -> Boolean,
    clearDialog: () -> Unit,
    onDeleteClick: (notificationDto: NotificationDto) -> Unit
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = snackBarState) {
        if (snackBarState.showSnackBar) {
            snackBarHostState.showSnackbar(snackBarState.message)
            disableSnackBarState()
        }
    }
    var showAddDialog by remember {
        mutableStateOf(false)
    }

    if (showAddDialog) {
        NotificationDialog(
            dateState = dateState,
            messageState = messageState,
            saveNotification = saveNotification,
            validateNotification = validateNotification,
            clearDialog = clearDialog
        ) {
            showAddDialog = false
        }
    }

    Scaffold(topBar = {
        ExpenseTrackerAppBar(
            title = "Notification",
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            navigationDescription = "Back icon",
            onNavigationIconClick = backPress,
            actionIcon = Icons.Filled.Add,
            actionDescription = "Add icon"
        ) {
            showAddDialog = true
        }
    }, snackbarHost = {
        SnackbarHost(hostState = snackBarHostState) {
            Snackbar(
                snackbarData = it, containerColor = Color.DarkGray
            )
        }
    }) {
        Column(Modifier.padding(it)) {
            ExpenseTrackerTabLayout(values = tabList, onClick = updateSelectedTab)
            val list = if (selectedTab == 0) upcomingNotificationList else completedNotificationList
            if (list.isEmpty()) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    SimpleText(text = "No notifications")
                }
            } else {
                LazyColumn {
                    items(list.size) { index ->
                        ExpenseTrackerNotificationCard(
                            notificationDto = list[index], onDeleteClick = onDeleteClick
                        )
                    }
                }
            }
        }
    }
}