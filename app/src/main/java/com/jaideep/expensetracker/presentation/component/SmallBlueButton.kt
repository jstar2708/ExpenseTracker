package com.jaideep.expensetracker.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaideep.expensetracker.presentation.theme.AppTheme

@Preview
@Composable
private fun SmallBlueButtonPreview() {
    AppTheme {
        SmallPrimaryColorButton(text = "Apply") {

        }
    }
}

@Composable
fun SmallPrimaryColorButton(
    text: String, modifier: Modifier = Modifier, onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(0.dp, 0.dp, 0.dp, 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonColors(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary,
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary
        )
    ) {
        SimpleText(
            text = text,
            textAlignment = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}