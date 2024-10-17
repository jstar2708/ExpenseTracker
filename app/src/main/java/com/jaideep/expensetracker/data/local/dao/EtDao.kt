package com.jaideep.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_ACCOUNT_BALANCE_BY_NAME
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_ACCOUNT_BALANCE_FOR_ALL_ACCOUNTS
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_ALL_CATEGORY_WISE_TRANSACTIONS_WITH_DATE
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_AMOUNT_SPENT_FROM_ACCOUNT_THIS_MONTH
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_AMOUNT_SPENT_FROM_ACCOUNT_TODAY
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_AMOUNT_SPENT_FROM_ALL_ACCOUNTS_THIS_MONTH
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_AMOUNT_SPENT_FROM_ALL_ACCOUNT_TODAY
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_LAST_TRANSACTION_DATE
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_CATEGORY_CARDS_DATA_FROM_ACCOUNT_WITHIN_DURATION
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_CATEGORY_CARDS_DATA_FROM_ALL_ACCOUNTS_WITHIN_DURATION
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_CATEGORY_ON_WHICH_MAX_AMOUNT_SPENT_FROM_ACCOUNT_THIS_MONTH
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_CATEGORY_ON_WHICH_MAX_AMOUNT_SPENT_FROM_ALL_ACCOUNTS_THIS_MONTH
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_CATEGORY_WISE_ACCOUNT_TRANSACTIONS_WITH_DATE
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_FIRST_TRANSACTION_DATE
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_MOST_FREQUENTLY_USED_ACC
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_TOTAL_EXPENDITURE
import com.jaideep.expensetracker.common.constant.sql.EtSql.GET_TOTAL_TRANSACTIONS_COUNT
import com.jaideep.expensetracker.model.CategoryCardData
import com.jaideep.expensetracker.model.dto.TransactionDto
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface EtDao {

    @Query(GET_AMOUNT_SPENT_FROM_ACCOUNT_TODAY)
    fun getAmountSpentFromAccountToday(date: Long, accountName: String): Flow<Double>

    @Query(GET_ACCOUNT_BALANCE_BY_NAME)
    fun getAccountBalanceByName(accountName: String): Flow<Double>

    @Query(GET_AMOUNT_SPENT_FROM_ALL_ACCOUNT_TODAY)
    fun getAmountSpentFromAllAccountsToday(date: Long): Flow<Double>

    @Query(GET_ACCOUNT_BALANCE_FOR_ALL_ACCOUNTS)
    fun getAccountBalanceForAllAccounts(): Flow<Double>

    @Query(GET_CATEGORY_ON_WHICH_MAX_AMOUNT_SPENT_FROM_ACCOUNT_THIS_MONTH)
    fun getCategoryOnWhichMaximumAmountSpentFromAccountThisMonth(
        accountName: String,
        date: Long
    ): Flow<CategoryCardData>

    @Query(GET_AMOUNT_SPENT_FROM_ACCOUNT_THIS_MONTH)
    fun getTotalAmountSpentFromAccountFromDuration(accountName: String, date: Long): Flow<Double>

    @Query(GET_CATEGORY_ON_WHICH_MAX_AMOUNT_SPENT_FROM_ALL_ACCOUNTS_THIS_MONTH)
    fun getCategoryOnWhichMaximumAmountSpentFromAllAccountsThisMonth(date: Long): Flow<CategoryCardData>

    @Query(GET_AMOUNT_SPENT_FROM_ALL_ACCOUNTS_THIS_MONTH)
    fun getTotalAmountSpentFromAllAccountsFromDuration(date: Long): Flow<Double>

    @Query(GET_CATEGORY_CARDS_DATA_FROM_ALL_ACCOUNTS_WITHIN_DURATION)
    fun getCategoryCardsDataFromAllAccountsWithinDuration(date: Long) : Flow<List<CategoryCardData>>

    @Query(GET_CATEGORY_CARDS_DATA_FROM_ACCOUNT_WITHIN_DURATION)
    fun getCategoryCardsDataFromAccountWithinDuration(accountName: String, date: Long) : Flow<List<CategoryCardData>>

    @Query(GET_ALL_CATEGORY_WISE_TRANSACTIONS_WITH_DATE)
    fun getCategoryWiseAllAccountTransactionsWithDate(categoryName: String, fromDate: Long, toDate: Long) : Flow<List<TransactionDto>>

    @Query(GET_CATEGORY_WISE_ACCOUNT_TRANSACTIONS_WITH_DATE)
    fun getCategoryWiseAccountTransactionWithDate(categoryName: String, accountName: String, fromDate: Long, toDate: Long) : Flow<List<TransactionDto>>
    @Query(GET_TOTAL_EXPENDITURE)
    fun getTotalExpenditure(): Double
    @Query(GET_TOTAL_TRANSACTIONS_COUNT)
    fun getTotalTransactions(): Int
    @Query(GET_LAST_TRANSACTION_DATE)
    fun getLastTransactionDate(): LocalDate
    @Query(GET_MOST_FREQUENTLY_USED_ACC)
    fun getMostFrequentlyUsedAccount(): String
    @Query(GET_FIRST_TRANSACTION_DATE)
    fun getFirstTransactionDate(): LocalDate
}