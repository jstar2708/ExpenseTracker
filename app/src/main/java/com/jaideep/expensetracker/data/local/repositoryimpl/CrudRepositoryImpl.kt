package com.jaideep.expensetracker.data.local.repositoryimpl

import com.jaideep.expensetracker.data.local.dao.AccountDao
import com.jaideep.expensetracker.data.local.dao.CategoryDao
import com.jaideep.expensetracker.data.local.dao.TransactionDao
import com.jaideep.expensetracker.data.local.entities.Transaction
import com.jaideep.expensetracker.domain.repository.CrudRepository
import javax.inject.Inject

class CrudRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao,
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao,
) : CrudRepository {


    @androidx.room.Transaction
    override suspend fun saveTransactionAndUpdateBalance(transaction: Transaction) {
        transactionDao.saveTransaction(transaction)
        accountDao.updateAccountBalance(
            transaction.accountId,
            if (transaction.isCredit == 1) transaction.amount else -transaction.amount
        )
    }

    @androidx.room.Transaction
    override suspend fun updateTransactionAndUpdateBalance(
        transaction: Transaction, previousAmount: Double
    ) {
        transactionDao.updateTransaction(transaction)
        accountDao.updateAccountBalance(
            transaction.accountId,
            if (transaction.isCredit == 1) previousAmount + transaction.amount else previousAmount - transaction.amount
        )
    }
}

