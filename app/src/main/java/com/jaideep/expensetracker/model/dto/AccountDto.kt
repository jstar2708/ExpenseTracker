package com.jaideep.expensetracker.model.dto

import java.time.LocalDate

data class AccountDto(
    val id: Int, val accountName: String, val balance: Double, val createdOn: LocalDate
)