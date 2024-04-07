package com.example.expensetracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [IncomeData::class , ExpenseData::class , TransactionData::class] ,
    version = 1
)

abstract class Datatabase : RoomDatabase() {
    abstract fun incomeDao(): IncomeDAO
    abstract fun expenseDao(): ExpenseDAO
    abstract fun transactionDao(): TransactionDAO

    companion object {
        @Volatile
        private var INSTANCE: Datatabase? = null
        fun getDatabase(context: Context): Datatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext ,
                    Datatabase::class.java ,
                    "App_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}