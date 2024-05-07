package com.jaideep.expensetracker.model

import java.time.LocalDateTime

data class TransactionDto(
    val amount: Double,
    val categoryDto: CategoryDto,
    val message: String,
    val createdTime: LocalDateTime,
    val isCredit: Boolean
)