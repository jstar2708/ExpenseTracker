package com.jaideep.expensetracker.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jaideep.expensetracker.data.local.dao.AccountDao
import com.jaideep.expensetracker.data.local.dao.CategoryDao
import com.jaideep.expensetracker.data.local.dao.CrudDao
import com.jaideep.expensetracker.data.local.dao.DatabaseDao
import com.jaideep.expensetracker.data.local.dao.EtDao
import com.jaideep.expensetracker.data.local.dao.NotificationDao
import com.jaideep.expensetracker.data.local.dao.TransactionDao
import com.jaideep.expensetracker.data.local.dao.TransactionPagingDao
import com.jaideep.expensetracker.data.local.entities.Account
import com.jaideep.expensetracker.data.local.entities.Category
import com.jaideep.expensetracker.data.local.entities.Notification
import com.jaideep.expensetracker.data.local.entities.Transaction

@Database(entities = [Account::class, Category::class, Transaction::class, Notification::class], version = 1, exportSchema = true)
@TypeConverters(value = [Converters::class])
abstract class EtDatabase : RoomDatabase() {

    abstract fun getEtDao(): EtDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getTransactionPagingDao(): TransactionPagingDao
    abstract fun getAccountDao(): AccountDao
    abstract fun getTransactionDao(): TransactionDao
    abstract fun getDatabaseDao(): DatabaseDao
    abstract fun getCrudDao(): CrudDao
    abstract fun getNotificationDao(): NotificationDao

    companion object {
        @Volatile
        private var INSTANCE: EtDatabase? = null

        fun getDatabase(context: Context): EtDatabase {
            return INSTANCE ?: synchronized(this) {
                //condition to check if a local database is present or not
                val instance = Room.databaseBuilder(
                    context.applicationContext, EtDatabase::class.java, "my_database"
                )
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}