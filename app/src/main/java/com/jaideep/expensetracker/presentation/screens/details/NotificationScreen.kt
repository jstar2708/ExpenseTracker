package com.jaideep.expensetracker.presentation.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaideep.expensetracker.model.TextFieldWithIconAndErrorPopUpState
import com.jaideep.expensetracker.model.dto.NotificationDto
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerAppBar
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
            updateSelectedTab = {})
    }
}

@Composable
fun NotificationScreenRoot() {
    val notificationViewModel = hiltViewModel<NotificationViewModel>()
    NotificationScreen(
        tabList = notificationViewModel.tabList,
        dateState = notificationViewModel.dateState,
        messageState = notificationViewModel.messageState,
        upcomingNotificationList = notificationViewModel.upcomingNotifications.collectAsState().value.toImmutableList(),
        completedNotificationList = notificationViewModel.completedNotifications.collectAsState().value.toImmutableList(),
        selectedTab = notificationViewModel.selectedTab,
        updateSelectedTab = notificationViewModel::updateSelectedTab
    )
}

@Composable
fun NotificationScreen(
    tabList: ImmutableList<String>,
    dateState: TextFieldWithIconAndErrorPopUpState,
    messageState: TextFieldWithIconAndErrorPopUpState,
    upcomingNotificationList: ImmutableList<NotificationDto>,
    completedNotificationList: ImmutableList<NotificationDto>,
    selectedTab: Int,
    updateSelectedTab: (pos: Int) -> Unit
) {
    Scaffold(topBar = {
        ExpenseTrackerAppBar(
            title = "Notification",
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            navigationDescription = "Back icon",
            onNavigationIconClick = { },
            actionIcon = Icons.Filled.Add,
            actionDescription = "Add icon"
        ) {

        }
    }) {
        Column(Modifier.padding(it)) {
            ExpenseTrackerTabLayout(values = tabList, onClick = updateSelectedTab)
            LazyColumn {
                items((if (selectedTab == 0) upcomingNotificationList else completedNotificationList).size) {

                }
            }

        }
    }
}