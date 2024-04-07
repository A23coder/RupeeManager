package com.example.expensetracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.BottomnavListener
import com.example.expensetracker.R
import com.example.expensetracker.adapters.DataT1Adapter
import com.example.expensetracker.database.Datatabase
import com.example.expensetracker.database.TransactionData
import com.example.expensetracker.databinding.FragmentTransactionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var appDb: Datatabase
    private lateinit var binding: FragmentTransactionBinding
    private var bottomnavListener: BottomnavListener? = null

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? , savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionBinding.inflate(inflater , container , false)
        appDb = Datatabase.getDatabase(requireContext().applicationContext)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        CoroutineScope(Dispatchers.Main).launch {
            getTransactionData()
        }
        bottomnavListener?.changeBottomNavListener(R.id.home)
        return binding.root
    }

    private suspend fun getTransactionData() {
        val trdao = appDb.transactionDao()
        val transactions: List<TransactionData> = withContext(Dispatchers.IO) {
            trdao.getCombinedTransactions()
        }
        recyclerView.adapter = DataT1Adapter(transactions.sortedWith(compareBy { it.date }))
        toggleEmptyView(transactions.isEmpty())

    }

    private fun toggleEmptyView(isEmpty: Boolean) {
        val emptyStateLayout = binding.emptyStateLayout
        if (isEmpty) {
            recyclerView.visibility = View.GONE
            emptyStateLayout.visibility = View.VISIBLE
            print("recycler view gone")
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyStateLayout.visibility = View.GONE
            print("recycler view visible")
        }
    }
}
