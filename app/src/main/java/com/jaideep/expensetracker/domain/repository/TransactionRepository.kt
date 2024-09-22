package com.jaideep.expensetracker.domain.repository

import com.jaideep.expensetracker.model.dto.TransactionDto
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getTransactionsForAccount(accountName: String, limit: Int): Flow<List<TransactionDto>>
    fun getDebitTransactionsForAccount(accountName: String, limit: Int): Flow<List<TransactionDto>>
    fun getCreditTransactionsForAccount(accountName: String, limit: Int): Flow<List<TransactionDto>>
    fun getCreditTransactionBetweenDatesForAccount(
        accountName: String, startDate: Long, endDate: Long, limit: Int
    ): Flow<List<TransactionDto>>

    fun getDebitTransactionBetweenDatesForAccount(
        accountName: String, startDate: Long, endDate: Long, limit: Int
    ): Flow<List<TransactionDto>>

    fun getTransactionBetweenDatesForAccount(
        accountName: String, startDate: Long, endDate: Long, limit: Int
    ): Flow<List<TransactionDto>>

    fun getAllTransactions(limit: Int): Flow<List<TransactionDto>>
    fun getDebitTransactions(limit: Int): Flow<List<TransactionDto>>
    fun getCreditTransactions(limit: Int): Flow<List<TransactionDto>>
    fun getCreditTransactionBetweenDates(
        startDate: Long, endDate: Long, limit: Int
    ): Flow<List<TransactionDto>>

    fun getDebitTransactionBetweenDates(
        startDate: Long, endDate: Long, limit: Int
    ): Flow<List<TransactionDto>>

    fun getTransactionBetweenDates(
        startDate: Long, endDate: Long, limit: Int
    ): Flow<List<TransactionDto>>

    suspend fun getTransactionById(transactionId: Int): TransactionDto
    suspend fun deleteTransactionById(transactionId: Int)
}