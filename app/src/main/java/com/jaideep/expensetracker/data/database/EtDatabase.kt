package com.jaideep.expensetracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jaideep.expensetracker.data.dao.EtDao
import com.jaideep.expensetracker.data.entities.Account
import com.jaideep.expensetracker.data.entities.Category
import com.jaideep.expensetracker.data.entities.Transaction

@Database(entities = [Account::class, Transaction::class, Category::class], version = 1)
abstract class EtDatabase : RoomDatabase(){

    abstract fun getEtDao(): EtDao

    companion object {
        @Volatile
        private var INSTANCE: EtDatabase? = null

        fun getDatabase(context: Context): EtDatabase {
            return INSTANCE ?: synchronized(this){
                //condition to check if a local database is present or not
                val instance =  Room.databaseBuilder(
                    context.applicationContext,
                    EtDatabase::class.java,
                    "my_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}