package com.jaideep.expensetracker.presentation.component.other

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.theme.md_theme_light_primary
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Preview
@Composable
private fun ExpenseTrackerTabLayoutPreview() {
    AppTheme {
        ExpenseTrackerTabLayout(values = persistentListOf("All", "Income", "Expense")) {

        }
    }
}

@Composable
fun ExpenseTrackerTabLayout(
    modifier: Modifier = Modifier,
    initialValue: Int = 0,
    values: ImmutableList<String>,
    onClick: (selectedTab: Int) -> Unit
) {
    var selectedTab by remember {
        mutableIntStateOf(initialValue)
    }
    TabRow(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        selectedTabIndex = selectedTab,
        divider = {},
        indicator = {},
        contentColor = Color.Black
    ) {
        values.forEachIndexed { index, title ->
            Tab(
                selected = index == selectedTab,
                modifier = if (index == selectedTab) Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(md_theme_light_primary)
                    .wrapContentWidth() else Modifier.clip(
                    RoundedCornerShape(10.dp)
                ),
                onClick = {
                    selectedTab = index
                    onClick(selectedTab)
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Black
            ) {
                SimpleText(
                    text = title, modifier = Modifier.padding(8.dp), color = Color.Unspecified
                )
            }
        }
    }
}