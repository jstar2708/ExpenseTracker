package com.jaideep.expensetracker.domain.repository

import com.jaideep.expensetracker.data.local.entities.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getTransactionsForAccount(accountName: String, limit: Int): Flow<List<Transaction>>
    fun getDebitTransactionsForAccount(accountName: String, limit: Int): Flow<List<Transaction>>
    fun getCreditTransactionsForAccount(accountName: String, limit: Int): Flow<List<Transaction>>
    fun getCreditTransactionBetweenDatesForAccount(
        accountName: String, startDate: Long, endDate: Long, limit: Int
    ): Flow<List<Transaction>>

    fun getDebitTransactionBetweenDatesForAccount(
        accountName: String, startDate: Long, endDate: Long, limit: Int
    ): Flow<List<Transaction>>

    fun getTransactionBetweenDatesForAccount(
        accountName: String, startDate: Long, endDate: Long, limit: Int
    ): Flow<List<Transaction>>

    fun getAllTransactions(limit: Int): Flow<List<Transaction>>
    fun getDebitTransactions(limit: Int): Flow<List<Transaction>>
    fun getCreditTransactions(limit: Int): Flow<List<Transaction>>
    fun getCreditTransactionBetweenDates(
        startDate: Long, endDate: Long, limit: Int
    ): Flow<List<Transaction>>

    fun getDebitTransactionBetweenDates(
        startDate: Long, endDate: Long, limit: Int
    ): Flow<List<Transaction>>

    fun getTransactionBetweenDates(
        startDate: Long, endDate: Long, limit: Int
    ): Flow<List<Transaction>>

    suspend fun saveTransaction(transaction: Transaction)
    suspend fun getTransactionById(transactionId: Int): Transaction
    suspend fun deleteTransactionById(transactionId: Int)
}