package com.jaideep.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.jaideep.expensetracker.common.constant.sql.DatabaseSql.DELETE_ALL_ACCOUNTS
import com.jaideep.expensetracker.common.constant.sql.DatabaseSql.DELETE_ALL_CATEGORIES
import com.jaideep.expensetracker.common.constant.sql.DatabaseSql.DELETE_ALL_TRANSACTIONS
import com.jaideep.expensetracker.common.constant.sql.DatabaseSql.DELETE_PRIMARY_KEY_IDX

@Dao
interface DatabaseDao {

    @Query(DELETE_PRIMARY_KEY_IDX)
    suspend fun clearPrimaryKeyIndex()

    @Query(DELETE_ALL_TRANSACTIONS)
    suspend fun clearTransactionsData()

    @Query(DELETE_ALL_ACCOUNTS)
    suspend fun clearAccountData()

    @Query(DELETE_ALL_CATEGORIES)
    suspend fun clearCategoryData()

    @Transaction
    suspend fun clearAllData() {
        clearTransactionsData()
        clearAccountData()
        clearCategoryData()
        clearPrimaryKeyIndex()
    }

}