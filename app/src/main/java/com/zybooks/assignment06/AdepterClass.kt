package com.zybooks.assignment06

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.zybooks.assignment06.fragments.FooterFragment
import com.zybooks.assignment06.fragments.MainFragment
import com.zybooks.assignment06.models.Expense
import java.util.Currency

class AdepterClass(private val expenses: MutableList<Expense>,
                   private val footerFragment: FooterFragment,
                   private val mainFragment: MainFragment
):
    RecyclerView.Adapter<AdepterClass.ViewHolder>(){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nameText: TextView = itemView.findViewById(R.id.expenseNameText)
        val amountText: TextView = itemView.findViewById(R.id.amountTextView)
        val textDate: TextView = itemView.findViewById(R.id.dateView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        val showDetailsButton: Button = itemView.findViewById(R.id.showDetailsButton)
        val convertedCostText: TextView = itemView.findViewById(R.id.convertedCostField)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.text_row_item,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val expense = expenses[position]
        val currencySymbol = Currency.getInstance(expense.currency).symbol
        holder.nameText.text = expense.name
        holder.amountText.text = expense.amount.toString()
        holder.textDate.text = expense.date
        holder.convertedCostText.text = "$currencySymbol${expense.convertedCost}"


        holder.deleteButton.setOnClickListener {
            expenses.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, expenses.size)
            val newAmount = expenses.sumOf { it.amount }
            mainFragment.onDeleteClick(expense)
            footerFragment.updateAmount(newAmount)
        }

        holder.showDetailsButton.setOnClickListener{
            val bundle = Bundle().apply {
                putString("expense_name", expense.name)
                putString("expense_amount", expense.amount.toString())
                putString("expense_date", expense.date)
                putDouble("expense_conversion",expense.convertedCost)
                putString("currency_code", expense.currency)

            }
            holder.itemView.findNavController().navigate(R.id.action_mainFragment_to_expenseDetailFragment, bundle)
        }
    }

    override fun getItemCount(): Int = expenses.size
}