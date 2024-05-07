package com.jaideep.expensetracker.domain.repository

import com.jaideep.expensetracker.data.entities.Transaction

interface TransactionRepository {
    suspend fun getTransactionsForAccount(accountId: Int): List<Transaction>
    suspend fun getDebitTransactionsForAccount(accountId: Int) : List<Transaction>
    suspend fun getCreditTransactionsForAccount(accountId: Int) : List<Transaction>
    suspend fun getCreditTransactionBetweenDatesForAccount(accountId: Int, startDate: Long, endDate: Long) : List<Transaction>
    suspend fun getDebitTransactionBetweenDatesForAccount(accountId: Int, startDate: Long, endDate: Long) : List<Transaction>
    suspend fun getTransactionBetweenDatesForAccount(accountId: Int, startDate: Long, endDate: Long): List<Transaction>
    suspend fun getAllTransactions(): List<Transaction>
    suspend fun getDebitTransactions() : List<Transaction>
    suspend fun getCreditTransactions() : List<Transaction>
    suspend fun getCreditTransactionBetweenDates(startDate: Long, endDate: Long) : List<Transaction>
    suspend fun getDebitTransactionBetweenDates(startDate: Long, endDate: Long) : List<Transaction>
    suspend fun getTransactionBetweenDates(startDate: Long, endDate: Long) : List<Transaction>
    suspend fun saveTransaction(transaction: Transaction)
    suspend fun getTransactionById(transactionId: Int) : Transaction
}