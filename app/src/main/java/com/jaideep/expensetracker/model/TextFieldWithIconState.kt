package com.jaideep.expensetracker.model

data class TextFieldWithIconState (
    val text: String,
    val isError: Boolean,
    val showError: Boolean,
    val onValueChange: (value: String) -> Unit,
    val onErrorIconClick: () -> Unit
)