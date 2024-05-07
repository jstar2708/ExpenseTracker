package com.jaideep.expensetracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jaideep.expensetracker.data.entities.Account
import com.jaideep.expensetracker.data.entities.Category
import com.jaideep.expensetracker.data.entities.Transaction

@Dao
interface EtDao {

    @Query(
        "Select sum(amount) from `Transaction` where isCredit = 0 and createdTime >= :time"
    )
    suspend fun getAmountSpentToday(time: Long) : Double

    @Query(
        "Select sum(amount) from `Transaction`" +
                " where accountId = :accountId and isCredit = 0 and createdTime >= :time"
    )
    suspend fun getAmountSpentTodayFromAccount(time: Long, accountId: Int) : Double

    @Query(
        "Select sum(amount) from `Transaction`" +
                " where isCredit = 0 and createdTime >= :time"
    )
    suspend fun getAmountSpentThisMonth(time: Long) : Double

    @Query(
        "Select sum(amount) from `Transaction`" +
                " where accountId = :accountId and isCredit = 0 and createdTime >= :time"
    )
    suspend fun getAmountSpentThisMonthFromAccount(time: Long, accountId: Int) : Double

    @Query("Select sum(amount) from `Transaction`" +
            "where categoryId = :categoryId and accountId = :accountId")
    suspend fun getAmountSpentThisMonthByCategoryFromAccount(categoryId: Int, accountId: Int) : Double

    @Query("Select sum(amount) from `Transaction`" +
            "where categoryId = :categoryId")
    suspend fun getAmountSpentThisMonthByCategory(categoryId: Int) : Double
}