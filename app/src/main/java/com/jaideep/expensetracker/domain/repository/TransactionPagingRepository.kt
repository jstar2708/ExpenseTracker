package com.jaideep.expensetracker.domain.repository

import androidx.paging.PagingSource
import com.jaideep.expensetracker.data.local.entities.Transaction
import com.jaideep.expensetracker.model.dto.TransactionDto

interface TransactionPagingRepository {
    fun getTransactionsForAccount(accountId: Int): PagingSource<Int, TransactionDto>
    fun getDebitTransactionsForAccount(accountId: Int): PagingSource<Int, TransactionDto>
    fun getCreditTransactionsForAccount(accountId: Int): PagingSource<Int, TransactionDto>
    fun getCreditTransactionBetweenDatesForAccount(
        accountId: Int,
        startDate: Long,
        endDate: Long,
    ): PagingSource<Int, TransactionDto>

    fun getDebitTransactionBetweenDatesForAccount(
        accountId: Int,
        startDate: Long,
        endDate: Long,
    ): PagingSource<Int, TransactionDto>

    fun getTransactionBetweenDatesForAccount(
        accountId: Int,
        startDate: Long,
        endDate: Long,
    ): PagingSource<Int, TransactionDto>

    fun getAllTransactions(): PagingSource<Int, TransactionDto>
    fun getDebitTransactions(): PagingSource<Int, TransactionDto>
    fun getCreditTransactions(): PagingSource<Int, TransactionDto>
    fun getCreditTransactionBetweenDates(
        startDate: Long,
        endDate: Long,
    ): PagingSource<Int, TransactionDto>

    fun getDebitTransactionBetweenDates(
        startDate: Long,
        endDate: Long,
    ): PagingSource<Int, TransactionDto>

    fun getTransactionBetweenDates(
        startDate: Long,
        endDate: Long,
    ): PagingSource<Int, TransactionDto>

    suspend fun saveTransaction(transaction: Transaction)
    suspend fun getTransactionById(transactionId: Int): TransactionDto
}