package com.example.expensetracker

import com.github.mikephil.charting.utils.ColorTemplate.rgb

class UtilsData {
    companion object {
        var right =
            com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.RIGHT
        var top = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.TOP
        var left = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.LEFT
        var bottom = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.BOTTOM
        val MONTHS = mutableListOf(
            "January" ,
            "February" ,
            "March" ,
            "April" ,
            "May" ,
            "June" ,
            "July" ,
            "August" ,
            "September" ,
            "October" ,
            "November" ,
            "December"
        )

        val categories_expense = arrayListOf(
            "Housing Expenses" ,
            "Transportation Expenses" ,
            "Food and Groceries" ,
            "Healthcare Expenses" ,
            "Education Expenses" ,
            "Entertainment and Recreation" ,
            "Personal Care and Clothing" ,
            "Utilities" ,
            "Debt Payments" ,
            "Savings and Investments"
        )
        val payment_mode = arrayListOf(
            "Cash" , "UPI" , "Debit Card" , "Credit Card" , "Net Banking/ M-Banking"
        )
        val income_sourec = listOf(
            "Salary" , "Business" , "Interest" , "Rental Income" , "Capital Gains" , "Selling"
        )

        val colors = mutableListOf(
            rgb("#97BC62") , rgb("#2A3132") ,
            rgb("#172D13") , rgb("#5AC3B0") ,
            rgb("#FDF5DF") , rgb("#848CCF") ,
            rgb("#F7CD46") , rgb("#5AC3B0") ,
            rgb("#FFF5D7") , rgb("#FFAAAB") ,
        )
    }
}