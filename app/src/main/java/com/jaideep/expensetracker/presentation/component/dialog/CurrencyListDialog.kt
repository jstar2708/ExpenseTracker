package com.jaideep.expensetracker.presentation.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jaideep.expensetracker.presentation.component.MediumBoldText
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.button.SmallPrimaryColorButton
import com.jaideep.expensetracker.presentation.theme.AppTheme
import kotlinx.collections.immutable.toImmutableList
import java.util.Currency

@Preview(showBackground = true)
@Composable
private fun CurrencyListDialogPreview() {
    AppTheme {
        CurrencyListDialog(hideDialog = {}, updateCurrency = {})
    }
}

@Composable
fun CurrencyListDialog(
    hideDialog: () -> Unit, updateCurrency: (symbol: String) -> Unit
) {
    Dialog(
        onDismissRequest = hideDialog,
        properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = true)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            val currencyList = Currency.getAvailableCurrencies().toImmutableList()
            var selectedSymbol by rememberSaveable {
                mutableStateOf(Currency.getInstance("INR").symbol)
            }
            Spacer(modifier = Modifier.height(8.dp))
            MediumBoldText(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = "Select your currency"
            )
            Spacer(modifier = Modifier.height(4.dp))
            LazyColumn(
                modifier = Modifier
                    .padding(8.dp)
                    .height(200.dp)
            ) {
                items(currencyList.size) { index ->
                    Row(Modifier.fillMaxWidth()) {
                        SimpleText(
                            text = "${currencyList[index].displayName} - ${currencyList[index].symbol}",
                            modifier = Modifier.weight(.7f)
                        )

                        RadioButton(modifier = Modifier.weight(.3f),
                            selected = selectedSymbol == currencyList[index].symbol,
                            onClick = {
                                selectedSymbol = currencyList[index].symbol
                            })
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Spacer(modifier = Modifier.weight(.15f))
                SmallPrimaryColorButton(
                    modifier = Modifier.weight(.5f), text = "Cancel", onClick = hideDialog
                )
                Spacer(modifier = Modifier.weight(.15f))
                SmallPrimaryColorButton(modifier = Modifier.weight(.5f), text = "Ok") {
                    updateCurrency(selectedSymbol)
                    hideDialog()
                }
                Spacer(modifier = Modifier.weight(.15f))
            }
        }
    }
}