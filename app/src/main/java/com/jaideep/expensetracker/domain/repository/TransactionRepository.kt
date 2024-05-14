package com.jaideep.expensetracker.domain.repository

import com.jaideep.expensetracker.data.local.entities.Transaction
import com.jaideep.expensetracker.data.local.paging.TransactionPagingSource

interface TransactionRepository {

    fun getTransactionPagingSource(): TransactionPagingSource
    suspend fun getTransactionsForAccount(accountId: Int, limit: Int, offset: Int): List<Transaction>
    suspend fun getDebitTransactionsForAccount(accountId: Int, limit: Int, offset: Int) : List<Transaction>
    suspend fun getCreditTransactionsForAccount(accountId: Int, limit: Int, offset: Int) : List<Transaction>
    suspend fun getCreditTransactionBetweenDatesForAccount(accountId: Int, startDate: Long, endDate: Long, limit: Int, offset: Int) : List<Transaction>
    suspend fun getDebitTransactionBetweenDatesForAccount(accountId: Int, startDate: Long, endDate: Long, limit: Int, offset: Int) : List<Transaction>
    suspend fun getTransactionBetweenDatesForAccount(accountId: Int, startDate: Long, endDate: Long, limit: Int, offset: Int): List<Transaction>
    suspend fun getAllTransactions(limit: Int, offset: Int): List<Transaction>
    suspend fun getDebitTransactions(limit: Int, offset: Int) : List<Transaction>
    suspend fun getCreditTransactions(limit: Int, offset: Int) : List<Transaction>
    suspend fun getCreditTransactionBetweenDates(startDate: Long, endDate: Long, limit: Int, offset: Int) : List<Transaction>
    suspend fun getDebitTransactionBetweenDates(startDate: Long, endDate: Long, limit: Int, offset: Int) : List<Transaction>
    suspend fun getTransactionBetweenDates(startDate: Long, endDate: Long, limit: Int, offset: Int) : List<Transaction>
    suspend fun saveTransaction(transaction: Transaction)
    suspend fun getTransactionById(transactionId: Int) : Transaction
}