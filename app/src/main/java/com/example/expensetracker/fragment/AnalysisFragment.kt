package com.example.expensetracker.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.expensetracker.BottomnavListener
import com.example.expensetracker.MainActivity
import com.example.expensetracker.R
import com.example.expensetracker.database.Datatabase
import com.example.expensetracker.database.ExpenseData
import com.example.expensetracker.database.IncomeData
import com.example.expensetracker.databinding.FragmentAnalysisBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AnalysisFragment : Fragment() {
    private var bottomnavListener: BottomnavListener? = null
    private lateinit var binding: FragmentAnalysisBinding
    private lateinit var appDb: Datatabase

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BottomnavListener) {
            bottomnavListener = context
        } else {
            throw RuntimeException("Context is not implemented")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("CommitTransaction")
    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? , savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnalysisBinding.inflate(inflater , container , false)
        appDb = Datatabase.getDatabase(requireContext().applicationContext)
        binding.spendingLayout.setOnClickListener {
            bottomnavListener?.changeBottomNavListener(R.id.transaction)
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout , TransactionFragment())
                .addToBackStack(this@AnalysisFragment.toString()).commit()
        }
        binding.incomeLayout.setOnClickListener {
            bottomnavListener?.changeBottomNavListener(R.id.transaction)
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout , TransactionFragment())
                .addToBackStack(this@AnalysisFragment.toString()).commit()
        }
        CoroutineScope(Dispatchers.Main).launch {
            showExpensePie()
            showIncomePie()
            showAmount()
        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private suspend fun showAmount() {
        appDb = Datatabase.getDatabase(requireContext().applicationContext)
        val amountIncomeDao = appDb.incomeDao()
        val amountIncome: List<Int> = withContext(Dispatchers.IO) {
            amountIncomeDao.getIncomeTotalAmountData()
        }
        val amountExpeseDao = appDb.expenseDao()
        val amountExpense: List<Int> = withContext(Dispatchers.IO) {
            amountExpeseDao.getExpenseTotalAmount()
        }
        val totalIncomeAmount = amountIncome.sum()
        val totalExpenseAmount = amountExpense.sum()

        binding.tvIncomeAmount.text = "₹ ${totalIncomeAmount.toFloat()}"
        binding.tvExpenseAmount.text = "₹ ${totalExpenseAmount.toFloat()}"

    }

    private suspend fun showIncomePie() {
        val chart: PieChart = binding.incomeChart
        val tr_dao = appDb.incomeDao()
        val transaction: List<IncomeData> = withContext(Dispatchers.IO) {
            tr_dao.getIncomeData()
        }

        val entries: ArrayList<PieEntry> = ArrayList()
        transaction.forEachIndexed { index, incomeData ->
            if (incomeData.amount != null && incomeData.income_source != null) {
                entries.add(PieEntry(incomeData.amount.toFloat(), incomeData.income_source))
            }
        }

        if (entries.isNotEmpty()) {
            val pieDataSet = PieDataSet(entries, "Incomes")
            val customColors = arrayListOf(
                Color.rgb(255, 204, 102),  // Orange
                Color.rgb(255, 102, 102),  // Red
                Color.rgb(102, 255, 102),  // Green
                Color.rgb(102, 178, 255),  // Blue
                Color.rgb(178, 102, 255),  // Purple
                Color.rgb(255, 102, 255)   // Pink
            )
            pieDataSet.colors = customColors
            pieDataSet.valueTextColor = Color.BLACK
            pieDataSet.valueTextSize = 20F

            val pieData = PieData(pieDataSet)
            pieData.setValueFormatter(PercentFormatter(chart))
            chart.data = pieData

            chart.description.isEnabled = false
            chart.legend.isEnabled = false
            chart.setUsePercentValues(true)
            chart.animateY(1000)
            chart.invalidate()
        } else {
            chart.clear()
            chart.description.isEnabled = false
        }
    }

    private suspend fun showExpensePie() {
        val chart: PieChart = binding.pieChart

        val tr_dao = appDb.expenseDao()
        val transaction: List<ExpenseData> = withContext(Dispatchers.IO) {
            tr_dao.getExpenseData()
        }

        val entries: ArrayList<PieEntry> = ArrayList()
        transaction.forEachIndexed { index, expenseData ->
            if (expenseData.amount != null && expenseData.type_epxpense != null) {
                entries.add(PieEntry(expenseData.amount.toFloat(), expenseData.type_epxpense))
            }
        }

        if (entries.isNotEmpty()) {
            val pieDataSet = PieDataSet(entries, "Spending")
            val customColors = arrayListOf(
                Color.rgb(255, 204, 102),  // Orange
                Color.rgb(255, 102, 102),  // Red
                Color.rgb(102, 255, 102),  // Green
                Color.rgb(102, 178, 255),  // Blue
                Color.rgb(178, 102, 255),  // Purple
                Color.rgb(255, 102, 255)   // Pink
            )
            pieDataSet.colors = customColors
            pieDataSet.valueTextColor = Color.BLACK
            pieDataSet.valueTextSize = 20F

            val pieData = PieData(pieDataSet)
            pieData.setValueFormatter(PercentFormatter(chart))
            chart.data = pieData

            chart.description.isEnabled = false
            chart.legend.isEnabled = false
            chart.setUsePercentValues(true)
            chart.animateY(1000)
            chart.invalidate()
        } else {
            chart.clear()
            chart.description.isEnabled = false
        }
    }

}