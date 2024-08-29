package com.jaideep.expensetracker.model

import androidx.compose.ui.text.input.TextFieldValue

data class DialogState (
    val showDialog: Boolean,
    val fromDate: String,
    val toDate: String,
    val showError: Boolean,
    val errorMessage: String
)
