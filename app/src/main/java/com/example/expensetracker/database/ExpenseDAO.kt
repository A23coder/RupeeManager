package com.example.expensetracker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExpenseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExpensedata(expenseData: ExpenseData)

    @Query("select * from expense_data")
    fun getExpenseData(): List<ExpenseData>

    @Query("select amount from expense_data")
    fun getExpenseTotalAmount(): List<Int>
}

