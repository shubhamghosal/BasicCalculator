package com.example.basiccalculator

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvInput: TextView
    private lateinit var buttonGrid: GridLayout

    private var input = ""
    private var firstNumber = 0.0
    private var operator = ""
    private var isOperatorPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
        buttonGrid = findViewById(R.id.buttonGrid)

        createButtons()
    }

    private fun createButtons() {
        val buttons = listOf(
            "C", "/", "*", "-",
            "7", "8", "9", "+",
            "4", "5", "6", "=",
            "1", "2", "3", ".",
            "0"
        )

        for (text in buttons) {
            val button = Button(this).apply {
                this.text = text
                this.textSize = 22f
                this.setTextColor(resources.getColor(android.R.color.white))
                this.setBackgroundColor(resources.getColor(R.color.teal_700))
                this.setPadding(0, 30, 0, 30)
                this.layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = GridLayout.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    setMargins(8, 8, 8, 8)
                }
                this.setBackgroundResource(R.drawable.gate_button_style)
                setOnClickListener { handleButtonClick(text) }
            }

            buttonGrid.addView(button)
        }
    }


    private fun handleButtonClick(value: String) {
        when (value) {
            "C" -> {
                input = ""
                firstNumber = 0.0
                operator = ""
                isOperatorPressed = false
                tvInput.text = "0"
            }

            "=" -> {
                if (isOperatorPressed && input.isNotEmpty()) {
                    val secondNumber = input.toDouble()
                    val result = when (operator) {
                        "+" -> firstNumber + secondNumber
                        "-" -> firstNumber - secondNumber
                        "*" -> firstNumber * secondNumber
                        "/" -> if (secondNumber != 0.0) firstNumber / secondNumber else Double.NaN
                        else -> 0.0
                    }
                    input = result.toString()
                    tvInput.text = input
                    isOperatorPressed = false
                }
            }

            "+", "-", "*", "/" -> {
                if (input.isNotEmpty()) {
                    firstNumber = input.toDouble()
                    operator = value
                    isOperatorPressed = true
                    input = ""
                }
            }

            else -> {
                // Append number or decimal
                if (value == "." && input.contains(".")) return
                input += value
                tvInput.text = input
            }
        }
    }

}
