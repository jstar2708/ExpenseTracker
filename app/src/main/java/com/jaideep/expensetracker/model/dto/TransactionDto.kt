package com.jaideep.expensetracker.model.dto

import java.time.LocalDate

data class TransactionDto(
    val accountName: String,
    val transactionId: Int,
    val amount: Double,
    val categoryId: Int,
    val categoryName: String,
    val iconName: String,
    val message: String,
    val createdOn: LocalDate,
    val isCredit: Boolean
)