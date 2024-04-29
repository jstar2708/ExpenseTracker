package com.jaideep.expensetracker.presentation.screens.transaction_screen.addtransactionscreen

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.presentation.screens.HeadingTextBold
import com.jaideep.expensetracker.presentation.screens.RadioButtonWithText
import com.jaideep.expensetracker.presentation.screens.SimpleText
import com.jaideep.expensetracker.presentation.screens.TextFieldWithIcon
import com.jaideep.expensetracker.presentation.theme.AppTheme

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AddTransactionPreview() {
    AppTheme {
        AddTransaction(navController = NavHostController(Application()))
    }
}
@Composable
fun AddTransaction(navController: NavHostController) {
    Surface {
        Column {
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "App logo",
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .defaultMinSize(minHeight = 150.dp)
                    .padding(4.dp)
            )

            BlueBackground(Modifier.weight(1f)) {
                val radioButtonValue = remember {
                    mutableStateOf("Income")
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    HeadingTextBold(
                        text = "Add Transaction",
                        MaterialTheme.colorScheme.onPrimary
                    )
                    SimpleText(
                        text = "Please provide transaction details",
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ){
                        RadioButtonWithText(
                            isSelected = radioButtonValue.value == "Income",
                            text = "Income",
                            selectedColor = Color.White,
                            unselectedColor = Color.White,
                            textColor = Color.White
                        ) {
                            TODO("Implement onClick")
                        }
                        RadioButtonWithText(
                            isSelected = radioButtonValue.value == "Expense",
                            text = "Expense",
                            selectedColor = Color.White,
                            unselectedColor = Color.White,
                            textColor = Color.White
                        ) {
                            TODO("Implement onClick")
                        }
                    }

                    TextFieldWithIcon(
                        label = "Choose Account",
                        icon = Icons.Filled.AccountBalanceWallet,
                        iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        borderColor = Color.Black
                    )
                    TextFieldWithIcon(
                        label = "Choose Account",
                        icon = Icons.Filled.AccountBalanceWallet,
                        iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        borderColor = Color.Black
                    )
                    TextFieldWithIcon(
                        label = "Choose Account",
                        icon = Icons.Filled.AccountBalanceWallet,
                        iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        borderColor = Color.Black
                    )
                    TextFieldWithIcon(
                        label = "Choose Account",
                        icon = Icons.Filled.AccountBalanceWallet,
                        iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        borderColor = Color.Black
                    )
                    TextFieldWithIcon(
                            label = "Choose Account",
                    icon = Icons.Filled.AccountBalanceWallet,
                    iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    borderColor = Color.Black
                    )

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = ButtonColors(Color.White, Color.Blue, Color.White, Color.White),
                        onClick = { /*TODO*/ }
                    ) {
                            Text(
                                text = "Save",
                                color = Color.Blue
                            )
                    }
                }
            }
        }


    }
}

//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun BlueBackground(modifier: Modifier, content: @Composable ()-> Unit) {
    Box(
        modifier
            .clip(
                RoundedCornerShape(
                    topStart = 30.dp, topEnd = 30.dp
                )
            )
            .fillMaxWidth(1f)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        content()
    }
}