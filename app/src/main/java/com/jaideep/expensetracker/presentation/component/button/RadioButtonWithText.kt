package com.jaideep.expensetracker.presentation.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaideep.expensetracker.presentation.component.SimpleText

@Preview(showBackground = true)
@Composable
private fun RadioButtonWithTextPreview() {
    RadioButtonWithText(
        isSelected = true,
        text = "Income",
        selectedColor = Color.Blue,
        unselectedColor = Color.Gray,
        textColor = Color.Black
    ) {

    }
}

@Composable
fun RadioButtonWithText(
    isSelected: Boolean,
    text: String,
    selectedColor: Color,
    unselectedColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.padding(4.dp, 0.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = isSelected, onClick = {
                onClick()
            }, colors = RadioButtonColors(
                selectedColor, unselectedColor, Color.Unspecified, Color.Unspecified
            )
        )
        SimpleText(text = text, color = textColor)
    }
}