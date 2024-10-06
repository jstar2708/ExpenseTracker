package com.jaideep.expensetracker.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.jaideep.expensetracker.common.EtDispatcher
import com.jaideep.expensetracker.common.constant.AppConstants.ET_PREFERENCES
import com.jaideep.expensetracker.data.local.dao.AccountDao
import com.jaideep.expensetracker.data.local.dao.CategoryDao
import com.jaideep.expensetracker.data.local.dao.CrudDao
import com.jaideep.expensetracker.data.local.dao.DatabaseDao
import com.jaideep.expensetracker.data.local.dao.EtDao
import com.jaideep.expensetracker.data.local.dao.TransactionDao
import com.jaideep.expensetracker.data.local.dao.TransactionPagingDao
import com.jaideep.expensetracker.data.local.database.EtDatabase
import com.jaideep.expensetracker.data.local.preferences.DatastoreRepository
import com.jaideep.expensetracker.data.local.preferences.DatastoreRepositoryImpl
import com.jaideep.expensetracker.data.local.repositoryimpl.AccountRepositoryImpl
import com.jaideep.expensetracker.data.local.repositoryimpl.CategoryRepositoryImpl
import com.jaideep.expensetracker.data.local.repositoryimpl.CrudRepositoryImpl
import com.jaideep.expensetracker.data.local.repositoryimpl.EtRepositoryImpl
import com.jaideep.expensetracker.data.local.repositoryimpl.TransactionPagingRepositoryImpl
import com.jaideep.expensetracker.data.local.repositoryimpl.TransactionRepositoryImpl
import com.jaideep.expensetracker.domain.repository.AccountRepository
import com.jaideep.expensetracker.domain.repository.CategoryRepository
import com.jaideep.expensetracker.domain.repository.CrudRepository
import com.jaideep.expensetracker.domain.repository.EtRepository
import com.jaideep.expensetracker.domain.repository.TransactionPagingRepository
import com.jaideep.expensetracker.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(app: Application): EtDatabase {
        return EtDatabase.getDatabase(app)
    }

    @Provides
    @Singleton
    fun providesDatastore(@ApplicationContext appContext: Context) : DataStore<Preferences>{
        return PreferenceDataStoreFactory.create(corruptionHandler = ReplaceFileCorruptionHandler {
            emptyPreferences()
        },
            scope = CoroutineScope(EtDispatcher.io + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(ET_PREFERENCES) })
    }

    @Provides
    @Singleton
    fun providesDatastoreRepository(datastore: DataStore<Preferences>): DatastoreRepository {
        return DatastoreRepositoryImpl(datastore)
    }

    @Provides
    @Singleton
    fun providesDao(etDatabase: EtDatabase): EtDao {
        return etDatabase.getEtDao()
    }

    @Provides
    @Singleton
    fun providesAccountDao(etDatabase: EtDatabase): AccountDao {
        return etDatabase.getAccountDao()
    }

    @Provides
    @Singleton
    fun providesTransactionPagingDao(etDatabase: EtDatabase): TransactionPagingDao {
        return etDatabase.getTransactionPagingDao()
    }

    @Provides
    @Singleton
    fun providesTransactionDao(etDatabase: EtDatabase): TransactionDao {
        return etDatabase.getTransactionDao()
    }

    @Provides
    @Singleton
    fun providesCategoryDao(etDatabase: EtDatabase): CategoryDao {
        return etDatabase.getCategoryDao()
    }

    @Provides
    @Singleton
    fun providesCrudDao(etDatabase: EtDatabase): CrudDao {
        return etDatabase.getCrudDao()
    }

    @Provides
    @Singleton
    fun providesDatabaseDao(etDatabase: EtDatabase): DatabaseDao {
        return etDatabase.getDatabaseDao()
    }

    @Provides
    @Singleton
    fun providesAccountRepository(accountDao: AccountDao): AccountRepository {
        return AccountRepositoryImpl(accountDao)
    }

    @Provides
    @Singleton
    fun providesEtRepository(etDao: EtDao): EtRepository {
        return EtRepositoryImpl(etDao)
    }

    @Provides
    @Singleton
    fun providesCategoryRepository(categoryDao: CategoryDao): CategoryRepository {
        return CategoryRepositoryImpl(categoryDao)
    }

    @Provides
    @Singleton
    fun providesTransactionPagingRepository(transactionPagingDao: TransactionPagingDao): TransactionPagingRepository {
        return TransactionPagingRepositoryImpl(transactionPagingDao)
    }

    @Provides
    @Singleton
    fun providesTransactionRepository(transactionDao: TransactionDao): TransactionRepository {
        return TransactionRepositoryImpl(transactionDao)
    }

    @Provides
    @Singleton
    fun providesCrudRepository(
        crudDao: CrudDao,
        databaseDao: DatabaseDao
    ): CrudRepository {
        return CrudRepositoryImpl(crudDao, databaseDao)
    }
}