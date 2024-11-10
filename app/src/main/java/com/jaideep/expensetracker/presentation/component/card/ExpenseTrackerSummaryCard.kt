package com.jaideep.expensetracker.presentation.component.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaideep.expensetracker.model.CategoryCardData
import com.jaideep.expensetracker.presentation.component.HeadingTextBold
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.theme.darkGray
import com.jaideep.expensetracker.presentation.utility.Utility
import com.jaideep.expensetracker.presentation.utility.getStringFromDouble

@Preview
@Composable
private fun SummaryCardPreview() {
    AppTheme {
        SummaryCard(
            accountBalance = 0.0,
            spentToday = 0.0,
            categoryCardData = CategoryCardData("Food", "Food", 0.0),
            amountSpentThisMonthFromAcc = 0.0
        )
    }
}

@Composable
fun SummaryCard(
    accountBalance: Double,
    spentToday: Double,
    categoryCardData: CategoryCardData?,
    amountSpentThisMonthFromAcc: Double
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = CardDefaults.elevatedShape,
        border = BorderStroke(.5.dp, Color.Black),
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Column {
                    HeadingTextBold(
                        text = "Balance",
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(start = 8.dp)
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    SimpleText(
                        text = "Spent Today ${getStringFromDouble(spentToday)}",
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    )
                }
                HeadingTextBold(
                    text = getStringFromDouble(accountBalance),
                    modifier = Modifier.weight(1f),
                    textAlignment = TextAlign.Center
                )
            }

            categoryCardData?.let { categoryCardData ->
                ExpenseTrackerCategoryCard(
                    iconId = Utility.getCategoryIconId(categoryCardData.iconName),
                    iconDescription = "${categoryCardData.categoryName} icon",
                    categoryName = categoryCardData.categoryName,
                    spendValue = "${
                        getStringFromDouble(categoryCardData.amountSpent)
                    } / ${
                        getStringFromDouble(amountSpentThisMonthFromAcc)
                    }",
                    progressValue = if (amountSpentThisMonthFromAcc == 0.0) 0f else (categoryCardData.amountSpent / amountSpentThisMonthFromAcc).toFloat(),
                    trackColor = darkGray
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
