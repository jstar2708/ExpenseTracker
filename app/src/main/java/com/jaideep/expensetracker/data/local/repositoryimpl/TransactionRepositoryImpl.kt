package com.jaideep.expensetracker.data.local.repositoryimpl

import com.jaideep.expensetracker.data.local.dao.TransactionDao
import com.jaideep.expensetracker.domain.repository.TransactionRepository
import com.jaideep.expensetracker.model.dto.TransactionDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
) : TransactionRepository {
    override fun getTransactionsForAccount(
        accountName: String, limit: Int
    ): Flow<List<TransactionDto>> {
        return dao.getTransactionsForAccount(accountName, limit)
    }

    override fun getDebitTransactionsForAccount(
        accountName: String, limit: Int
    ): Flow<List<TransactionDto>> {
        return dao.getDebitTransactionsForAccount(accountName, limit)
    }

    override fun getCreditTransactionsForAccount(
        accountName: String, limit: Int
    ): Flow<List<TransactionDto>> {
        return dao.getCreditTransactionsForAccount(accountName, limit)
    }

    override fun getCreditTransactionBetweenDatesForAccount(
        accountName: String, startDate: Long, endDate: Long, limit: Int
    ): Flow<List<TransactionDto>> {
        return dao.getCreditTransactionBetweenDatesForAccount(
            accountName, startDate, endDate, limit
        )
    }

    override fun getDebitTransactionBetweenDatesForAccount(
        accountName: String, startDate: Long, endDate: Long, limit: Int
    ): Flow<List<TransactionDto>> {
        return dao.getDebitTransactionBetweenDatesForAccount(accountName, startDate, endDate, limit)
    }

    override fun getTransactionBetweenDatesForAccount(
        accountName: String, startDate: Long, endDate: Long, limit: Int
    ): Flow<List<TransactionDto>> {
        return dao.getTransactionBetweenDatesForAccount(accountName, startDate, endDate, limit)
    }

    override fun getAllTransactions(limit: Int): Flow<List<TransactionDto>> {
        return dao.getAllTransactions(limit)
    }

    override fun getDebitTransactions(limit: Int): Flow<List<TransactionDto>> {
        return dao.getDebitTransactions(limit)
    }

    override fun getCreditTransactions(limit: Int): Flow<List<TransactionDto>> {
        return dao.getCreditTransactions(limit)
    }

    override fun getCreditTransactionBetweenDates(
        startDate: Long, endDate: Long, limit: Int
    ): Flow<List<TransactionDto>> {
        return dao.getCreditTransactionBetweenDates(startDate, endDate, limit)
    }

    override fun getDebitTransactionBetweenDates(
        startDate: Long, endDate: Long, limit: Int
    ): Flow<List<TransactionDto>> {
        return dao.getDebitTransactionBetweenDates(startDate, endDate, limit)
    }

    override fun getTransactionBetweenDates(
        startDate: Long, endDate: Long, limit: Int

    ): Flow<List<TransactionDto>> {
        return dao.getTransactionBetweenDates(startDate, endDate, limit)
    }

    override suspend fun getTransactionById(transactionId: Int): TransactionDto {
        return dao.getTransactionById(transactionId)
    }

    override suspend fun deleteTransactionById(transactionId: Int) {
        return dao.deleteTransactionById(transactionId)
    }
}