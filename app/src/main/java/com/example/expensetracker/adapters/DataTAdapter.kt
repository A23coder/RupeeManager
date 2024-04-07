package com.example.expensetracker.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.database.IncomeData

class DataTAdapter(
    private var dataTransaction: List<IncomeData>
) : RecyclerView.Adapter<DataTAdapter.MyViewViewHolder>() {

    class MyViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val t_name: TextView = itemView.findViewById(R.id.tv_transaction_name)
        val t_transactionDate: TextView = itemView.findViewById(R.id.tv_transaction_date)
        val amount: TextView = itemView.findViewById(R.id.tv_transaction_amount)
        val t_income_source: TextView = itemView.findViewById(R.id.tv_amount_caption)
        val tv_catMode: TextView = itemView.findViewById(R.id.tv_category_mode)
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
        holder.t_name.text = item.details.toString()
        holder.amount.text = "₹" + item.amount.toString()
        holder.t_transactionDate.text = item.date.toString()
        holder.t_income_source.text = item.income_source.toString()
        holder.tv_catMode.text = "Income"
        holder.tv_catMode.setTextColor(
            ContextCompat.getColor(
                holder.itemView.context ,
                R.color.purple_200
            )
        )
//        holder.t_name.text = item.details.toString()
//        holder.t_income_source.text = item.income_source.toString()
//        holder.amount.text = "₹" + item.amount.toString()
//        holder.t_transactionDate.text = item.date.toString()
//        holder.t_amount.text = item?.transaction_amount.toString()
//        holder.img_tr.setImageResource(item!!.img_Id)
    }

}