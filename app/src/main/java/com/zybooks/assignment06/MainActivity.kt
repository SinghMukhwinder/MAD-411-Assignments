package com.zybooks.assignment06

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.Transliterator.Position
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var expenseInput: EditText
    private lateinit var amountInput: EditText
    private lateinit var dateInput: TextInputEditText
    private lateinit var expenseAdapter: AdepterClass
    private val expenses = mutableListOf<Expense>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("ActivityLifecycle", "onStart called")



        expenseInput = findViewById(R.id.expenseText)
        amountInput = findViewById(R.id.amountText)
        dateInput =  findViewById(R.id.dateField)
        val addButton: Button = findViewById(R.id.addExpenseBtn)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)



        recyclerView.layoutManager = LinearLayoutManager(this)
        expenseAdapter = AdepterClass(expenses)
        recyclerView.adapter = expenseAdapter


        addButton.setOnClickListener{
            addExpense()}
        dateInput.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            dateInput.setText(selectedDate)
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun addExpense(){
        val name = expenseInput.text.toString().trim()
        val amount = amountInput.text.toString().trim()
        val date = dateInput.text.toString().trim()

        if (name.isEmpty()||amount.isEmpty()){
            Toast.makeText(this, " Please enter name and amount", Toast.LENGTH_SHORT).show()
        }
        else {
            val item = Expense(name,"$${amount}", date)
            expenses.add(item)
            expenseAdapter.notifyItemInserted(expenses.size - 1)
            expenseInput.text.clear()
            amountInput.text.clear()
        }
    }

    private fun showExpenseDetails(position: Int){
        val expense = expenses[position]
        val intent = Intent(this, ExpenseDetailActivity::class.java).apply {
            putExtra("expense_name", expense.name)
            putExtra("expense_amount", expense.amount)
            putExtra("expense_date", expense.date)
        }
        startActivity(intent)
    }


    fun showName(view: View) {}


    override fun onStart() {
        super.onStart()
        Log.d("ActivityLifecycle", "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ActivityLifecycle", "onResume called")

    }

    override fun onPause() {
        super.onPause()
        Log.d("ActivityLifecycle", "onPause called")

    }

    override fun onStop() {
        super.onStop()
        Log.d("ActivityLifecycle", "onStop called")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ActivityLifecycle", "onDestroy called")

    }

}



