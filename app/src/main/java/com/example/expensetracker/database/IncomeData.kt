package com.example.expensetracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "income_table")
data class IncomeData(
    @PrimaryKey(autoGenerate = true)
    val id: Int ,
    val date: String? ,
    val type:String,
    val amount: Int? ,
    val income_source: String? ,
    val details: String?
)

// date , amount, name, description, type-source, expens type
@Entity(tableName = "expense_data")
data class ExpenseData(
    @PrimaryKey(autoGenerate = true) val id: Int ,
    val date: String? ,
    val type:String,
    val amount: Int? ,
    val type_epxpense: String? ,
    val details: String? ,
    val type_payment: String?
)

@Entity("transaction_table")
data class TransactionData(
    @PrimaryKey(autoGenerate = true)
    val id: Int ,
    val date: String? ,
    val amount: Int? ,
    val t_mode: String? , // This could be "income" or "expense" to differentiate between income and expense
    val sourceOrExpenseType: String? , // This could be either income_source or type_expense depending on the type
    val details: String?
)

