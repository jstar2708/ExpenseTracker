package com.example.expensetracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.expensetracker.data.entities.Account
import com.example.expensetracker.data.entities.Category
import com.example.expensetracker.data.entities.Transaction

@Dao
interface EtDao {

    @Query("Select * from Account")
    suspend fun getAccounts() : List<Account>

    @Query("Select * from Category")
    suspend fun getCategories() : List<Category>

    @Query(
        "Select sum(amount) from `Transaction`" +
            " where isCredit = 0 and dateTime >= :time")
    suspend fun getAmountSpentToday(time: Long) : Double

    @Query(
        "Select sum(amount) from `Transaction`" +
            " where accountId = :accountId and isCredit = 0 and dateTime >= :time")
    suspend fun getAmountSpentTodayFromAccount(time: Long, accountId: Int) : Double

    @Query(
        "Select sum(amount) from `Transaction`" +
            " where isCredit = 0 and dateTime >= :time")
    suspend fun getAmountSpentThisMonth(time: Long) : Double

    @Query(
        "Select sum(amount) from `Transaction`" +
            " where accountId = :accountId and isCredit = 0 and dateTime >= :time")
    suspend fun getAmountSpentThisMonthFromAccount(time: Long, accountId: Int) : Double

    @Query("Select sum(amount) from `Transaction`" +
            "where categoryId = :categoryId and accountId = :accountId")
    suspend fun getAmountSpentThisMonthByCategoryFromAccount(categoryId: Int, accountId: Int) : Double

    @Query("Select sum(amount) from `Transaction`" +
            "where categoryId = :categoryId")
    suspend fun getAmountSpentThisMonthByCategory(categoryId: Int) : Double

    @Query("Select * from `Transaction`" +
            "where accountId = :accountId")
    suspend fun getTransactionsForAccount(accountId: Int): List<Transaction>

    @Query("Select * from `Transaction`" +
            "where accountId = :accountId and isCredit = 0")
    suspend fun getDebitTransactionsForAccount(accountId: Int) : List<Transaction>

    @Query("Select * from `Transaction`" +
            "where accountId = :accountId and isCredit = 1")
    suspend fun getCreditTransactionsForAccount(accountId: Int) : List<Transaction>

    @Query("Select * from `Transaction`" +
            "where accountId = :accountId and isCredit = 1 and dateTime >= :startDate and dateTime <= :endDate")
    suspend fun getCreditTransactionBetweenDatesForAccount(accountId: Int, startDate: Long, endDate: Long) : List<Transaction>

    @Query("Select * from `Transaction`" +
            "where accountId = :accountId and isCredit = 0 and dateTime >= :startDate and dateTime <= :endDate")
    suspend fun getDebitTransactionBetweenDatesForAccount(accountId: Int, startDate: Long, endDate: Long) : List<Transaction>

    @Query("Select * from `Transaction`" +
            "where accountId = :accountId and dateTime >= :startDate and dateTime <= :endDate")
    suspend fun getTransactionBetweenDatesForAccount(accountId: Int, startDate: Long, endDate: Long): List<Transaction>

    @Query("Select * from `Transaction`")
    suspend fun getTransactions(): List<Transaction>

    @Query("Select * from `Transaction`" +
            "where isCredit = 0")
    suspend fun getDebitTransactions() : List<Transaction>

    @Query("Select * from `Transaction`" +
            "where isCredit = 1")
    suspend fun getCreditTransactions() : List<Transaction>

    @Query("Select * from `Transaction`" +
            "where isCredit = 1 and dateTime >= :startDate and dateTime <= :endDate")
    suspend fun getCreditTransactionBetweenDates(startDate: Long, endDate: Long) : List<Transaction>

    @Query("Select * from `Transaction`" +
            "where isCredit = 0 and dateTime >= :startDate and dateTime <= :endDate")
    suspend fun getDebitTransactionBetweenDates(startDate: Long, endDate: Long) : List<Transaction>

    @Query("Select * from `Transaction`" +
            "where dateTime >= :startDate and dateTime <= :endDate")
    suspend fun getTransactionBetweenDates(startDate: Long, endDate: Long) : List<Transaction>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTransaction(transaction: Transaction)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAccount(account: Account)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCategory(category: Category)

    @Query("Select * from account where id = :accountId")
    suspend fun getAccountById(accountId: Int) : Account

    @Query("Select * from `Transaction` where id = :transactionId")
    suspend fun getTransactionById(transactionId: Int) : Transaction

    @Query("Select * from category where id = :categoryId")
    suspend fun getCategoryById(categoryId: Int) : Category
}