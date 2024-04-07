package com.example.expensetracker.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.expensetracker.BottomnavListener
import com.example.expensetracker.MainActivity
import com.example.expensetracker.R
import com.example.expensetracker.adapters.DataT1Adapter
import com.example.expensetracker.database.Datatabase
import com.example.expensetracker.database.TransactionData
import com.example.expensetracker.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var appDb: Datatabase
    private lateinit var firebaseAuth: FirebaseAuth
    private var bottomnavListener: BottomnavListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BottomnavListener) {
            bottomnavListener = context
        } else {
            throw RuntimeException("Context must implement BottomnavListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? , savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater , container , false)
        appDb = Datatabase.getDatabase(requireContext().applicationContext)
        firebaseAuth = FirebaseAuth.getInstance()

        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            binding.tvUsername.text = currentUser.displayName ?: "User Name"
            currentUser.photoUrl?.let {
                Glide.with(this).load(it).into(binding.imgUser)
            }
        } else {
            binding.tvUsername.text = "User Name"
            binding.imgUser.setImageResource(R.drawable.user)
        }

        setupClickListeners()
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        CoroutineScope(Dispatchers.Main).launch {
            getTransactionData()
            getAmount()
        }
        return binding.root
    }

    private fun setupClickListeners() {
        binding.spendingLayout.setOnClickListener {
            navigateToTransactionFragment()
        }
        binding.incomeLayout.setOnClickListener {
            navigateToTransactionFragment()
        }
        binding.imgSearch.setOnClickListener {
            navigateToTransactionFragment()
        }
    }

    private fun navigateToTransactionFragment() {
        bottomnavListener?.changeBottomNavListener(R.id.transaction)
        (activity as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout , TransactionFragment())
            .addToBackStack(this@HomeFragment.toString()).commit()
    }

    @SuppressLint("SetTextI18n")
    private suspend fun getAmount() {
        val amountIncomeDao = appDb.incomeDao()
        val amountIncome: List<Int> = withContext(Dispatchers.IO) {
            amountIncomeDao.getIncomeTotalAmountData()
        }
        val amountExpenseDao = appDb.expenseDao()
        val amountExpense: List<Int> = withContext(Dispatchers.IO) {
            amountExpenseDao.getExpenseTotalAmount()
        }
        val totalIncomeAmount = amountIncome.sum()
        val totalExpenseAmount = amountExpense.sum()

        val balance = if (totalIncomeAmount >= totalExpenseAmount) {
            totalIncomeAmount - totalExpenseAmount
        } else {
            -(totalExpenseAmount - totalIncomeAmount)
        }
        binding.tvBalanceAmount.text = "₹ ${balance.toFloat()}"
        binding.tvIncomeAmount.text = "₹ ${totalIncomeAmount.toFloat()}"
        binding.tvExpenseAmount.text = "₹ ${totalExpenseAmount.toFloat()}"
    }

    private suspend fun getTransactionData() {
        val transactionDao = appDb.transactionDao()
        val transaction: List<TransactionData> = withContext(Dispatchers.IO) {
            transactionDao.getCombinedTransactions()
        }
        val filteredTransactions = if (transaction.size < 10) {
            transaction.sortedBy { it.date }
        } else {
            transaction.sortedBy { it.date }.take(10)
        }
        recyclerView.adapter = DataT1Adapter(filteredTransactions)
        toggleEmptyView(filteredTransactions.isEmpty())
    }

    private fun toggleEmptyView(isEmpty: Boolean) {
        val emptyStateLayout = binding.emptyStateLayout
        recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
        emptyStateLayout.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }
}
