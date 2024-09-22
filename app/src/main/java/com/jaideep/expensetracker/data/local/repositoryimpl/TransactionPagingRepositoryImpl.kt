package com.jaideep.expensetracker.data.local.repositoryimpl

import androidx.paging.PagingSource
import com.jaideep.expensetracker.data.local.dao.TransactionPagingDao
import com.jaideep.expensetracker.data.local.entities.Transaction
import com.jaideep.expensetracker.domain.repository.TransactionPagingRepository
import com.jaideep.expensetracker.model.dto.TransactionDto
import javax.inject.Inject

class TransactionPagingRepositoryImpl @Inject constructor(
    private val dao: TransactionPagingDao
) : TransactionPagingRepository {

    override fun getTransactionsForAccount(
        accountId: Int
    ): PagingSource<Int, TransactionDto> {
        return dao.getTransactionsForAccount(accountId)
    }

    override fun getDebitTransactionsForAccount(
        accountId: Int
    ): PagingSource<Int, TransactionDto> {
        return dao.getDebitTransactionsForAccount(accountId)
    }

    override fun getCreditTransactionsForAccount(
        accountId: Int
    ): PagingSource<Int, TransactionDto> {
        return dao.getCreditTransactionsForAccount(accountId)
    }

    override fun getCreditTransactionBetweenDatesForAccount(
        accountId: Int, startDate: Long, endDate: Long,
    ): PagingSource<Int, TransactionDto> {
        return dao.getCreditTransactionBetweenDatesForAccount(accountId, startDate, endDate)
    }

    override fun getDebitTransactionBetweenDatesForAccount(
        accountId: Int, startDate: Long, endDate: Long,
    ): PagingSource<Int, TransactionDto> {
        return dao.getDebitTransactionBetweenDatesForAccount(accountId, startDate, endDate)
    }

    override fun getTransactionBetweenDatesForAccount(
        accountId: Int, startDate: Long, endDate: Long,
    ): PagingSource<Int, TransactionDto> {
        return dao.getTransactionBetweenDatesForAccount(accountId, startDate, endDate)
    }

    override fun getAllTransactions(): PagingSource<Int, TransactionDto> {
        return dao.getAllTransactions()
    }

    override fun getDebitTransactions(): PagingSource<Int, TransactionDto> {
        return dao.getDebitTransactions()
    }

    override fun getCreditTransactions(): PagingSource<Int, TransactionDto> {
        return dao.getCreditTransactions()
    }

    override fun getCreditTransactionBetweenDates(
        startDate: Long, endDate: Long,
    ): PagingSource<Int, TransactionDto> {
        return dao.getCreditTransactionBetweenDates(startDate, endDate)
    }

    override fun getDebitTransactionBetweenDates(
        startDate: Long, endDate: Long,
    ): PagingSource<Int, TransactionDto> {
        return dao.getDebitTransactionBetweenDates(startDate, endDate)
    }

    override fun getTransactionBetweenDates(
        startDate: Long, endDate: Long,
    ): PagingSource<Int, TransactionDto> {
        return dao.getTransactionBetweenDates(startDate, endDate)
    }

    override suspend fun saveTransaction(transaction: Transaction) {
        dao.saveTransaction(transaction)
    }

    override suspend fun getTransactionById(transactionId: Int): TransactionDto {
        return dao.getTransactionById(transactionId)
    }
}