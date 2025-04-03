package com.zybooks.assignment06

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Currency
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zybooks.assignment06.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

import java.io.FileNotFoundException
import java.io.IOException
import java.util.Calendar

private const val FILE_NAME = "expense.txt"

class MainFragment : Fragment() {
    private lateinit var expenseInput: EditText
    private lateinit var amountInput: EditText
    private lateinit var dateInput: Button
    private lateinit var expenseAdapter: AdepterClass
    private val expenses = mutableListOf<Expense>()
    private lateinit var tipsButton: Button
    private lateinit var footerFragment: FooterFragment
    private lateinit var headerFragment: HeaderFragment
    private lateinit var currencySpinner: Spinner

    private var date: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        Log.d("ActivityLifecycle", "onCreate called")


        expenseInput = view.findViewById(R.id.expenseText)
        amountInput = view.findViewById(R.id.amountText)
        dateInput = view.findViewById(R.id.dateField)
        val addButton: Button = view.findViewById(R.id.addExpenseBtn)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        tipsButton = view.findViewById(R.id.tipsButton)
        currencySpinner = view.findViewById(R.id.currencySpinner)


        val currencies = Currency.getAvailableCurrencies().map { it.currencyCode }.sorted()
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, currencies)
        currencySpinner.adapter = adapter

        val defaultIndex = currencies.indexOfFirst { it == "CAD" }
        if (defaultIndex >= 0)
            currencySpinner.setSelection(defaultIndex)

        expenses.clear()
        expenses.addAll(loadTasksFromFile(requireContext()))
        footerFragment = FooterFragment(expenses.sumOf { it.amount })
        expenseAdapter = AdepterClass(expenses, footerFragment, this)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = expenseAdapter


        addButton.setOnClickListener {
            addExpense()
        }
        dateInput.setOnClickListener {
            showDatePicker()
        }


        tipsButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data =
                Uri.parse("https://en.wikipedia.org/wiki/2020%E2%80%932021_Indian_farmers%27_protest")
            startActivity(intent)
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.footerFragmentContainer, footerFragment)
            .addToBackStack(null)
            .commit()

        headerFragment = HeaderFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.headerFragmentContainer, headerFragment).addToBackStack(null)
            .commit()

        return view
    }


    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                dateInput.text = date
            }, year, month, day)

        datePickerDialog.show()
    }


    private fun addExpense() {
        val name = expenseInput.text.toString().trim()
        val amountText = amountInput.text.toString().trim()
        val date = dateInput.text.toString().trim()

        val amount = amountText.toDoubleOrNull()

        val selectedCurrencyCode = currencySpinner.selectedItem.toString()
        val selectedCurrency = Currency.getInstance(selectedCurrencyCode)

        if (name.isNotEmpty() && amount != null && date.isNotEmpty()) {
            lifecycleScope.launch {
                try {
                    val response = withContext(Dispatchers.IO) {
                        RetrofitInstance.api.getPrice()
                    }
                    val exchangeRate = response.cad[selectedCurrencyCode] ?: 1.0
                    val convertedAmount = amount * exchangeRate

                    val finalAmount = amountText.toDouble()
                    expenses.add(
                        Expense(
                            name,
                            finalAmount,
                            date,
                            selectedCurrency,
                            convertedAmount
                        )
                    )
                    expenseAdapter.notifyItemInserted(expenses.size - 1)
                    expenseInput.text.clear()
                    amountInput.text.clear()
                    saveTasksToFile(requireContext(), expenses)
                    footerFragment.updateAmount(expenses.sumOf { it.amount })

                    Toast.makeText(
                        requireContext(),
                        "Expense added with conversion",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Failed to fetch currency: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Can you please enter the valid value",
                Toast.LENGTH_SHORT
            )
                .show()
        }
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


//    private fun totalExpense(): Double {
//        return expenses.sumOf { it.amount }
//    }


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

    @SuppressLint("NotifyDataSetChanged")
    fun onDeleteClick(expense:  Expense){
        expenses.remove(expense)
        expenseAdapter.notifyDataSetChanged()
        saveTasksToFile(requireContext(), expenses)
    }

}