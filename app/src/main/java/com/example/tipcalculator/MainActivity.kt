package com.example.tipcalculator


import android.animation.ArgbEvaluator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15

class MainActivity : AppCompatActivity() {

    private lateinit var etBaseAmount: EditText
    private lateinit var tipPercentage: TextView
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tipDescription: TextView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etBaseAmount = findViewById(R.id.etBaseAmount)
        tipPercentage = findViewById(R.id.tipPercentage)
        seekBarTip = findViewById(R.id.seekBarTip)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        tipDescription = findViewById(R.id.tipDescription)
        button = findViewById(R.id.button)

        seekBarTip.progress = INITIAL_TIP_PERCENT
        tipPercentage.text = "$INITIAL_TIP_PERCENT%"
        updateTipDescription(INITIAL_TIP_PERCENT)
        seekBarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG, "onProgressChanged $p1")
                tipPercentage.text = "$p1%"
                computeTipAndTotal()
                updateTipDescription(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })
        etBaseAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged $p0")
                computeTipAndTotal()
            }

        })
        button.setOnClickListener(View.OnClickListener{
            val intent = Intent(this,SplitBill::class.java)
            startActivity(intent)
        })

    }

    private fun updateTipDescription(progress: Int) {
        val description =  when (progress){
            in 0..9 -> "Poor"
            in 10..14 -> "Acceptable"
            in 15..19 -> "Good"
            in 20..24-> "Great"
            else -> "Amazing"
        }
        tipDescription.text = description

        val color = ArgbEvaluator().evaluate(
            progress.toFloat() / seekBarTip.max,
            ContextCompat.getColor(this, R.color.color_worst_tip),
            ContextCompat.getColor(this, R.color.color_best_tip)
        ) as Int
        tipDescription.setTextColor(color)
    }

    private fun computeTipAndTotal() {
        if (etBaseAmount.text.isEmpty()){
            tvTipAmount.text = "00.00"
            tvTotalAmount.text = "00.00"
        }else {
            val toDouble = etBaseAmount.text.toString().toDouble()
            val tipPercent = seekBarTip.progress
            val tipAmount = toDouble * tipPercent / 100
            val totalAmount = tipAmount + toDouble
            tvTipAmount.text = "%.2f".format(tipAmount)
            tvTotalAmount.text = "%.2f".format(totalAmount)
        }
    }
}