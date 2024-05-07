package com.jaideep.expensetracker.data.repositoryimpl

import com.jaideep.expensetracker.data.dao.AccountDao
import com.jaideep.expensetracker.data.entities.Account
import com.jaideep.expensetracker.domain.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao
) : AccountRepository {
    override suspend fun getAccounts(): List<Account> {
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
}