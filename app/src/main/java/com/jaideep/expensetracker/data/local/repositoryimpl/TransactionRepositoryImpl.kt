package com.jaideep.expensetracker.data.local.repositoryimpl

import com.jaideep.expensetracker.data.local.dao.TransactionDao
import com.jaideep.expensetracker.data.local.entities.Transaction
import com.jaideep.expensetracker.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
) : TransactionRepository {
    override fun getTransactionsForAccount(
        accountId: Int, limit: Int
    ): Flow<List<Transaction>> {
        return dao.getTransactionsForAccount(accountId, limit)
    }

    override fun getDebitTransactionsForAccount(
        accountId: Int,
        limit: Int
    ): Flow<List<Transaction>> {
        return dao.getDebitTransactionsForAccount(accountId, limit)
    }

    override fun getCreditTransactionsForAccount(
        accountId: Int,
        limit: Int
    ): Flow<List<Transaction>> {
        return dao.getCreditTransactionsForAccount(accountId, limit)
    }

    override fun getCreditTransactionBetweenDatesForAccount(
        accountId: Int, startDate: Long, endDate: Long, limit: Int
    ): Flow<List<Transaction>> {
        return dao.getCreditTransactionBetweenDatesForAccount(accountId, startDate, endDate, limit)
    }

    override fun getDebitTransactionBetweenDatesForAccount(
        accountId: Int, startDate: Long, endDate: Long, limit: Int
    ): Flow<List<Transaction>> {
        return dao.getDebitTransactionBetweenDatesForAccount(accountId, startDate, endDate, limit)
    }

    override fun getTransactionBetweenDatesForAccount(
        accountId: Int, startDate: Long, endDate: Long, limit: Int
    ): Flow<List<Transaction>> {
        return dao.getTransactionBetweenDatesForAccount(accountId, startDate, endDate, limit)
    }

    override fun getAllTransactions(limit: Int): Flow<List<Transaction>> {
        return dao.getAllTransactions(limit)
    }

    override fun getDebitTransactions(limit: Int): Flow<List<Transaction>> {
        return dao.getDebitTransactions(limit)
    }

    override fun getCreditTransactions(limit: Int): Flow<List<Transaction>> {
        return dao.getCreditTransactions(limit)
    }

    override fun getCreditTransactionBetweenDates(
        startDate: Long, endDate: Long, limit: Int
    ): Flow<List<Transaction>> {
        return dao.getCreditTransactionBetweenDates(startDate, endDate, limit)
    }

    override fun getDebitTransactionBetweenDates(
        startDate: Long, endDate: Long, limit: Int
    ): Flow<List<Transaction>> {
        return dao.getDebitTransactionBetweenDates(startDate, endDate, limit)
    }

    override fun getTransactionBetweenDates(
        startDate: Long, endDate: Long, limit: Int
    ): Flow<List<Transaction>> {
        return dao.getTransactionBetweenDates(startDate, endDate, limit)
    }

    override suspend fun saveTransaction(transaction: Transaction) {
        dao.saveTransaction(transaction)
    }

    override suspend fun getTransactionById(transactionId: Int): Transaction {
        return dao.getTransactionById(transactionId)
    }
}