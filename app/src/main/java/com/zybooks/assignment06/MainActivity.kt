package com.zybooks.assignment06

import AdepterClass
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar
//Gson Import
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

private const val FILE_NAME = "expense.txt"

class MainActivity : AppCompatActivity() {

    private lateinit var expenseInput: EditText
    private lateinit var amountInput: EditText
    private lateinit var dateInput: Button
    private lateinit var expenseAdapter: AdepterClass
    private val expenses = mutableListOf<Expense>()
    private lateinit var tipsButton: Button


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

        tipsButton = findViewById(R.id.tipsButton)

        expenses.clear()
        expenses.addAll(loadTasksFromFile(this))

        recyclerView.layoutManager = LinearLayoutManager(this)
        expenseAdapter = AdepterClass(
            expenses,
            onDeleteClick = { position -> deleteExpenses(position) },
            onShowDetailClick = { position -> showDetailsButton(position)
            }

        )
        recyclerView.adapter = expenseAdapter


        addButton.setOnClickListener{
            addExpense()
        }
        dateInput.setOnClickListener {
            showDatePicker()
        }


        tipsButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://en.wikipedia.org/wiki/2020%E2%80%932021_Indian_farmers%27_protest")
            startActivity(intent)
        }
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.headerFragmentContainer, HeaderFragment())
        transaction.replace(R.id.footerFragmentContainer, FooterFragment(totalExpense()))
        transaction.commit()
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
        val amountText = amountInput.text.toString().trim()
        val date = dateInput.text.toString().trim()

        val amount = amountText.toDoubleOrNull()

        if (name.isEmpty()|| amount == null || date.isEmpty()){
            Toast.makeText(this, " Please enter name, amount and date", Toast.LENGTH_SHORT).show()
        }
        else {
            val item = Expense(name,amount, date)
            expenses.add(item)
            expenseAdapter.notifyItemInserted(expenses.size - 1)
            updateFooter()
            expenseInput.text.clear()
            amountInput.text.clear()

            dateInput.text = "Select Date"
            saveTasksToFile(this,expenses)
        }
    }

    private fun showDetailsButton(position: Int){
        val expense = expenses[position]
        val intent = Intent(this, ExpenseDetailActivity::class.java).apply {
            putExtra("expense_name", expense.name)
            putExtra("expense_amount", expense.amount)
            putExtra("expense_date", expense.date)
        }
        startActivity(intent)
    }




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

    private  fun deleteExpenses(position: Int){
        expenses.removeAt(position)
        expenseAdapter.notifyItemRemoved(position)
        saveTasksToFile(this, expenses)
        updateFooter()
        }
    private fun updateFooter(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.footerFragmentContainer, FooterFragment(totalExpense()))
        fragmentTransaction.commit()
    }

    private fun totalExpense(): Double {
        return expenses.sumOf { it.amount }
    }


    private fun saveTasksToFile(context: Context, expenses: List<Expense>) {
        try {
            val json = Gson().toJson(expenses)
            context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use { output ->
                output.write(json.toByteArray())
            }
            Log.d("FileStorage", "Tasks saved successfully")
        } catch (e: IOException) {
            Log.e("FileStorage", "Error saving tasks: ${e.message}")
        }
    }

    private fun loadTasksFromFile(context: Context): MutableList<Expense> {
        val expenses: MutableList<Expense> = mutableListOf()
        try {
            val file = File(context.filesDir, FILE_NAME)
            if (!file.exists()) return expenses

            val json = file.readText()
            val type = object : TypeToken<List<Expense>>() {}.type
            val loadedTasks: List<Expense> = Gson().fromJson(json, type)
            expenses.addAll(loadedTasks)

            Log.d("FileStorage", "Tasks loaded successfully")
        } catch (e: FileNotFoundException) {
            Log.e("FileStorage", "File not found: ${e.message}")
        } catch (e: IOException) {
            Log.e("FileStorage", "Error reading file: ${e.message}")
        }
        return expenses
    }


}



