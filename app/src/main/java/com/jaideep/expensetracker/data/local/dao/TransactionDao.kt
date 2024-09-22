package com.jaideep.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jaideep.expensetracker.common.constant.sql.TransactionSql.DELETE_TRANSACTION_BY_ID
import com.jaideep.expensetracker.common.constant.sql.TransactionSql.GET_ACCOUNT_CREDIT_TRANSACTIONS
import com.jaideep.expensetracker.common.constant.sql.TransactionSql.GET_ACCOUNT_CREDIT_TRANSACTIONS_BETWEEN_DATES
import com.jaideep.expensetracker.common.constant.sql.TransactionSql.GET_ACCOUNT_DEBIT_TRANSACTIONS
import com.jaideep.expensetracker.common.constant.sql.TransactionSql.GET_ACCOUNT_DEBIT_TRANSACTIONS_BETWEEN_DATES
import com.jaideep.expensetracker.common.constant.sql.TransactionSql.GET_ACCOUNT_TRANSACTIONS
import com.jaideep.expensetracker.common.constant.sql.TransactionSql.GET_ACCOUNT_TRANSACTIONS_BETWEEN_DATES
import com.jaideep.expensetracker.common.constant.sql.TransactionSql.GET_ALL_CREDIT_TRANSACTIONS
import com.jaideep.expensetracker.common.constant.sql.TransactionSql.GET_ALL_DEBIT_TRANSACTIONS
import com.jaideep.expensetracker.common.constant.sql.TransactionSql.GET_ALL_TRANSACTIONS
import com.jaideep.expensetracker.common.constant.sql.TransactionSql.GET_CREDIT_TRANSACTIONS_BETWEEN_DATES
import com.jaideep.expensetracker.common.constant.sql.TransactionSql.GET_DEBIT_TRANSACTIONS_BETWEEN_DATES
import com.jaideep.expensetracker.common.constant.sql.TransactionSql.GET_TRANSACTIONS_BETWEEN_DATES
import com.jaideep.expensetracker.common.constant.sql.TransactionSql.GET_TRANSACTION_BY_ID
import com.jaideep.expensetracker.data.local.entities.Transaction
import com.jaideep.expensetracker.model.dto.TransactionDto
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query(GET_ACCOUNT_TRANSACTIONS)
    fun getTransactionsForAccount(accountName: String, limit: Int): Flow<List<TransactionDto>>

    @Query(GET_ACCOUNT_DEBIT_TRANSACTIONS)
    fun getDebitTransactionsForAccount(accountName: String, limit: Int): Flow<List<TransactionDto>>

    @Query(GET_ACCOUNT_CREDIT_TRANSACTIONS)
    fun getCreditTransactionsForAccount(accountName: String, limit: Int): Flow<List<TransactionDto>>

    @Query(GET_ACCOUNT_CREDIT_TRANSACTIONS_BETWEEN_DATES)
    fun getCreditTransactionBetweenDatesForAccount(
        accountName: String, startDate: Long, endDate: Long, limit: Int
    ): Flow<List<TransactionDto>>

    @Query(GET_ACCOUNT_DEBIT_TRANSACTIONS_BETWEEN_DATES)
    fun getDebitTransactionBetweenDatesForAccount(
        accountName: String, startDate: Long, endDate: Long, limit: Int
    ): Flow<List<TransactionDto>>

    @Query(GET_ACCOUNT_TRANSACTIONS_BETWEEN_DATES)
    fun getTransactionBetweenDatesForAccount(
        accountName: String, startDate: Long, endDate: Long, limit: Int
    ): Flow<List<TransactionDto>>

    @Query(GET_ALL_TRANSACTIONS)
    fun getAllTransactions(limit: Int): Flow<List<TransactionDto>>

    @Query(GET_ALL_DEBIT_TRANSACTIONS)
    fun getDebitTransactions(limit: Int): Flow<List<TransactionDto>>

    @Query(GET_ALL_CREDIT_TRANSACTIONS)
    fun getCreditTransactions(limit: Int): Flow<List<TransactionDto>>

    @Query(GET_CREDIT_TRANSACTIONS_BETWEEN_DATES)
    fun getCreditTransactionBetweenDates(
        startDate: Long, endDate: Long, limit: Int
    ): Flow<List<TransactionDto>>

    @Query(GET_DEBIT_TRANSACTIONS_BETWEEN_DATES)
    fun getDebitTransactionBetweenDates(
        startDate: Long,
        endDate: Long,
        limit: Int,
    ): Flow<List<TransactionDto>>

    @Query(GET_TRANSACTIONS_BETWEEN_DATES)
    fun getTransactionBetweenDates(
        startDate: Long, endDate: Long, limit: Int
    ): Flow<List<TransactionDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTransaction(transaction: Transaction)

    @Query(GET_TRANSACTION_BY_ID)
    suspend fun getTransactionById(transactionId: Int): TransactionDto

    @Query(DELETE_TRANSACTION_BY_ID)
    fun deleteTransactionById(transactionId: Int)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTransaction(transaction: Transaction)
}