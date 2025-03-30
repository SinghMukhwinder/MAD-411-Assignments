package com.zybooks.assignment06

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

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


        val financialTips: Button = view.findViewById(R.id.financialTips)
        financialTips.setOnClickListener { financialTips() }
    }

    private fun financialTips() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://en.wikipedia.org/wiki/2020%E2%80%932021_Indian_farmers%27_protest")
        startActivity(intent)

    }

    fun updateAmount(newTotal: Double){
        totalExpense = newTotal
        expenseTextView.text = "Total Expense: $totalExpense"
    }
}