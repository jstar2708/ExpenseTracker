package com.jaideep.expensetracker.domain.repository

import com.jaideep.expensetracker.data.local.entities.Transaction

interface CrudRepository {
    suspend fun saveTransactionAndUpdateBalance(transaction: Transaction)
    suspend fun updateTransactionAndUpdateBalance(transaction: Transaction, previousAmount: Double)
    suspend fun clearAllData()
}