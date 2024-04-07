package com.example.expensetracker.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.database.TransactionData
import java.util.Locale

class DataT1Adapter(
    private var dataTransaction: List<TransactionData>
) : RecyclerView.Adapter<DataT1Adapter.MyViewViewHolder>() {

    class MyViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val t_name: TextView = itemView.findViewById(R.id.tv_transaction_name)
        val t_transactionDate: TextView = itemView.findViewById(R.id.tv_transaction_date)
        val amount: TextView = itemView.findViewById(R.id.tv_transaction_amount)
        val t_income_source: TextView = itemView.findViewById(R.id.tv_amount_caption)
        val tv_catMode: TextView = itemView.findViewById(R.id.tv_category_mode)
        val t_img_mode: ImageView = itemView.findViewById(R.id.img_tr_mode)
        val img_tr: ImageView = itemView.findViewById(R.id.img_tr)
    }

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): MyViewViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.transaction_items , parent , false)
        return MyViewViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataTransaction.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n" , "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewViewHolder , position: Int) {
        val item = dataTransaction[position]
        holder.t_name.text = item.details!!.capitalize(Locale.ROOT)
        holder.amount.text = "₹" + item.amount.toString()
        holder.t_transactionDate.text = item.date.toString()
        holder.t_income_source.text = item.sourceOrExpenseType.toString()
        holder.tv_catMode.text = item.t_mode.toString()
        checkCondditions(holder , item)
    }

    private fun checkCondditions(holder: MyViewViewHolder , item: TransactionData) {
        if (item.t_mode == "Income") {
            holder.tv_catMode.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context , R.color.purple_200
                )
            )
            holder.amount.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context , R.color.purple_200
                )
            )
            holder.t_img_mode.setImageResource(R.drawable.banking)
        } else {
            holder.tv_catMode.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context , R.color.red
                )
            )
            holder.amount.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context , R.color.red
                )
            )
            holder.t_img_mode.setImageResource(R.drawable.cash)
        }
        if (holder.tv_catMode.text == "Income") {
            when (holder.t_income_source.text) {
                "Salary" -> {
                    holder.img_tr.setImageResource(R.drawable.banking)
                }

                "Business" -> {
                    holder.img_tr.setImageResource(R.drawable.cash)
                }

                "Interest" -> {
                    holder.img_tr.setImageResource(R.drawable.banking)
                }

                "Rental Income" -> {
                    holder.img_tr.setImageResource(R.drawable.house)
                }

                "Capital Gains" -> {
                    holder.img_tr.setImageResource(R.drawable.banking)
                }

                "Interest" -> {
                    holder.img_tr.setImageResource(R.drawable.banking)
                }

                else -> {
                    holder.img_tr.setImageResource(R.drawable.cash)
                }
            }
        } else {
            when (holder.t_income_source.text) {
                "Healthcare Expenses" -> {
                    holder.img_tr.setImageResource(R.drawable.medical)
                }

                "Housing Expenses" -> {
                    holder.img_tr.setImageResource(R.drawable.house)
                }

                "Transportation Expenses" -> {
                    holder.img_tr.setImageResource(R.drawable.transportation)
                }

                "Food and Groceries" -> {
                    holder.img_tr.setImageResource(R.drawable.ggrocery)
                }

                "Education Expenses" -> {
                    holder.img_tr.setImageResource(R.drawable.edc)
                }

                "Entertainment and Recreation" -> {
                    holder.img_tr.setImageResource(R.drawable.recreational)
                }

                "Personal Care and Clothing" -> {
                    holder.img_tr.setImageResource(R.drawable.clothes)
                }

                "Utilities" -> {
                    holder.img_tr.setImageResource(R.drawable.utility)
                }

                "Debt Payments" -> {
                    holder.img_tr.setImageResource(R.drawable.pydebts)
                }

                "Savings and Investments" -> {
                    holder.img_tr.setImageResource(R.drawable.investing)
                }

                else -> {
                    holder.img_tr.setImageResource(R.drawable.cash)
                }
            }
        }
    }
}