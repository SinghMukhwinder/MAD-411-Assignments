package com.zybooks.assignment06

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
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

    @SuppressLint("MissingInflatedId")
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


        addButton.setOnClickListener{
            addExpense()
        }
        }
    private fun addExpense(){
        val name = expenseInput.text.toString().trim()
        val amount = amountInput.text.toString().trim()

        if (name.isEmpty()||amount.isEmpty()){
            Toast.makeText(this, " Please enter name and amount", Toast.LENGTH_SHORT).show()
        }
        else {
            val item = Expense(name,"$${amount}")
            expenses.add(item)
            expenseAdapter.notifyItemInserted(expenses.size - 1)
            expenseInput.text.clear()
            amountInput.text.clear()
        }

    }
    private fun deleteExpense(position: Int){
        expenses.removeAt(position)
        expenseAdapter.notifyItemRemoved(position)
    }

    fun showName(view: View) {}

}


