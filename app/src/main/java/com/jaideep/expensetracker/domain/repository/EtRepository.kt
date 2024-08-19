package com.jaideep.expensetracker.domain.repository

import kotlinx.coroutines.flow.Flow

interface EtRepository {
    fun getAmountSpentTodayForAccount(accountName: String): Flow<Double>
    fun getAccountBalanceByName(accountName: String): Flow<Double>
    fun getAmountSpentTodayForAllAccount(): Flow<Double>
    fun getAccountBalanceForAllAccounts(): Flow<Double>
}