package com.jaideep.expensetracker.presentation.component.card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.presentation.component.SimpleText

@Preview
@Composable
private fun ExpenseTrackerCategoryCardPreview() {
    ExpenseTrackerCategoryCard(
        iconId = R.drawable.food,
        iconDescription = "Food icon",
        categoryName = "Food",
        spendValue = "$0 / $1000",
        progressValue = 0.8f,
        trackColor = Color.Yellow
    )
}

@Composable
fun ExpenseTrackerCategoryCard(
    iconId: Int,
    iconDescription: String,
    categoryName: String,
    spendValue: String,
    progressValue: Float,
    trackColor: Color
) {
    Column(
        modifier = Modifier
            .wrapContentWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = iconDescription,
                modifier = Modifier
                    .padding(8.dp)
                    .size(30.dp),
                tint = Color.Unspecified
            )

            SimpleText(
                text = categoryName,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                overflow = TextOverflow.Ellipsis
            )

            SimpleText(
                text = spendValue, modifier = Modifier.padding(8.dp), textAlignment = TextAlign.End
            )
        }

        LinearProgressIndicator(
            progress = { progressValue },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(10.dp)
                .clip(RoundedCornerShape(8.dp)),
            color = trackColor
        )
    }
}