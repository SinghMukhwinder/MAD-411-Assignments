package com.zybooks.assignment06.models

data class Expense(
    val name: String,
    val amount: Double,
    val date: String,
    val currency : String,
    val convertedCost: Double
)
