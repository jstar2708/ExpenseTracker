package com.jaideep.expensetracker.presentation.component.button

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaideep.expensetracker.presentation.component.SimpleSmallText
import com.jaideep.expensetracker.presentation.theme.AppTheme

@Preview
@Composable
private fun ExpenseTrackerBlueButtonPreview() {
    AppTheme {
        ExpenseTrackerBlueButton(name = "Save", onClick = { /*TODO*/ })
    }
}

@Composable
fun ExpenseTrackerBlueButton(
    name: String, onClick: () -> Unit, modifier: Modifier = Modifier, containsIcon: Boolean = true
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonColors(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary,
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary
        )
    ) {
        SimpleSmallText(
            text = name,
            textAlignment = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
        )
        Spacer(
            modifier = Modifier.size(5.dp)
        )
        if (containsIcon) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Arrow icon",
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }
    }
}