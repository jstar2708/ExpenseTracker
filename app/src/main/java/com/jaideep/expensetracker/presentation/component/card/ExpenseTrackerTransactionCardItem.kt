package com.jaideep.expensetracker.presentation.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.jaideep.expensetracker.presentation.theme.greenColor

@Preview
@Composable
private fun ExpenseTrackerTransactionCardItemPreview() {
    ExpenseTrackerTransactionCardItem(
        iconId = R.drawable.fuel,
        iconDescription = "Fuel icon",
        categoryName = "Fuel",
        transactionDescription = "Petrol in scooter",
        amount = "$49"
    )
}

@Composable
fun ExpenseTrackerTransactionCardItem(
    iconId: Int,
    iconDescription: String,
    categoryName: String,
    transactionDescription: String,
    amount: String,
    isCredit: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
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

                SimpleText(
                    text = transactionDescription,
                    modifier = Modifier.padding(4.dp),
                    overflow = TextOverflow.Ellipsis,
                    color = Color.DarkGray
                )
            }

            SimpleTextBold(
                text = amount,
                modifier = Modifier.padding(4.dp),
                overflow = TextOverflow.Ellipsis,
                color = if (isCredit) greenColor else Color.Black
            )
        }
    }
}
