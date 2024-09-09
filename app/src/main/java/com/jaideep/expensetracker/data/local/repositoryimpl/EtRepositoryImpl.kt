package com.jaideep.expensetracker.data.local.repositoryimpl

import com.jaideep.expensetracker.data.local.dao.EtDao
import com.jaideep.expensetracker.domain.repository.EtRepository
import com.jaideep.expensetracker.model.CategoryCardData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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
        return etDao.getTotalAmountSpentFromAccountThisMonth(accountName, date)
    }

    override fun getCategoryOnWhichMaximumAmountSpentFromAllAccountsThisMonth(date: Long): Flow<CategoryCardData> {
        return etDao.getCategoryOnWhichMaximumAmountSpentFromAllAccountsThisMonth(date)
    }

    override fun getAmountSpentFromAllAccountThisMonth(date: Long): Flow<Double> {
        return etDao.getTotalAmountSpentFromAllAccountsThisMonth(date)
    }

    override fun getCategoryCardsDataFromAllAccountsWithinDuration(date: Long): Flow<List<CategoryCardData>> {
        return etDao.getCategoryCardsDataFromAllAccountsWithinDuration(date)
    }

    override fun getCategoryCardsDataFromAccountWithinDuration(
        accountName: String,
        date: Long
    ): Flow<List<CategoryCardData>> {
        return etDao.getCategoryCardsDataFromAccountWithinDuration(accountName, date)
    }

}