package com.jaideep.expensetracker.presentation.screens.cu

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.model.TextFieldWithIconAndErrorPopUpState
import com.jaideep.expensetracker.presentation.component.HeadingTextBold
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.SimpleTextBold
import com.jaideep.expensetracker.presentation.component.textfield.TextFieldWithIconAndErrorPopUp
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.viewmodel.AddCategoryViewModel

@Preview
@Composable
private fun AddCategoryScreenPreview() {
    AppTheme {
        CUCategoryScreen(
            TextFieldWithIconAndErrorPopUpState("",
                isError = false,
                showError = false,
                onValueChange = { _ -> },
                onErrorIconClick = {},
                errorMessage = ""
            ),
            exitScreen = false,
            saveCategory = {},
        ) {

        }
    }
}

@Composable
fun CUCategoryScreenRoot(
    navControllerRoot: NavHostController,
    isAdd: Boolean
) {
    val addCategoryViewModel: AddCategoryViewModel = hiltViewModel()
    AppTheme {
        CUCategoryScreen(
            categoryState = addCategoryViewModel.categoryState.value,
            saveCategory = addCategoryViewModel::validateAndSaveCategory,
            exitScreen = addCategoryViewModel.exitScreen.value,
        ) {
            val savedStateHandle = navControllerRoot.previousBackStackEntry?.savedStateHandle
            savedStateHandle?.set("isCategorySaved", addCategoryViewModel.isCategorySaved.value)
            navControllerRoot.popBackStack()
        }
    }
}

@Composable
fun CUCategoryScreen(
    categoryState: TextFieldWithIconAndErrorPopUpState,
    exitScreen: Boolean,
    saveCategory: () -> Unit,
    backPress: () -> Unit
) {

    LaunchedEffect(key1 = exitScreen) {
        if (exitScreen) {
            backPress()
        }
    }

    Surface {
        Column(
            Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom
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
                        text = "Add Category", color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    SimpleText(
                        text = "Please provide Category details",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    TextFieldWithIconAndErrorPopUp(
                        label = "Category Name",
                        icon = Icons.Filled.AccountBalanceWallet,
                        iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        borderColor = Color.Black,
                        text = categoryState.text,
                        isError = categoryState.isError,
                        errorMessage = categoryState.errorMessage,
                        onValueChange = categoryState.onValueChange,
                        onErrorIconClick = categoryState.onErrorIconClick,
                        showErrorText = categoryState.showError
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(60.dp),
                        colors = ButtonColors(Color.White, Color.Blue, Color.White, Color.White),
                        onClick = {
                            saveCategory()
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