package com.jaideep.expensetracker.presentation.component.other

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaideep.expensetracker.presentation.component.MediumBoldText
import com.jaideep.expensetracker.presentation.theme.AppTheme

@Preview
@Composable
private fun ExpenseTrackerAppBarPreview() {
    AppTheme {
        ExpenseTrackerAppBar(
            title = "Jaideep Kumar",
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            navigationDescription = "Navigation icon",
            onNavigationIconClick = { },
            actionIcon = Icons.Filled.Notifications,
            actionDescription = "Action icon"
        ) {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseTrackerAppBar(
    title: String,
    navigationIcon: ImageVector,
    navigationDescription: String,
    onNavigationIconClick: () -> Unit,
    actionIcon: ImageVector?,
    actionDescription: String,
    onActionIconClick: () -> Unit,
) {
    TopAppBar(title = {
        MediumBoldText(
            modifier = Modifier.padding(start = 4.dp, end = 4.dp), text = title
        )
    }, navigationIcon = {
        IconButton(onClick = { onNavigationIconClick() }) {
            Icon(
                imageVector = navigationIcon,
                contentDescription = navigationDescription,
                modifier = if (navigationIcon == Icons.AutoMirrored.Filled.ArrowBack) Modifier.size(
                    30.dp
                ) else Modifier.size(40.dp),
            )
        }
    }, actions = {
        actionIcon?.let {
            IconButton(onClick = { onActionIconClick() }) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = actionDescription,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    })
}