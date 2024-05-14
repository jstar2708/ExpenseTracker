package com.jaideep.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jaideep.expensetracker.common.TransactionSql.GET_ACCOUNT_CREDIT_TRANSACTIONS
import com.jaideep.expensetracker.common.TransactionSql.GET_ACCOUNT_CREDIT_TRANSACTIONS_BETWEEN_DATES
import com.jaideep.expensetracker.common.TransactionSql.GET_ACCOUNT_DEBIT_TRANSACTIONS
import com.jaideep.expensetracker.common.TransactionSql.GET_ACCOUNT_DEBIT_TRANSACTIONS_BETWEEN_DATES
import com.jaideep.expensetracker.common.TransactionSql.GET_ACCOUNT_TRANSACTIONS
import com.jaideep.expensetracker.common.TransactionSql.GET_ACCOUNT_TRANSACTIONS_BETWEEN_DATES
import com.jaideep.expensetracker.common.TransactionSql.GET_ALL_CREDIT_TRANSACTIONS
import com.jaideep.expensetracker.common.TransactionSql.GET_ALL_DEBIT_TRANSACTIONS
import com.jaideep.expensetracker.common.TransactionSql.GET_ALL_TRANSACTIONS
import com.jaideep.expensetracker.common.TransactionSql.GET_CREDIT_TRANSACTIONS_BETWEEN_DATES
import com.jaideep.expensetracker.common.TransactionSql.GET_DEBIT_TRANSACTIONS_BETWEEN_DATES
import com.jaideep.expensetracker.common.TransactionSql.GET_TRANSACTIONS_BETWEEN_DATES
import com.jaideep.expensetracker.common.TransactionSql.GET_TRANSACTION_BY_ID
import com.jaideep.expensetracker.data.local.entities.Transaction

@Dao
interface TransactionDao {
    @Query(GET_ACCOUNT_TRANSACTIONS)
    suspend fun getTransactionsForAccount(accountId: Int, limit: Int, offset: Int): List<Transaction>

    @Query(GET_ACCOUNT_DEBIT_TRANSACTIONS)
    suspend fun getDebitTransactionsForAccount(accountId: Int, limit: Int, offset: Int): List<Transaction>

    @Query(GET_ACCOUNT_CREDIT_TRANSACTIONS)
    suspend fun getCreditTransactionsForAccount(accountId: Int, limit: Int, offset: Int): List<Transaction>

    @Query(GET_ACCOUNT_CREDIT_TRANSACTIONS_BETWEEN_DATES)
    suspend fun getCreditTransactionBetweenDatesForAccount(
        accountId: Int,
        startDate: Long,
        endDate: Long,
        limit: Int,
        offset: Int
    ): List<Transaction>

    @Query(GET_ACCOUNT_DEBIT_TRANSACTIONS_BETWEEN_DATES)
    suspend fun getDebitTransactionBetweenDatesForAccount(
        accountId: Int,
        startDate: Long,
        endDate: Long,
        limit: Int,
        offset: Int
    ): List<Transaction>

    @Query(GET_ACCOUNT_TRANSACTIONS_BETWEEN_DATES)
    suspend fun getTransactionBetweenDatesForAccount(
        accountId: Int,
        startDate: Long,
        endDate: Long,
        limit: Int,
        offset: Int
    ): List<Transaction>

    @Query(GET_ALL_TRANSACTIONS)
    suspend fun getAllTransactions(limit: Int, offset: Int): List<Transaction>

    @Query(GET_ALL_DEBIT_TRANSACTIONS)
    suspend fun getDebitTransactions(limit: Int, offset: Int): List<Transaction>

    @Query(GET_ALL_CREDIT_TRANSACTIONS)
    suspend fun getCreditTransactions(limit: Int, offset: Int): List<Transaction>

    @Query(GET_CREDIT_TRANSACTIONS_BETWEEN_DATES)
    suspend fun getCreditTransactionBetweenDates(startDate: Long, endDate: Long, limit: Int, offset: Int): List<Transaction>

    @Query(GET_DEBIT_TRANSACTIONS_BETWEEN_DATES)
    suspend fun getDebitTransactionBetweenDates(startDate: Long, endDate: Long, limit: Int, offset: Int): List<Transaction>

    @Query(GET_TRANSACTIONS_BETWEEN_DATES)
    suspend fun getTransactionBetweenDates(startDate: Long, endDate: Long, limit: Int, offset: Int): List<Transaction>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTransaction(transaction: Transaction)

    @Query(GET_TRANSACTION_BY_ID)
    suspend fun getTransactionById(transactionId: Int): Transaction
}