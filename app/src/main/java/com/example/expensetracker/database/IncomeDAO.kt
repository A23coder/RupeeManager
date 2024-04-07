package com.example.expensetracker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IncomeDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIncomeData(incomeData: IncomeData)


    @Query("select * from income_table")
    fun getIncomeData(): List<IncomeData>

    @Query("select amount from income_table")
    fun getIncomeTotalAmountData(): List<Int>

}

