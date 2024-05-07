package com.jaideep.expensetracker.di

import android.app.Application
import com.jaideep.expensetracker.data.dao.AccountDao
import com.jaideep.expensetracker.data.dao.CategoryDao
import com.jaideep.expensetracker.data.dao.EtDao
import com.jaideep.expensetracker.data.dao.TransactionDao
import com.jaideep.expensetracker.data.database.EtDatabase
import com.jaideep.expensetracker.data.repositoryimpl.EtRepositoryImpl
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
    fun providesEtRepositoryImpl(etDao: EtDao, accountDao: AccountDao, transactionDao: TransactionDao, categoryDao: CategoryDao) : EtRepositoryImpl {
        return EtRepositoryImpl(etDao, transactionDao, accountDao, categoryDao)
    }
}