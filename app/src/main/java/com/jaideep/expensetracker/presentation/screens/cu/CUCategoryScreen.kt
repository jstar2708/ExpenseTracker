package com.jaideep.expensetracker.presentation.screens.cu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.model.TextFieldWithIconAndErrorPopUpState
import com.jaideep.expensetracker.presentation.component.HeadingTextBold
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.SimpleTextBold
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerProgressBar
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
            screenTitle = "Add Category",
            screenDetails = "Please provide category details",
            buttonText = "Save"
        ) {

        }
    }
}

@Composable
fun CUCategoryScreenRoot(
    navControllerRoot: NavHostController, categoryId: Int
) {
    val addCategoryViewModel: AddCategoryViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {
        addCategoryViewModel.initData(categoryId)
    }
    if (addCategoryViewModel.isCategoryLoading.value) {
        ExpenseTrackerProgressBar(Modifier.size(50.dp))
    } else if (addCategoryViewModel.categoryRetrievalError.value) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Center
        ) {
            SimpleText(
                text = "Error loading category data", color = Color.Red
            )
        }
    } else {
        CUCategoryScreen(
            categoryState = addCategoryViewModel.categoryState.value,
            saveCategory = addCategoryViewModel::validateAndSaveCategory,
            exitScreen = addCategoryViewModel.exitScreen.value,
            screenTitle = addCategoryViewModel.screenTitle.value,
            screenDetails = addCategoryViewModel.screenDetails.value,
            buttonText = addCategoryViewModel.buttonText.value
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
    screenTitle: String,
    screenDetails: String,
    buttonText: String,
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
                        text = screenTitle, color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    SimpleText(
                        text = screenDetails, color = MaterialTheme.colorScheme.onPrimary
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
                        onClick = saveCategory) {
                        SimpleTextBold(
                            text = buttonText, color = Color.Blue, textAlignment = TextAlign.End
                        )
                    }
                }
            }
        }
    }
}