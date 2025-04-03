package com.zybooks.assignment06

import android.icu.util.Currency


data class Expense(
    val name: String,
    val amount: Double,
    val date: String,
    val currency : Currency,
    val convertedCost: Double
)
