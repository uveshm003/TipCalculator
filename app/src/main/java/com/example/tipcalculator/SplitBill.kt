package com.example.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView

class SplitBill : AppCompatActivity() {
    private lateinit var etPerson: EditText
    private lateinit var etAmount: EditText
    private lateinit var splitAmount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_split_bill)

        etAmount = findViewById(R.id.etAmount)
        etPerson = findViewById(R.id.etPerson)
        splitAmount = findViewById(R.id.splitAmount)

        etPerson.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                splitBillAmount()
            }

        })

        etAmount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                splitBillAmount()
            }

        })

    }

    private fun splitBillAmount() {
        if (etPerson.text.isEmpty() || etAmount.text.isEmpty()){
            splitAmount.text = "00.00"
        }else {
            val person = etPerson.text.toString().toDouble()
            val amount = etAmount.text.toString().toDouble()
            val perPersonAmount  = amount / person
            splitAmount.text = "%.2f".format(perPersonAmount)
        }
    }
}