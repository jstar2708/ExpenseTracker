package com.jaideep.expensetracker.domain.repository

import com.jaideep.expensetracker.data.entities.Account
import com.jaideep.expensetracker.data.entities.Category
import com.jaideep.expensetracker.data.entities.Transaction

interface EtRepository {

    suspend fun getAllAccounts() : List<Account>

    suspend fun getAllCategories() : List<Category>

    suspend fun getAllTransactions() : List<Transaction>

    suspend fun getAccount(accountId: Int) : Account

    suspend fun getTransaction(transactionId: Int) : Transaction

    suspend fun getCategory(categoryId: Int) : Category
    suspend fun saveAccount(account: Account)
    suspend fun saveCategory(category: Category)
    suspend fun saveTransaction(transaction: Transaction)

}