package com.example.expensetracker.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TransactionDAO {
    @Query("SELECT income_table.id, income_table.date, income_table.amount, 'Income' as t_mode, income_table.income_source as sourceOrExpenseType, income_table.details FROM income_table UNION ALL SELECT expense_data.id, expense_data.date, expense_data.amount, 'Expense' as t_mode , expense_data.type_epxpense as sourceOrExpenseType, expense_data.details as detailsOfex FROM expense_data")
    fun getCombinedTransactions(): List<TransactionData>
}
