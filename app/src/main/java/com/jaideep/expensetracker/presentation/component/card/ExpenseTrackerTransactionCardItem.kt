package com.jaideep.expensetracker.presentation.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.SimpleTextBold
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.theme.greenColor

@Preview
@Composable
private fun ExpenseTrackerTransactionCardItemPreview() {
    AppTheme {
        ExpenseTrackerTransactionCardItem(
            iconId = R.drawable.fuel,
            iconDescription = "Fuel icon",
            categoryName = "Fuel",
            transactionDescription = "Petrol in scooter",
            amount = "$49",
            transactionId = 0,
            onClick = {},
            showAllDetails = true
        )
    }
}

@Composable
fun ExpenseTrackerTransactionCardItem(
    iconId: Int,
    iconDescription: String,
    categoryName: String,
    transactionDescription: String,
    amount: String,
    isCredit: Boolean = false,
    transactionId: Int,
    showAllDetails: Boolean = false,
    onClick: (id: Int) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .background(MaterialTheme.colorScheme.background)
        .clickable {
            onClick(transactionId)
        }) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(4.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = iconDescription,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .size(30.dp),
                tint = Color.Unspecified
            )

            Column(Modifier.weight(1f)) {
                SimpleTextBold(
                    text = categoryName,
                    modifier = Modifier.padding(4.dp),
                    overflow = TextOverflow.Ellipsis
                )

                if (!showAllDetails) {
                    SimpleText(
                        text = transactionDescription,
                        modifier = Modifier.padding(4.dp),
                        overflow = TextOverflow.Ellipsis,
                        color = Color.DarkGray
                    )
                }
            }

            SimpleTextBold(
                text = amount,
                modifier = Modifier.padding(4.dp),
                overflow = TextOverflow.Ellipsis,
                color = if (isCredit) greenColor else Color.Black
            )

            Icon(
                imageVector = if (showAllDetails) Icons.Filled.KeyboardArrowDown else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .padding(4.dp),
                tint = Color.LightGray
            )
        }

        if (showAllDetails) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SimpleText(text = "Account name: ")
                SimpleTextBold(
                    text = "PNB", maxLines = 1
                )
            }
            Spacer(Modifier.height(4.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SimpleText(text = "Transaction date: ")
                SimpleTextBold(text = "27 Aug 2024", maxLines = 1)
            }
            Spacer(Modifier.height(4.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 0.dp), verticalAlignment = Alignment.Top
            ) {
                SimpleText(text = "Notes: ")
                SimpleText(text = transactionDescription, maxLines = 6)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Icon(imageVector = Icons.Filled.ModeEdit, contentDescription = "")
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
            }
        }
    }
}
