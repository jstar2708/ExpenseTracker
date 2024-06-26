package com.jaideep.expensetracker.di

import android.app.Application
import com.jaideep.expensetracker.data.local.dao.AccountDao
import com.jaideep.expensetracker.data.local.dao.CategoryDao
import com.jaideep.expensetracker.data.local.dao.EtDao
import com.jaideep.expensetracker.data.local.dao.TransactionDao
import com.jaideep.expensetracker.data.local.database.EtDatabase
import com.jaideep.expensetracker.data.local.repositoryimpl.AccountRepositoryImpl
import com.jaideep.expensetracker.data.local.repositoryimpl.CategoryRepositoryImpl
import com.jaideep.expensetracker.data.local.repositoryimpl.EtRepositoryImpl
import com.jaideep.expensetracker.data.local.repositoryimpl.TransactionRepositoryImpl
import com.jaideep.expensetracker.domain.repository.AccountRepository
import com.jaideep.expensetracker.domain.repository.CategoryRepository
import com.jaideep.expensetracker.domain.repository.EtRepository
import com.jaideep.expensetracker.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(app: Application) : EtDatabase {
        return EtDatabase.getDatabase(app)
    }

    @Provides
    @Singleton
    fun providesDao(etDatabase: EtDatabase): EtDao {
        return etDatabase.getEtDao();
    }
    @Provides
    @Singleton
    fun providesAccountDao(etDatabase: EtDatabase): AccountDao {
        return etDatabase.getAccountDao();
    }

    @Provides
    @Singleton
    fun providesTransactionDao(etDatabase: EtDatabase): TransactionDao {
        return etDatabase.getTransactionDao();
    }
    @Provides
    @Singleton
    fun providesCategoryDao(etDatabase: EtDatabase): CategoryDao {
        return etDatabase.getCategoryDao();
    }

    @Provides
    @Singleton
    fun providesAccountRepository(accountDao: AccountDao) : AccountRepository {
        return AccountRepositoryImpl(accountDao)
    }

    @Provides
    @Singleton
    fun providesEtRepository(etDao: EtDao, accountDao: AccountDao, transactionDao: TransactionDao, categoryDao: CategoryDao) : EtRepository {
        return EtRepositoryImpl(etDao, transactionDao, accountDao, categoryDao)
    }@Provides
    @Singleton
    fun providesCategoryRepository(categoryDao: CategoryDao) : CategoryRepository {
        return CategoryRepositoryImpl(categoryDao)
    }@Provides
    @Singleton
    fun providesTransactionRepository(transactionDao: TransactionDao) : TransactionRepository {
        return TransactionRepositoryImpl(transactionDao)
    }
}