package com.jaideep.expensetracker.data.local.repositoryimpl

import com.jaideep.expensetracker.data.local.dao.AccountDao
import com.jaideep.expensetracker.data.local.dao.CategoryDao
import com.jaideep.expensetracker.data.local.dao.EtDao
import com.jaideep.expensetracker.data.local.dao.TransactionPagingDao
import com.jaideep.expensetracker.data.local.entities.Account
import com.jaideep.expensetracker.data.local.entities.Category
import com.jaideep.expensetracker.data.local.entities.Transaction
import com.jaideep.expensetracker.domain.repository.EtRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EtRepositoryImpl @Inject constructor(
    private val dao: EtDao,
    private val transactionPagingDao: TransactionPagingDao,
    private val accountDao: AccountDao,
    private val categoryDao: CategoryDao
) : EtRepository {

    private lateinit var transactionList: List<Transaction>
    private lateinit var accountList: List<Account>
    private lateinit var categoryList: List<Category>

    override fun getAllAccounts(): Flow<List<Account>> {
        return accountDao.getAccounts()
    }

    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories()
    }

    override suspend fun getAccount(accountId: Int): Account {
        return accountDao.getAccountById(accountId)
    }

    override suspend fun getTransaction(transactionId: Int): Transaction {
        return transactionPagingDao.getTransactionById(transactionId)
    }

    override suspend fun getCategory(categoryId: Int): Category {
        return categoryDao.getCategoryById(categoryId)
    }

    override suspend fun saveAccount(account: Account) {

    }

    override suspend fun saveCategory(category: Category) {

    }

    override suspend fun saveTransaction(transaction: Transaction) {

    }
}