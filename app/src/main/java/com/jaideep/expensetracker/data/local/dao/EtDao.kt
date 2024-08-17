package com.jaideep.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface EtDao {

    @Query(
        "Select sum(amount) from `Transaction` where isCredit = 0 and createdTime >= :time"
    )
    suspend fun getAmountSpentToday(time: Long): Double

    @Query(
        "Select sum(amount) from `Transaction`" + " where accountId = :accountId and isCredit = 0 and createdTime >= :time"
    )
    suspend fun getAmountSpentTodayFromAccount(time: Long, accountId: Int): Double

    @Query(
        "Select sum(amount) from `Transaction`" + " where isCredit = 0 and createdTime >= :time"
    )
    suspend fun getAmountSpentThisMonth(time: Long): Double

    @Query(
        "Select sum(amount) from `Transaction`" + " where accountId = :accountId and isCredit = 0 and createdTime >= :time"
    )
    suspend fun getAmountSpentThisMonthFromAccount(time: Long, accountId: Int): Double

    @Query(
        "Select sum(amount) from `Transaction`" + "where categoryId = :categoryId and accountId = :accountId"
    )
    suspend fun getAmountSpentThisMonthByCategoryFromAccount(
        categoryId: Int,
        accountId: Int
    ): Double

    @Query(
        "Select sum(amount) from `Transaction`" + "where categoryId = :categoryId"
    )
    suspend fun getAmountSpentThisMonthByCategory(categoryId: Int): Double
}