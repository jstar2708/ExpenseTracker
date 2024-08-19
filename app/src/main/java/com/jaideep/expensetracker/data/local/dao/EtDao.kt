package com.jaideep.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_ACCOUNT_BALANCE_BY_NAME
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_ACCOUNT_BALANCE_FOR_ALL_ACCOUNTS
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_AMOUNT_SPENT_FROM_ACCOUNT
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_AMOUNT_SPENT_FROM_ALL_ACCOUNT
import kotlinx.coroutines.flow.Flow

@Dao
interface EtDao {

    @Query(GET_AMOUNT_SPENT_FROM_ACCOUNT)
    fun getAmountSpentFromAccountToday(date: Long, accountName: String): Flow<Double>

    @Query(GET_ACCOUNT_BALANCE_BY_NAME)
    fun getAccountBalanceByName(accountName: String): Flow<Double>
    @Query(GET_AMOUNT_SPENT_FROM_ALL_ACCOUNT)
    fun getAmountSpentFromAllAccountsToday(date: Long): Flow<Double>
    @Query(GET_ACCOUNT_BALANCE_FOR_ALL_ACCOUNTS)
    fun getAccountBalanceForAllAccounts(): Flow<Double>
}