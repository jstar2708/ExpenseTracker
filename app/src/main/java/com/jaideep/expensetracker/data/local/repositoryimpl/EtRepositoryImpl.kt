package com.jaideep.expensetracker.data.local.repositoryimpl

import com.jaideep.expensetracker.data.local.dao.EtDao
import com.jaideep.expensetracker.domain.repository.EtRepository
import com.jaideep.expensetracker.model.CategoryCardData
import com.jaideep.expensetracker.model.dto.TransactionDto
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class EtRepositoryImpl @Inject constructor(
    private val etDao: EtDao
) : EtRepository {
    override fun getAmountSpentTodayForAccount(accountName: String, date: Long): Flow<Double> {
        return etDao.getAmountSpentFromAccountToday(date, accountName)
    }

    override fun getAccountBalanceByName(accountName: String): Flow<Double> {
        return etDao.getAccountBalanceByName(accountName)
    }

    override fun getAmountSpentTodayForAllAccount(date: Long): Flow<Double> {
        return etDao.getAmountSpentFromAllAccountsToday(date)
    }

    override fun getAccountBalanceForAllAccounts(): Flow<Double> {
        return etDao.getAccountBalanceForAllAccounts()
    }

    override fun getCategoryOnWhichMaximumAmountSpentFromAccountThisMonth(
        accountName: String, date: Long
    ): Flow<CategoryCardData> {
        return etDao.getCategoryOnWhichMaximumAmountSpentFromAccountThisMonth(accountName, date)
    }

    override fun getTotalAmountSpentFromAccountThisMonth(
        accountName: String, date: Long
    ): Flow<Double> {
        return etDao.getTotalAmountSpentFromAccountFromDuration(accountName, date)
    }

    override fun getCategoryOnWhichMaximumAmountSpentFromAllAccountsThisMonth(date: Long): Flow<CategoryCardData> {
        return etDao.getCategoryOnWhichMaximumAmountSpentFromAllAccountsThisMonth(date)
    }

    override fun getAmountSpentFromAllAccountThisMonth(date: Long): Flow<Double> {
        return etDao.getTotalAmountSpentFromAllAccountsFromDuration(date)
    }

    override fun getCategoryCardsDataFromAllAccountsWithinDuration(date: Long): Flow<List<CategoryCardData>> {
        return etDao.getCategoryCardsDataFromAllAccountsWithinDuration(date)
    }

    override fun getCategoryCardsDataFromAccountWithinDuration(
        accountName: String, date: Long
    ): Flow<List<CategoryCardData>> {
        return etDao.getCategoryCardsDataFromAccountWithinDuration(accountName, date)
    }

    override fun getCategoryWiseAllAccountTransactionsWithDate(
        categoryName: String, fromDate: Long, toDate: Long
    ): Flow<List<TransactionDto>> {
        return etDao.getCategoryWiseAllAccountTransactionsWithDate(categoryName, fromDate, toDate)
    }

    override fun getCategoryWiseAccountTransactionWithDate(
        categoryName: String, accountName: String, fromDate: Long, toDate: Long
    ): Flow<List<TransactionDto>> {
        return etDao.getCategoryWiseAccountTransactionWithDate(
            categoryName, accountName, fromDate, toDate
        )
    }

    override fun getTotalExpenditure(): Double {
        return etDao.getTotalExpenditure()
    }

    override fun getTotalTransactions(): Int {
        return etDao.getTotalTransactions()
    }

    override fun getLastTransactionDate(): LocalDate? {
        return etDao.getLastTransactionDate()
    }

    override fun getFirstTransactionDate(): LocalDate? {
        return etDao.getFirstTransactionDate()
    }

    override fun getMostFrequentlyUsedAccount(): String {
        return etDao.getMostFrequentlyUsedAccount()
    }

    override fun getAmountSpentFromAllAccountThisYear(startOfYear: Long): Flow<Double> {
        return etDao.getTotalAmountSpentFromAllAccountsFromDuration(startOfYear)
    }

    override fun getTotalAmountSpentFromAccountThisYear(
        accountName: String, startDateOfYearInMillis: Long
    ): Flow<Double> {
        return etDao.getTotalAmountSpentFromAccountFromDuration(accountName, startDateOfYearInMillis)
    }

}