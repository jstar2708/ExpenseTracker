package com.jaideep.expensetracker.presentation.component.other

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jaideep.expensetracker.presentation.theme.AppTheme

@Preview
@Composable
private fun ExpenseTrackerProgressBarPreview() {
    AppTheme {
        ExpenseTrackerProgressBar()
    }
}

@Composable
fun ExpenseTrackerProgressBar(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier, color = MaterialTheme.colorScheme.primary
    )
}