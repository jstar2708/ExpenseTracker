package com.jaideep.expensetracker.di

import android.app.Application
import com.jaideep.expensetracker.data.dao.EtDao
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
    fun providesEtRepositoryImpl(etDao: EtDao) : EtRepositoryImpl {
        return EtRepositoryImpl(etDao)
    }
}