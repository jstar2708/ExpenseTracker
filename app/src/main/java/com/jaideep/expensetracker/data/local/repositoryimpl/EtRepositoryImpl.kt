package com.jaideep.expensetracker.data.local.repositoryimpl

import com.jaideep.expensetracker.data.local.dao.EtDao
import com.jaideep.expensetracker.domain.repository.EtRepository
import com.jaideep.expensetracker.model.CategoryCardData
import com.jaideep.expensetracker.presentation.utility.Utility
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EtRepositoryImpl @Inject constructor(
    private val etDao: EtDao
) : EtRepository {
    override fun getAmountSpentTodayForAccount(accountName: String): Flow<Double> {
        return etDao.getAmountSpentFromAccountToday(Utility.getCurrentDateInMillis(), accountName)
    }

    override fun getAccountBalanceByName(accountName: String): Flow<Double> {
        return etDao.getAccountBalanceByName(accountName)
    }

    override fun getAmountSpentTodayForAllAccount(): Flow<Double> {
        return etDao.getAmountSpentFromAllAccountsToday(Utility.getCurrentDateInMillis())
    }

    override fun getAccountBalanceForAllAccounts(): Flow<Double> {
        return etDao.getAccountBalanceForAllAccounts()
    }

    override fun getCategoryOnWhichMaximumAmountSpentFromAccountThisMonth(
        accountName: String,
        date: Long
    ): Flow<CategoryCardData> {
        return etDao.getCategoryOnWhichMaximumAmountSpentFromAccountThisMonth(accountName, date)
    }

    override fun getTotalAmountSpentFromAccountThisMonth(
        accountName: String,
        date: Long
    ): Flow<Double> {
        return etDao.getTotalAmountSpentFromAccountThisMonth(accountName, date)
    }

    override fun getCategoryOnWhichMaximumAmountSpentFromAllAccountsThisMonth(date: Long): Flow<CategoryCardData> {
        return etDao.getCategoryOnWhichMaximumAmountSpentFromAllAccountsThisMonth(date)
    }

    override fun getAmountSpentFromAllAccountThisMonth(date: Long): Flow<Double> {
        return etDao.getTotalAmountSpentFromAllAccountsThisMonth(date)
    }

}