package com.example.expensetracker.domain.repository

import com.example.expensetracker.data.entities.Account
import com.example.expensetracker.data.entities.Category
import com.example.expensetracker.data.entities.Transaction

interface EtRepository {

    suspend fun getAllAccounts() : List<Account>

    suspend fun getAllCategories() : List<Category>

    suspend fun getAllTransactions() : List<Transaction>

    suspend fun getAccount(accountId: Int) : Account

    suspend fun getTransaction(transactionId: Int) : Transaction

    suspend fun getCategory(categoryId: Int) : Category

}