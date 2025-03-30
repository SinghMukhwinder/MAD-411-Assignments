package com.zybooks.assignment06

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ExpenseDetailFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expense_detail, container, false)

        val nameExpense = arguments?.getString("expense_name") ?: "No Expense name"
        val amountExpense = arguments?.getString("expense_amount") ?: "No Expense Amount"
        val dateExpense = arguments?.getString("expense_date") ?: "No Expense Date"

        val expenseTitle: TextView = view.findViewById(R.id.expenseName)
        val amountTitle: TextView = view.findViewById(R.id.expenseAmount)
        val dateTitle: TextView = view.findViewById(R.id.expenseDate)


        expenseTitle.text = "Expense Name: $nameExpense"
        amountTitle.text = "Expense Amount: $amountExpense"
        dateTitle.text = "Expense Date: $dateExpense"

        return  view
    }
}