package com.jaideep.expensetracker.domain.repository

import androidx.paging.PagingSource
import com.jaideep.expensetracker.data.local.entities.Transaction

interface TransactionPagingRepository {
    fun getTransactionsForAccount(accountId: Int): PagingSource<Int, Transaction>
    fun getDebitTransactionsForAccount(accountId: Int): PagingSource<Int, Transaction>
    fun getCreditTransactionsForAccount(accountId: Int): PagingSource<Int, Transaction>
    fun getCreditTransactionBetweenDatesForAccount(
        accountId: Int,
        startDate: Long,
        endDate: Long,
    ): PagingSource<Int, Transaction>

    fun getDebitTransactionBetweenDatesForAccount(
        accountId: Int,
        startDate: Long,
        endDate: Long,
    ): PagingSource<Int, Transaction>

    fun getTransactionBetweenDatesForAccount(
        accountId: Int,
        startDate: Long,
        endDate: Long,
    ): PagingSource<Int, Transaction>

    fun getAllTransactions(): PagingSource<Int, Transaction>
    fun getDebitTransactions(): PagingSource<Int, Transaction>
    fun getCreditTransactions(): PagingSource<Int, Transaction>
    fun getCreditTransactionBetweenDates(
        startDate: Long,
        endDate: Long,
    ): PagingSource<Int, Transaction>

    fun getDebitTransactionBetweenDates(
        startDate: Long,
        endDate: Long,
    ): PagingSource<Int, Transaction>

    fun getTransactionBetweenDates(
        startDate: Long,
        endDate: Long,
    ): PagingSource<Int, Transaction>

    suspend fun saveTransaction(transaction: Transaction)
    suspend fun getTransactionById(transactionId: Int): Transaction
}