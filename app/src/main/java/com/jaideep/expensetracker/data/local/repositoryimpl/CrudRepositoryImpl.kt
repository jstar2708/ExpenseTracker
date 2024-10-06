package com.jaideep.expensetracker.data.local.repositoryimpl

import com.jaideep.expensetracker.data.local.dao.CrudDao
import com.jaideep.expensetracker.data.local.dao.DatabaseDao
import com.jaideep.expensetracker.data.local.entities.Transaction
import com.jaideep.expensetracker.domain.repository.CrudRepository
import javax.inject.Inject

class CrudRepositoryImpl @Inject constructor(
    private val crudDao: CrudDao,
    private val databaseDao: DatabaseDao
) : CrudRepository {

    override suspend fun saveTransactionAndUpdateBalance(transaction: Transaction) {
        return crudDao.saveTransactionAndUpdateBalance(transaction)
    }

    override suspend fun updateTransactionAndUpdateBalance(
        transaction: Transaction, previousAmount: Double
    ) {
        return crudDao.updateTransactionAndUpdateBalance(transaction, previousAmount)
    }

    override suspend fun clearAllData() {
        return databaseDao.clearAllData()
    }
}

