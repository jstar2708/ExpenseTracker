package com.jaideep.expensetracker.domain.repository

import com.jaideep.expensetracker.data.local.entities.Transaction

interface TransactionRepository {
    fun getTransactionsForAccount(accountId: Int, limit: Int): List<Transaction>
    fun getDebitTransactionsForAccount(accountId: Int, limit: Int): List<Transaction>
    fun getCreditTransactionsForAccount(accountId: Int, limit: Int): List<Transaction>
    fun getCreditTransactionBetweenDatesForAccount(
        accountId: Int,
        startDate: Long,
        endDate: Long,
        limit: Int
    ): List<Transaction>

    fun getDebitTransactionBetweenDatesForAccount(
        accountId: Int,
        startDate: Long,
        endDate: Long,
        limit: Int
    ): List<Transaction>

    fun getTransactionBetweenDatesForAccount(
        accountId: Int,
        startDate: Long,
        endDate: Long,
        limit: Int
    ): List<Transaction>

    fun getAllTransactions(limit: Int): List<Transaction>
    fun getDebitTransactions(limit: Int): List<Transaction>
    fun getCreditTransactions(limit: Int): List<Transaction>
    fun getCreditTransactionBetweenDates(
        startDate: Long,
        endDate: Long,
        limit: Int
    ): List<Transaction>

    fun getDebitTransactionBetweenDates(
        startDate: Long,
        endDate: Long,
        limit: Int
    ): List<Transaction>

    fun getTransactionBetweenDates(
        startDate: Long,
        endDate: Long,
        limit: Int
    ): List<Transaction>

    suspend fun saveTransaction(transaction: Transaction)
    suspend fun getTransactionById(transactionId: Int): Transaction
}