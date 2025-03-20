package com.zybooks.assignment06

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdepterClass(private val expenses: MutableList<Expense>):
RecyclerView.Adapter<AdepterClass.ViewHolder>(){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nameText: TextView = itemView.findViewById(R.id.expenseNameText)
        val amountText: TextView = itemView.findViewById(R.id.amountTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.text_row_item,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val expense = expenses[position]
        holder.nameText.text = expense.name
        holder.amountText.text = "$${expense.amount}"

        holder.deleteButton.setOnClickListener {
            expenses.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int = expenses.size

    }