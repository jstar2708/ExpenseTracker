package com.jaideep.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jaideep.expensetracker.common.constant.sql.AccountSql.UPDATE_ACCOUNT_BALANCE
import com.jaideep.expensetracker.data.local.entities.Transaction

@Dao
interface CrudDao {

    @androidx.room.Transaction
    suspend fun saveTransactionAndUpdateBalance(transaction: Transaction) {
        saveTransaction(transaction)
        updateAccountBalance(
            transaction.accountId,
            if (transaction.isCredit == 1) transaction.amount else -transaction.amount
        )
    }

    @androidx.room.Transaction
    suspend fun updateTransactionAndUpdateBalance(
        transaction: Transaction, previousAmount: Double
    ) {
        updateTransaction(transaction)
        updateAccountBalance(
            transaction.accountId,
            if (transaction.isCredit == 1) previousAmount + transaction.amount else previousAmount - transaction.amount
        )
    }

    @Query(UPDATE_ACCOUNT_BALANCE)
    suspend fun updateAccountBalance(accountId: Int, amount: Double)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTransaction(transaction: Transaction)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTransaction(transaction: Transaction)
}