package com.jaideep.expensetracker.di

import android.app.Application
import com.jaideep.expensetracker.data.local.dao.AccountDao
import com.jaideep.expensetracker.data.local.dao.CategoryDao
import com.jaideep.expensetracker.data.local.dao.EtDao
import com.jaideep.expensetracker.data.local.dao.TransactionPagingDao
import com.jaideep.expensetracker.data.local.database.EtDatabase
import com.jaideep.expensetracker.data.local.repositoryimpl.AccountRepositoryImpl
import com.jaideep.expensetracker.data.local.repositoryimpl.CategoryRepositoryImpl
import com.jaideep.expensetracker.data.local.repositoryimpl.EtRepositoryImpl
import com.jaideep.expensetracker.data.local.repositoryimpl.TransactionPagingRepositoryImpl
import com.jaideep.expensetracker.domain.repository.AccountRepository
import com.jaideep.expensetracker.domain.repository.CategoryRepository
import com.jaideep.expensetracker.domain.repository.EtRepository
import com.jaideep.expensetracker.domain.repository.TransactionPagingRepository
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
    fun providesTransactionDao(etDatabase: EtDatabase): TransactionPagingDao {
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
    fun providesEtRepository(etDao: EtDao, accountDao: AccountDao, transactionPagingDao: TransactionPagingDao, categoryDao: CategoryDao) : EtRepository {
        return EtRepositoryImpl(etDao, transactionPagingDao, accountDao, categoryDao)
    }@Provides
    @Singleton
    fun providesCategoryRepository(categoryDao: CategoryDao) : CategoryRepository {
        return CategoryRepositoryImpl(categoryDao)
    }@Provides
    @Singleton
    fun providesTransactionRepository(transactionPagingDao: TransactionPagingDao) : TransactionPagingRepository {
        return TransactionPagingRepositoryImpl(transactionPagingDao)
    }
}