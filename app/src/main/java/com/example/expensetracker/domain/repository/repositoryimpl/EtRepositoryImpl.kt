package com.example.expensetracker.domain.repository.repositoryimpl

import com.example.expensetracker.data.dao.EtDao
import com.example.expensetracker.data.entities.Account
import com.example.expensetracker.data.entities.Category
import com.example.expensetracker.data.entities.Transaction
import com.example.expensetracker.domain.repository.EtRepository
import javax.inject.Inject

class EtRepositoryImpl @Inject constructor(
    private val dao: EtDao
) : EtRepository {

    private lateinit var transactionList: List<Transaction>
    private lateinit var accountList: List<Account>
    private lateinit var categoryList: List<Category>

    override suspend fun getAllAccounts(): List<Account> {
        return dao.getAccounts()
    }

    override suspend fun getAllCategories(): List<Category> {
        return listOf()
    }

    override suspend fun getAllTransactions(): List<Transaction> {
        return dao.getTransactions()
    }

    override suspend fun getAccount(accountId: Int): Account {
        return dao.getAccountById(accountId);
    }

    override suspend fun getTransaction(transactionId: Int): Transaction {
        return dao.getTransactionById(transactionId)
    }

    override suspend fun getCategory(categoryId: Int): Category {
        return dao.getCategoryById(categoryId);
    }
}