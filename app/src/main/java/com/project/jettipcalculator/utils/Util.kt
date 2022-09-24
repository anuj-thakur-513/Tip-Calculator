package com.project.jettipcalculator.utils

// function to calculate tip amount
fun calculateTotalTip(totalBill: Double, tipPercentage: Int): Double {
    return if (totalBill > 1 && totalBill.toString()
            .isNotEmpty()
    ) (totalBill * tipPercentage) / 100 else 0.0
}

// function to calculate total bill per person
fun calculateTotalBillPerPerson(totalBill: Double, splitBy: Int, tipPercentage: Int): Double {
    val bill = totalBill + calculateTotalTip(totalBill = totalBill, tipPercentage = tipPercentage)
    return bill / splitBy
}