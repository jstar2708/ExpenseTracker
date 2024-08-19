package com.jaideep.expensetracker.domain.repository

interface EtRepository {
    suspend fun getAmountSpentTodayForAccount(accountName: String): Double
    suspend fun getAccountBalanceByName(accountName: String): Double
}