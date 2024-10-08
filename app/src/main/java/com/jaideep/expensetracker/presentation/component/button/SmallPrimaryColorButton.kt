package com.jaideep.expensetracker.presentation.component.button

import androidx.compose.foundation.layout.fillMaxSize
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
import com.jaideep.expensetracker.presentation.component.SimpleSmallText
import com.jaideep.expensetracker.presentation.component.SimpleText
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
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonColors(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary,
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary
        )
    ) {
        SimpleSmallText(
            text = text,
            textAlignment = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}