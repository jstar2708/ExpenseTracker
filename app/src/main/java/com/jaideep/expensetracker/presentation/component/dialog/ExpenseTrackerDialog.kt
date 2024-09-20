package com.jaideep.expensetracker.presentation.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.SimpleTextBold
import com.jaideep.expensetracker.presentation.component.button.SmallPrimaryColorButton
import com.jaideep.expensetracker.presentation.theme.AppTheme

@Preview
@Composable
private fun ExpenseTrackerDialogPreview() {
    AppTheme {
        ExpenseTrackerDialog(title = "Exit",
            message = "Do you want to exit?",
            okButtonText = "Yes",
            cancelButtonText = "No",
            onOkClick = { /*TODO*/ }) {

        }
    }
}

@Composable
fun ExpenseTrackerDialog(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    okButtonText: String,
    cancelButtonText: String,
    onOkClick: () -> Unit,
    onCancelClicked: () -> Unit
) {
    Dialog(
        onDismissRequest = { onCancelClicked() }, properties = DialogProperties(
            dismissOnClickOutside = true, dismissOnBackPress = true
        )
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(.9f),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            SimpleTextBold(
                modifier = Modifier.padding(16.dp, 8.dp), text = title
            )
            Spacer(modifier = Modifier.size(8.dp))
            SimpleText(
                modifier = Modifier.padding(16.dp, 0.dp), text = message
            )
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SmallPrimaryColorButton(text = cancelButtonText, onClick = onCancelClicked)
                SmallPrimaryColorButton(text = okButtonText, onClick = {
                    onOkClick()
                    onCancelClicked()
                })
            }
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}