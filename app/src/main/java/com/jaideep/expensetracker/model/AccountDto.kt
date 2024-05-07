package com.jaideep.expensetracker.model

import java.time.LocalDateTime

data class AccountDto(
    val accountName: String,
    val balance: String,
    val createdOn: LocalDateTime
) {
}