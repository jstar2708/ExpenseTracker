package com.jaideep.expensetracker.model

data class TextFieldWithIconAndErrorPopUpState (
    val text: String,
    val isError: Boolean,
    val showError: Boolean,
    val errorMessage: String,
    val onValueChange: (value: String) -> Unit,
    val onErrorIconClick: () -> Unit
)