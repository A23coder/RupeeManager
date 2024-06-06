package com.example.expensetracker

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.expensetracker.adapters.DataT1Adapter
import com.example.expensetracker.database.Datatabase
import com.example.expensetracker.database.ExpenseData
import com.example.expensetracker.database.IncomeData
import com.example.expensetracker.databinding.ActivityAddDataBinding
import com.example.expensetracker.fragment.HomeFragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Suppress("DEPRECATION")
class AddData : AppCompatActivity() , BottomnavListener {
    private lateinit var binding: ActivityAddDataBinding
    private lateinit var database: Datatabase
    private var radioButtonText: Any? = null
    private lateinit var db: FirebaseFirestore
    private val adapter = DataT1Adapter(emptyList())

    @SuppressLint("SetTextI18n" , "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        println("HIIIIIII")

        binding.tvMode.text = "Expense"
        binding.tvMode.setTextColor(Color.RED)
        getCalender()

        database = Datatabase.getDatabase(applicationContext)
        db = FirebaseFirestore.getInstance()

        val catAdapter = ArrayAdapter(
            this@AddData ,
            android.R.layout.simple_spinner_dropdown_item ,
            UtilsData.categories_expense
        )
        binding.catSpinner.adapter = catAdapter
        val payAdapter = ArrayAdapter(
            this@AddData , android.R.layout.simple_spinner_dropdown_item , UtilsData.payment_mode
        )
        binding.paymentSpinner.isEnabled = true
        binding.paymentSpinner.adapter = payAdapter

        binding.rdGrp.setOnCheckedChangeListener { _ , checkedId ->
            val radioButton: RadioButton = findViewById(checkedId)
            radioButtonText = radioButton.text.toString()

            val incometext = "Income"
            if (radioButtonText == incometext) {
                val incomeAdapter = ArrayAdapter(
                    this@AddData ,
                    android.R.layout.simple_spinner_dropdown_item ,
                    UtilsData.income_sourec
                )
                binding.catSpinner.adapter = incomeAdapter
                binding.paymentSpinner.isEnabled = false
                binding.tvMode.text = "Income"
                binding.tvMode.setTextColor(resources.getColor(R.color.purple_200))
            } else {
                val catAdapter = ArrayAdapter(
                    this@AddData ,
                    android.R.layout.simple_spinner_dropdown_item ,
                    UtilsData.categories_expense
                )
                binding.catSpinner.adapter = catAdapter
                val payAdapter = ArrayAdapter(
                    this@AddData ,
                    android.R.layout.simple_spinner_dropdown_item ,
                    UtilsData.payment_mode
                )
                binding.paymentSpinner.isEnabled = true
                binding.paymentSpinner.adapter = payAdapter
                binding.tvMode.text = "Expense"
                binding.tvMode.setTextColor(Color.RED)
            }
        }
        binding.addDataFab.setOnClickListener {
            if (radioButtonText == "Income") {
                println("=== income radio he")
                writeIncomeData()
            } else {
                println("=== expense radio che")
                writeExpenseData()
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun writeExpenseData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val mode = binding.tvMode.text.toString()
                val date = binding.tvDate.text.toString()
                val amount = binding.edtAmount.text.toString()
                val expense_cat = binding.catSpinner.selectedItem.toString()
                val payment_mode = binding.paymentSpinner.selectedItem.toString()
                val details = binding.edtDetails.text.toString()
                println("$mode $date $amount $expense_cat $payment_mode $details")
                Log.d(
                    "AddData" ,
                    "Mode: $mode, Date: $date, Amount: $amount, Income Source: $expense_cat, Details: $details"
                )
                if (date.isNotEmpty() && amount.isNotEmpty() && expense_cat.isNotEmpty()) {
                    GlobalScope.launch(Dispatchers.Main) {
                        val expenseData = ExpenseData(
                            0 ,
                            date ,
                            mode ,
                            Integer.parseInt(amount) ,
                            expense_cat ,
                            details ,
                            payment_mode
                        )
                        database.expenseDao().insertExpensedata(expenseData)
                        Log.d("ExData" , "${expenseData.details.toString()} ${expenseData.amount}")
                        Toast.makeText(
                            this@AddData , "Data SuccessFully Added" , Toast.LENGTH_SHORT
                        ).show()
//                        val dbExpenseData = db.collection("ExpenseData")
//                        dbExpenseData.add(expenseData).addOnSuccessListener {
//                            Toast.makeText(
//                                this@AddData ,
//                                "Your Data has been Saved on Cloud" ,
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }.addOnFailureListener {
//                            Toast.makeText(
//                                this@AddData ,
//                                "Your Data has not been Saved on Cloud due to network issue" ,
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
                        binding.edtAmount.text.clear()
                        binding.edtDetails.text.clear()
                        val fragment = HomeFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout , fragment).commit()
                    }
                } else {
                    Toast.makeText(
                        this@AddData , "Data not Added" , Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (_: Exception) {
            }
        }
    }


    @SuppressLint("SimpleDateFormat" , "SetTextI18n")
    private fun getCalender() {
        val sdf = SimpleDateFormat("d-MMMM-yyyy")
        binding.tvDate.text = sdf.format(Date())
        binding.tvDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dateDialog = DatePickerDialog(this@AddData , { _ , year , month , dayOfMonth ->
                binding.tvDate.text = "$dayOfMonth-${UtilsData.MONTHS[month]}-$year "
            } , year , month , day)
            dateDialog.show()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun writeIncomeData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val mode = binding.tvMode.text.toString()
                val date = binding.tvDate.text.toString()
                val amount = binding.edtAmount.text.toString()
                val income_source = binding.catSpinner.selectedItem.toString()
                val details = binding.edtDetails.text.toString()
                Log.d(
                    "AddData" ,
                    "Mode: $mode, Date: $date, Amount: $amount, Income Source: $income_source, Details: $details"
                )
                if (date.isNotEmpty() && amount.isNotEmpty() && income_source.isNotEmpty()) {
                    GlobalScope.launch(Dispatchers.Main) {
                        val incomeData = IncomeData(
                            0 , date , mode , Integer.parseInt(amount) , income_source , details
                        )
                        database.incomeDao().insertIncomeData(incomeData)
                        Toast.makeText(
                            this@AddData , "Data SuccessFully Added" , Toast.LENGTH_SHORT
                        ).show()
                        binding.edtAmount.text.clear()
                        binding.edtDetails.text.clear()

                        val fragment = HomeFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout , fragment).commit()
//                        val dbIncomeData = db.collection("IncomeData")
//                        dbIncomeData.add(incomeData).addOnSuccessListener {
//                            Toast.makeText(
//                                this@AddData ,
//                                "Your Data has been Saved on Cloud" ,
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }.addOnFailureListener {
//                            Toast.makeText(
//                                this@AddData ,
//                                "Your Data has not been Saved on Cloud due to network issue" ,
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
                    }
                } else {
                    Toast.makeText(
                        this@AddData , "Data not Added" , Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (_: Exception) {
            }
        }
    }

    override fun changeBottomNavListener(itemId: Int) {
        Toast.makeText(this , "Navigation item clicked: $itemId" , Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Log.d("Start" , "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Start" , "onRestart")
//        HomeFragment().refreshData(this)
    }

    override fun onStart() {
        super.onStart()
        Log.d("Start" , "On Start")
    }
}
