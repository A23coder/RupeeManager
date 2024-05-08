package com.example.expensetracker.bin

class data {
//    @OptIn(DelicateCoroutinesApi::class)
//    private fun getTransactionData() {
//        try {
//            CoroutineScope(Dispatchers.Main).launch {
//                val dao = appDb.incomeDao()
//                val income: List<IncomeData> = dao.getIncomeData()
//                Log.d("DATAAAA","${income.get(1).date}")
//                recyclerView.adapter = DataTAdapter(income)
//                toggleEmptyView()
//            }
//        }catch (_:Exception){
//        println("========")
//        }
//    }


//class TransactionFragment : Fragment() {
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var appDb: Datatabase
//
//    //    private lateinit var newArrList: ArrayList<DataTransaction>
//    var dataTransaction: ArrayList<DataTransaction>? = null
//
//    private lateinit var binding: FragmentTransactionBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater , container: ViewGroup? , savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentTransactionBinding.inflate(inflater , container , false)
//        appDb = Datatabase.getDatabase(context!!.applicationContext)
//
//        getTransactionData()
//        return binding.root
//    }
//
//
//    @OptIn(DelicateCoroutinesApi::class)
//    private fun getTransactionData() {
////        dataTransaction!!.add(DataTransaction(R.drawable.rent , "Rent" , "₹ 10000"))
////        dataTransaction!!.add(DataTransaction(R.drawable.groceries , "Groceries" , "₹ 10000"))
//        CoroutineScope(Dispatchers.Main).launch {
//            GlobalScope.launch(Dispatchers.Main) {
//                val dao =appDb.incomeDao()
//                val income: List<IncomeData> = dao.getIncomeData()
//                appDb.incomeDao().getIncomeData()
//                recyclerView.adapter = DataTAdapter(income)
//                recyclerView = binding.recyclerView
//                recyclerView.layoutManager = LinearLayoutManager(context)
//                toggleEmptyView()
//            }
//        }
//
//
//    }
//
//    private fun toggleEmptyView() {
//        val emptyStateLayout = binding.emptyStateLayout
//        if (dataTransaction.isNullOrEmpty()) {
//            recyclerView.visibility = View.GONE
//            emptyStateLayout.visibility = View.VISIBLE
//        } else {
//            recyclerView.visibility = View.VISIBLE
//            emptyStateLayout.visibility = View.GONE
//        }
//    }
//}


    //        val indao = appDb.incomeDao()
//        val income: List<IncomeData> = withContext(Dispatchers.IO) {
//            indao.getIncomeData()
//        }

    //        val income: List<IncomeData> = withContext(Dispatchers.IO) {
//            indao.getIncomeData()
//        }
//        val exdao = appDb.expenseDao()
//        val expense: List<ExpenseData> = withContext(Dispatchers.IO) {
//            exdao.getExpenseData()
//        }
//        recyclerView.adapter = DataTAdapter(income.sortedByDescending { it.date })

}