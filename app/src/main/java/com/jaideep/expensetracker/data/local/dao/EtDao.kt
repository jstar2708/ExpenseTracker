package com.jaideep.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_ACCOUNT_BALANCE_BY_NAME
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_AMOUNT_SPENT_FROM_ACCOUNT

@Dao
interface EtDao {

    @Query(GET_AMOUNT_SPENT_FROM_ACCOUNT)
    suspend fun getAmountSpentFromAccountToday(date: Long, accountName: String): Double

    @Query(GET_ACCOUNT_BALANCE_BY_NAME)
    fun getAccountBalanceByName(accountName: String): Double
}