package com.zybooks.assignment06

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var expenseInput: EditText
    private lateinit var amountInput: EditText
    private lateinit var expenseAdapter: AdepterClass
    private val expenses = mutableListOf<Expense>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        expenseInput = findViewById(R.id.expenseText)
        amountInput = findViewById(R.id.amountText)
        val addButton: Button = findViewById(R.id.addExpenseBtn)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)



        recyclerView.layoutManager = LinearLayoutManager(this)
        expenseAdapter = AdepterClass(expenses)
        recyclerView.adapter = expenseAdapter


        addButton.setOnClickListener {
            val expense = expenseInput.text.toString().trim()
            val amountText = amountInput.text.toString().trim()

            val amount = amountText.toDouble()
            if (expense.isNotEmpty() && amount != null && amount > 0) {
                val newExpense = Expense(expense,amount)
                expenses.add(newExpense)
                expenseAdapter.notifyItemInserted(expenses.size - 1)

                // Clear input fields
                expenseInput.text.clear()
                amountInput.text.clear()
            }else {
                Toast.makeText(this, "Please enter valid data", Toast.LENGTH_SHORT).show()
            }
        }
    }

}


