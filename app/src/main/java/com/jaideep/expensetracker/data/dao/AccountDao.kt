package com.jaideep.expensetracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jaideep.expensetracker.data.entities.Account
import com.jaideep.expensetracker.common.AccountSql.GET_ALL_ACCOUNTS;
import com.jaideep.expensetracker.common.AccountSql.GET_ACCOUNT_BY_ID;

@Dao
interface AccountDao {
    @Query(GET_ALL_ACCOUNTS)
    suspend fun getAccounts() : List<Account>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAccount(account: Account)
    @Query(GET_ACCOUNT_BY_ID)
    suspend fun getAccountById(accountId: Int) : Account
    @Update
    suspend fun updateAccount(account: Account)
}