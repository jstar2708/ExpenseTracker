package com.jaideep.expensetracker.domain.repository

import com.jaideep.expensetracker.model.CategoryCardData
import com.jaideep.expensetracker.model.dto.TransactionDto
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
    fun getCategoryCardsDataFromAllAccountsWithinDuration(date: Long): Flow<List<CategoryCardData>>
    fun getCategoryCardsDataFromAccountWithinDuration(
        accountName: String,
        date: Long
    ): Flow<List<CategoryCardData>>

    fun getCategoryWiseAllAccountTransactionsWithDate(
        categoryName: String, fromDate: Long, toDate: Long
    ): Flow<List<TransactionDto>>

    fun getCategoryWiseAccountTransactionWithDate(
        categoryName: String, accountName: String, fromDate: Long, toDate: Long
    ): Flow<List<TransactionDto>>

}