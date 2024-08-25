package com.jaideep.expensetracker.domain.repository

import com.jaideep.expensetracker.model.CategoryCardData
import kotlinx.coroutines.flow.Flow

interface EtRepository {
    fun getAmountSpentTodayForAccount(accountName: String, date: Long): Flow<Double>
    fun getAccountBalanceByName(accountName: String): Flow<Double>
    fun getAmountSpentTodayForAllAccount(date: Long): Flow<Double>
    fun getAccountBalanceForAllAccounts(): Flow<Double>
    fun getCategoryOnWhichMaximumAmountSpentFromAccountThisMonth(
        accountName: String, date: Long
    ): Flow<CategoryCardData>

    fun getTotalAmountSpentFromAccountThisMonth(accountName: String, date: Long): Flow<Double>
    fun getCategoryOnWhichMaximumAmountSpentFromAllAccountsThisMonth(date: Long): Flow<CategoryCardData>
    fun getAmountSpentFromAllAccountThisMonth(date: Long): Flow<Double>

}