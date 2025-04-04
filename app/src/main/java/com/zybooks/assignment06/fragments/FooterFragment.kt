package com.zybooks.assignment06.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.zybooks.assignment06.R

class FooterFragment(
    private var totalExpense: Double):Fragment() {
    private lateinit var expenseTextView: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.footer_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expenseTextView = view.findViewById(R.id.totalExpense)
        expenseTextView.text = "Total Expense: $totalExpense"

    }

    fun updateAmount(newTotal: Double){
        totalExpense = newTotal
        expenseTextView.text = "Total Expense: $totalExpense"
    }
}