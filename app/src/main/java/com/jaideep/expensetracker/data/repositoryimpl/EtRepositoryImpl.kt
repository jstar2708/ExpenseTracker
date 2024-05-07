package com.jaideep.expensetracker.data.repositoryimpl

import com.jaideep.expensetracker.data.dao.AccountDao
import com.jaideep.expensetracker.data.dao.CategoryDao
import com.jaideep.expensetracker.data.dao.EtDao
import com.jaideep.expensetracker.data.dao.TransactionDao
import com.jaideep.expensetracker.data.entities.Account
import com.jaideep.expensetracker.data.entities.Category
import com.jaideep.expensetracker.data.entities.Transaction
import com.jaideep.expensetracker.domain.repository.EtRepository
import javax.inject.Inject

class EtRepositoryImpl @Inject constructor(
    private val dao: EtDao,
    private val transactionDao: TransactionDao,
    private val accountDao: AccountDao,
    private val categoryDao: CategoryDao
) : EtRepository {

    private lateinit var transactionList: List<Transaction>
    private lateinit var accountList: List<Account>
    private lateinit var categoryList: List<Category>

    override suspend fun getAllAccounts(): List<Account> {
        return accountDao.getAccounts()
    }

    override suspend fun getAllCategories(): List<Category> {
        return categoryDao.getAllCategories()
    }

    override suspend fun getAllTransactions(): List<Transaction> {
        return transactionDao.getAllTransactions()
    }

    override suspend fun getAccount(accountId: Int): Account {
        return accountDao.getAccountById(accountId);
    }

    override suspend fun getTransaction(transactionId: Int): Transaction {
        return transactionDao.getTransactionById(transactionId)
    }

    override suspend fun getCategory(categoryId: Int): Category {
        return categoryDao.getCategoryById(categoryId);
    }

    override suspend fun saveAccount(account: Account) {
        TODO("Not yet implemented")
    }

    override suspend fun saveCategory(category: Category) {
        TODO("Not yet implemented")
    }

    override suspend fun saveTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }
}