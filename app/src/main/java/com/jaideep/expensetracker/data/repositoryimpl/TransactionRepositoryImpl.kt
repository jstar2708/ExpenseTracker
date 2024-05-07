package com.jaideep.expensetracker.data.repositoryimpl

import com.jaideep.expensetracker.data.dao.TransactionDao
import com.jaideep.expensetracker.data.entities.Transaction
import com.jaideep.expensetracker.domain.repository.TransactionRepository
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
) : TransactionRepository{
    override suspend fun getTransactionsForAccount(accountId: Int): List<Transaction> {
        return dao.getTransactionsForAccount(accountId)
    }

    override suspend fun getDebitTransactionsForAccount(accountId: Int): List<Transaction> {
        return dao.getDebitTransactionsForAccount(accountId)
    }

    override suspend fun getCreditTransactionsForAccount(accountId: Int): List<Transaction> {
        return dao.getCreditTransactionsForAccount(accountId)
    }

    override suspend fun getCreditTransactionBetweenDatesForAccount(
        accountId: Int,
        startDate: Long,
        endDate: Long
    ): List<Transaction> {
        return dao.getCreditTransactionBetweenDatesForAccount(accountId, startDate, endDate)
    }

    override suspend fun getDebitTransactionBetweenDatesForAccount(
        accountId: Int,
        startDate: Long,
        endDate: Long
    ): List<Transaction> {
        return dao.getDebitTransactionBetweenDatesForAccount(accountId, startDate, endDate)
    }

    override suspend fun getTransactionBetweenDatesForAccount(
        accountId: Int,
        startDate: Long,
        endDate: Long
    ): List<Transaction> {
        return dao.getTransactionBetweenDatesForAccount(accountId, startDate, endDate)
    }

    override suspend fun getAllTransactions(): List<Transaction> {
        return dao.getAllTransactions()
    }

    override suspend fun getDebitTransactions(): List<Transaction> {
        return dao.getDebitTransactions()
    }

    override suspend fun getCreditTransactions(): List<Transaction> {
        return dao.getCreditTransactions()
    }

    override suspend fun getCreditTransactionBetweenDates(
        startDate: Long,
        endDate: Long
    ): List<Transaction> {
        return dao.getCreditTransactionBetweenDates(startDate, endDate)
    }

    override suspend fun getDebitTransactionBetweenDates(
        startDate: Long,
        endDate: Long
    ): List<Transaction> {
        return dao.getDebitTransactionBetweenDates(startDate, endDate)
    }

    override suspend fun getTransactionBetweenDates(
        startDate: Long,
        endDate: Long
    ): List<Transaction> {
        return dao.getTransactionBetweenDates(startDate, endDate)
    }

    override suspend fun saveTransaction(transaction: Transaction) {
        dao.saveTransaction(transaction)
    }

    override suspend fun getTransactionById(transactionId: Int): Transaction {
        return dao.getTransactionById(transactionId)
    }
}