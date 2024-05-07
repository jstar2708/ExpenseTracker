package com.jaideep.expensetracker.domain.repository

import com.jaideep.expensetracker.data.entities.Account

interface AccountRepository {
    suspend fun getAccounts() : List<Account>
    suspend fun saveAccount(account: Account)
    suspend fun getAccountById(accountId: Int) : Account
    suspend fun updateAccount(account: Account)
}