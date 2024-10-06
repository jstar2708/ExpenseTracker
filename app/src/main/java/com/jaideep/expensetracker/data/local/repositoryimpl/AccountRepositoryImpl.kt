package com.jaideep.expensetracker.data.local.repositoryimpl

import com.jaideep.expensetracker.data.local.dao.AccountDao
import com.jaideep.expensetracker.data.local.entities.Account
import com.jaideep.expensetracker.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao
) : AccountRepository {
    override fun getAccounts(): Flow<List<Account>> {
        return accountDao.getAccounts()
    }

    override suspend fun saveAccount(account: Account) {
        return accountDao.saveAccount(account)
    }

    override suspend fun getAccountById(accountId: Int): Account {
        return accountDao.getAccountById(accountId)
    }

    override suspend fun updateAccount(account: Account) {
        accountDao.updateAccount(account)
    }

    override suspend fun getTotalAccountsCount(): Int {
        return accountDao.getTotalAccountsCount()
    }
}