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
        accountName: String, limit: Int
    ): Flow<List<Transaction>> {
        return dao.getTransactionsForAccount(accountName, limit)
    }

    override fun getDebitTransactionsForAccount(
        accountName: String,
        limit: Int
    ): Flow<List<Transaction>> {
        return dao.getDebitTransactionsForAccount(accountName, limit)
    }

    override fun getCreditTransactionsForAccount(
        accountName: String,
        limit: Int
    ): Flow<List<Transaction>> {
        return dao.getCreditTransactionsForAccount(accountName, limit)
    }

    override fun getCreditTransactionBetweenDatesForAccount(
        accountName: String, startDate: Long, endDate: Long, limit: Int
    ): Flow<List<Transaction>> {
        return dao.getCreditTransactionBetweenDatesForAccount(accountName, startDate, endDate, limit)
    }

    override fun getDebitTransactionBetweenDatesForAccount(
        accountName: String, startDate: Long, endDate: Long, limit: Int
    ): Flow<List<Transaction>> {
        return dao.getDebitTransactionBetweenDatesForAccount(accountName, startDate, endDate, limit)
    }

    override fun getTransactionBetweenDatesForAccount(
        accountName: String, startDate: Long, endDate: Long, limit: Int
    ): Flow<List<Transaction>> {
        return dao.getTransactionBetweenDatesForAccount(accountName, startDate, endDate, limit)
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