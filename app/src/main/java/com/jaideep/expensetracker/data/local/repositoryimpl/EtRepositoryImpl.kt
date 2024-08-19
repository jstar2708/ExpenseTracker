package com.jaideep.expensetracker.data.local.repositoryimpl

import com.jaideep.expensetracker.data.local.dao.EtDao
import com.jaideep.expensetracker.domain.repository.EtRepository
import com.jaideep.expensetracker.presentation.utility.Utility
import javax.inject.Inject

class EtRepositoryImpl @Inject constructor(
    private val etDao: EtDao
) : EtRepository {
    override suspend fun getAmountSpentTodayForAccount(accountName: String): Double {
        return etDao.getAmountSpentFromAccountToday(Utility.getCurrentDateInMillis(), accountName)
    }

    override suspend fun getAccountBalanceByName(accountName: String): Double {
        return etDao.getAccountBalanceByName(accountName)
    }

}