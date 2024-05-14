package com.jaideep.expensetracker.presentation.screens.add.acount

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jaideep.expensetracker.presentation.component.HeadingTextBold
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.SimpleTextBold
import com.jaideep.expensetracker.presentation.component.TextFieldWithIcon
import com.jaideep.expensetracker.presentation.screens.add.transaction.BlueBackground
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.model.AccountDto
import com.jaideep.expensetracker.presentation.viewmodel.AddViewModel
import java.time.LocalDateTime

@Preview
@Composable
private fun AddAccountScreenPreview() {
    AppTheme {
        AddAccountScreen(navController = NavHostController(Application()))
    }
}

@Composable
fun AddAccountScreen(navController: NavHostController, addViewModel: AddViewModel = hiltViewModel()) {
    Surface {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "App logo",
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .defaultMinSize(minHeight = 150.dp)
                    .padding(4.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            BlueBackground(
                Modifier.wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    HeadingTextBold(
                        text = "Add Account", color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    SimpleText(
                        text = "Please provide Account details",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    val accountName = remember {
                        mutableStateOf(TextFieldValue(""))
                    }
                    TextFieldWithIcon(
                        label = "Account Name",
                        icon = Icons.Filled.AccountBalanceWallet,
                        iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        borderColor = Color.Black,
                        text = accountName
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    val initialBalance = remember {
                        mutableStateOf(TextFieldValue(""))
                    }
                    val isBalanceError = remember {
                        mutableStateOf(false)
                    }
                    TextFieldWithIcon(
                        label = "Initial Balance",
                        icon = Icons.Filled.AccountBalanceWallet,
                        iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        borderColor = Color.Black,
                        text = initialBalance,
                        isError = isBalanceError
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(60.dp),
                        colors = ButtonColors(Color.White, Color.Blue, Color.White, Color.White),
                        onClick = {
                            try {
                                addViewModel.saveAccount(
                                    AccountDto(accountName.value.text, initialBalance.value.text.toDouble(), LocalDateTime.now())
                                )
                                navController.popBackStack()
                            }
                            catch (ne : NumberFormatException) {
                                isBalanceError.value = true
                            }
                        }) {
                        SimpleTextBold(
                            text = "Save", color = Color.Blue
                        )
                    }
                }
            }
        }


    }
}