package com.jaideep.expensetracker.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jaideep.expensetracker.common.constant.sql.TransactionPagingSql.GET_ACCOUNT_CREDIT_TRANSACTIONS
import com.jaideep.expensetracker.common.constant.sql.TransactionPagingSql.GET_ACCOUNT_CREDIT_TRANSACTIONS_BETWEEN_DATES
import com.jaideep.expensetracker.common.constant.sql.TransactionPagingSql.GET_ACCOUNT_DEBIT_TRANSACTIONS
import com.jaideep.expensetracker.common.constant.sql.TransactionPagingSql.GET_ACCOUNT_DEBIT_TRANSACTIONS_BETWEEN_DATES
import com.jaideep.expensetracker.common.constant.sql.TransactionPagingSql.GET_ACCOUNT_TRANSACTIONS
import com.jaideep.expensetracker.common.constant.sql.TransactionPagingSql.GET_ACCOUNT_TRANSACTIONS_BETWEEN_DATES
import com.jaideep.expensetracker.common.constant.sql.TransactionPagingSql.GET_ALL_CREDIT_TRANSACTIONS
import com.jaideep.expensetracker.common.constant.sql.TransactionPagingSql.GET_ALL_DEBIT_TRANSACTIONS
import com.jaideep.expensetracker.common.constant.sql.TransactionPagingSql.GET_ALL_TRANSACTIONS
import com.jaideep.expensetracker.common.constant.sql.TransactionPagingSql.GET_CREDIT_TRANSACTIONS_BETWEEN_DATES
import com.jaideep.expensetracker.common.constant.sql.TransactionPagingSql.GET_DEBIT_TRANSACTIONS_BETWEEN_DATES
import com.jaideep.expensetracker.common.constant.sql.TransactionPagingSql.GET_TRANSACTIONS_BETWEEN_DATES
import com.jaideep.expensetracker.common.constant.sql.TransactionPagingSql.GET_TRANSACTION_BY_ID
import com.jaideep.expensetracker.data.local.entities.Transaction

@Dao
interface TransactionPagingDao {
    @Query(GET_ACCOUNT_TRANSACTIONS)
    fun getTransactionsForAccount(accountId: Int): PagingSource<Int, Transaction>

    @Query(GET_ACCOUNT_DEBIT_TRANSACTIONS)
    fun getDebitTransactionsForAccount(accountId: Int): PagingSource<Int, Transaction>

    @Query(GET_ACCOUNT_CREDIT_TRANSACTIONS)
    fun getCreditTransactionsForAccount(accountId: Int): PagingSource<Int, Transaction>

    @Query(GET_ACCOUNT_CREDIT_TRANSACTIONS_BETWEEN_DATES)
    fun getCreditTransactionBetweenDatesForAccount(
        accountId: Int, startDate: Long, endDate: Long
    ): PagingSource<Int, Transaction>

    @Query(GET_ACCOUNT_DEBIT_TRANSACTIONS_BETWEEN_DATES)
    fun getDebitTransactionBetweenDatesForAccount(
        accountId: Int, startDate: Long, endDate: Long
    ): PagingSource<Int, Transaction>

    @Query(GET_ACCOUNT_TRANSACTIONS_BETWEEN_DATES)
    fun getTransactionBetweenDatesForAccount(
        accountId: Int, startDate: Long, endDate: Long
    ): PagingSource<Int, Transaction>

    @Query(GET_ALL_TRANSACTIONS)
    fun getAllTransactions(): PagingSource<Int, Transaction>

    @Query(GET_ALL_DEBIT_TRANSACTIONS)
    fun getDebitTransactions(): PagingSource<Int, Transaction>

    @Query(GET_ALL_CREDIT_TRANSACTIONS)
    fun getCreditTransactions(): PagingSource<Int, Transaction>

    @Query(GET_CREDIT_TRANSACTIONS_BETWEEN_DATES)
    fun getCreditTransactionBetweenDates(
        startDate: Long,
        endDate: Long
    ): PagingSource<Int, Transaction>

    @Query(GET_DEBIT_TRANSACTIONS_BETWEEN_DATES)
    fun getDebitTransactionBetweenDates(
        startDate: Long,
        endDate: Long,
    ): PagingSource<Int, Transaction>

    @Query(GET_TRANSACTIONS_BETWEEN_DATES)
    fun getTransactionBetweenDates(startDate: Long, endDate: Long): PagingSource<Int, Transaction>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTransaction(transaction: Transaction)

    @Query(GET_TRANSACTION_BY_ID)
    suspend fun getTransactionById(transactionId: Int): Transaction
}