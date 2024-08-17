package com.jaideep.expensetracker.domain.repository

import com.jaideep.expensetracker.data.local.entities.Account
import com.jaideep.expensetracker.data.local.entities.Category
import com.jaideep.expensetracker.data.local.entities.Transaction
import kotlinx.coroutines.flow.Flow

interface EtRepository {

    fun getAllAccounts(): Flow<List<Account>>

    fun getAllCategories(): Flow<List<Category>>

    suspend fun getAccount(accountId: Int): Account

    suspend fun getTransaction(transactionId: Int): Transaction

    suspend fun getCategory(categoryId: Int): Category
    suspend fun saveAccount(account: Account)
    suspend fun saveCategory(category: Category)
    suspend fun saveTransaction(transaction: Transaction)

}