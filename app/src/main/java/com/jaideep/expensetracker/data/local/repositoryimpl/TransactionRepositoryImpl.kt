package com.jaideep.expensetracker.data.local.repositoryimpl

import com.jaideep.expensetracker.data.local.dao.TransactionDao
import com.jaideep.expensetracker.data.local.entities.Transaction
import com.jaideep.expensetracker.data.local.paging.TransactionPagingSource
import com.jaideep.expensetracker.domain.repository.TransactionRepository
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
) : TransactionRepository{
    override fun getTransactionPagingSource() = TransactionPagingSource(dao)

    override suspend fun getTransactionsForAccount(accountId: Int, limit: Int, offset: Int): List<Transaction> {
        return dao.getTransactionsForAccount(accountId, limit, offset)
    }

    override suspend fun getDebitTransactionsForAccount(accountId: Int, limit: Int, offset: Int): List<Transaction> {
        return dao.getDebitTransactionsForAccount(accountId, limit, offset)
    }

    override suspend fun getCreditTransactionsForAccount(accountId: Int, limit: Int, offset: Int): List<Transaction> {
        return dao.getCreditTransactionsForAccount(accountId, limit, offset)
    }

    override suspend fun getCreditTransactionBetweenDatesForAccount(
        accountId: Int,
        startDate: Long,
        endDate: Long,
        limit: Int,
        offset: Int
    ): List<Transaction> {
        return dao.getCreditTransactionBetweenDatesForAccount(accountId, startDate, endDate, limit, offset)
    }

    override suspend fun getDebitTransactionBetweenDatesForAccount(
        accountId: Int,
        startDate: Long,
        endDate: Long,
        limit: Int,
        offset: Int
    ): List<Transaction> {
        return dao.getDebitTransactionBetweenDatesForAccount(accountId, startDate, endDate, limit, offset)
    }

    override suspend fun getTransactionBetweenDatesForAccount(
        accountId: Int,
        startDate: Long,
        endDate: Long,
        limit: Int,
        offset: Int
    ): List<Transaction> {
        return dao.getTransactionBetweenDatesForAccount(accountId, startDate, endDate, limit, offset)
    }

    override suspend fun getAllTransactions(limit: Int, offset: Int): List<Transaction> {
        return dao.getAllTransactions(limit, offset)
    }

    override suspend fun getDebitTransactions(limit: Int, offset: Int): List<Transaction> {
        return dao.getDebitTransactions(limit, offset)
    }

    override suspend fun getCreditTransactions(limit: Int, offset: Int): List<Transaction> {
        return dao.getCreditTransactions(limit, offset)
    }

    override suspend fun getCreditTransactionBetweenDates(
        startDate: Long,
        endDate: Long,
        limit: Int,
        offset: Int
    ): List<Transaction> {
        return dao.getCreditTransactionBetweenDates(startDate, endDate, limit, offset)
    }

    override suspend fun getDebitTransactionBetweenDates(
        startDate: Long,
        endDate: Long,
        limit: Int,
        offset: Int
    ): List<Transaction> {
        return dao.getDebitTransactionBetweenDates(startDate, endDate, limit, offset)
    }

    override suspend fun getTransactionBetweenDates(
        startDate: Long,
        endDate: Long,
        limit: Int,
        offset: Int
    ): List<Transaction> {
        return dao.getTransactionBetweenDates(startDate, endDate, limit, offset)
    }

    override suspend fun saveTransaction(transaction: Transaction) {
        dao.saveTransaction(transaction)
    }

    override suspend fun getTransactionById(transactionId: Int): Transaction {
        return dao.getTransactionById(transactionId)
    }
}