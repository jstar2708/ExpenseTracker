package com.jaideep.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jaideep.expensetracker.common.constant.sql.AccountSql.GET_ACCOUNT_BY_ID
import com.jaideep.expensetracker.common.constant.sql.AccountSql.GET_ALL_ACCOUNTS
import com.jaideep.expensetracker.data.local.entities.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Query(GET_ALL_ACCOUNTS)
    fun getAccounts(): Flow<List<Account>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAccount(account: Account)

    @Query(GET_ACCOUNT_BY_ID)
    suspend fun getAccountById(accountId: Int): Account

    @Update
    suspend fun updateAccount(account: Account)
}