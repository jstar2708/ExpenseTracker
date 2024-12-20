package com.jaideep.expensetracker.presentation.component.card

import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.presentation.component.SimpleTextBold

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
    trackColor: Color,
    onClick: (categoryName: String) -> Unit = {}
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick(categoryName) }
        .padding(8.dp)
        .background(MaterialTheme.colorScheme.background)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = iconDescription,
                modifier = Modifier
                    .padding(8.dp)
                    .size(30.dp),
                tint = Color.Unspecified
            )

            SimpleTextBold(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                text = categoryName,
                overflow = TextOverflow.Ellipsis,
                textAlignment = TextAlign.End
            )

            SimpleTextBold(
                text = spendValue, modifier = Modifier.padding(8.dp), textAlignment = TextAlign.End
            )
        }

        var progress by remember {
            mutableFloatStateOf(0f)
        }

        val animatedProgress by animateFloatAsState(
            targetValue = progress, animationSpec = tween(
                durationMillis = 1000, delayMillis = 500, easing = EaseOut
            ), label = "progressState"
        )

        LaunchedEffect(key1 = progressValue) {
            progress = progressValue
        }
        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(10.dp)
                .clip(RoundedCornerShape(8.dp)),
            color = trackColor
        )
    }
}