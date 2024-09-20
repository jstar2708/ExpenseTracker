package com.jaideep.expensetracker.model.dto

import java.time.LocalDate

data class TransactionDto(
    val accountName: String,
    val transactionId: Int,
    val amount: Double,
    val categoryDto: CategoryDto,
    val message: String,
    val createdTime: LocalDate,
    val isCredit: Boolean
)