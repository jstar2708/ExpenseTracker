package com.jaideep.expensetracker.presentation.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaideep.expensetracker.model.dto.NotificationDto
import com.jaideep.expensetracker.presentation.component.SimpleSmallText
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.theme.AppTheme
import java.time.LocalDate

@Preview
@Composable
private fun ExpenseTrackerNotificationCardPreview() {
    AppTheme {
        ExpenseTrackerNotificationCard(notificationDto = NotificationDto(
            message = "This is a notification", date = LocalDate.of(2024, 8, 12), id = 0
        ), onDeleteClick = {})
    }
}

@Composable
fun ExpenseTrackerNotificationCard(
    notificationDto: NotificationDto, onDeleteClick: (id: Int) -> Unit
) {
    var expandCard by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier
        .clip(RoundedCornerShape(8.dp))
        .fillMaxWidth()
        .clickable { }
        .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(.8f)) {
                SimpleSmallText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    text = notificationDto.date.toString(),
                )
                SimpleText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    text = notificationDto.message
                )
            }
            Icon(
                modifier = Modifier.clickable {
                    expandCard = false
                    onDeleteClick(notificationDto.id)
                }, imageVector = Icons.Filled.Delete, contentDescription = ""
            )
        }

    }
}