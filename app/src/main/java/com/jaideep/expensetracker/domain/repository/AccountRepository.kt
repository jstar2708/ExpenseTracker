package com.jaideep.expensetracker.domain.repository

import com.jaideep.expensetracker.data.local.entities.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getAccounts(): Flow<List<Account>>
    suspend fun saveAccount(account: Account)
    suspend fun getAccountById(accountId: Int): Account
    suspend fun updateAccount(account: Account)
    suspend fun getTotalAccountsCount(): Int
}