package com.zybooks.assignment06

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ExpenseDetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expense_activity_details)

        val expenseTitle: TextView = findViewById(R.id.expenseName)
        val amountTitle: TextView = findViewById(R.id.expenseAmount)
        val dateTitle: TextView = findViewById(R.id.expenseDate)

        val nameExpense = intent.getStringExtra("expense_name")
        val amountExpense = intent.getStringExtra("expense_amount")
        val dateExpense = intent.getStringExtra("expense_date")

        expenseTitle.text = "Expense Name: $nameExpense"
        amountTitle.text = "Expense Amount: $amountExpense"
        dateTitle.text = "Expense Date: $dateExpense"
    }
}