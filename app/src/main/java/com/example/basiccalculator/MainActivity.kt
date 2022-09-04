package com.example.basiccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

// Set tag for debugging purposes later on
const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    // Setup variables
    private var currentNumber: String = "0"
    private var previousNumber: String = "0"
    private lateinit var display: TextView
    private var currentOperation: String = "none"
    private var resetOnInput = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set reference to views
        display = findViewById<TextView>(R.id.textView)
        val number1 = findViewById<Button>(R.id.number1)
        val number2 = findViewById<Button>(R.id.number2)
        val number3 = findViewById<Button>(R.id.number3)
        val number4 = findViewById<Button>(R.id.number4)
        val number5 = findViewById<Button>(R.id.number5)
        val number6 = findViewById<Button>(R.id.number6)
        val number7 = findViewById<Button>(R.id.number7)
        val number8 = findViewById<Button>(R.id.number8)
        val number9 = findViewById<Button>(R.id.number9)
        val number0 = findViewById<Button>(R.id.number0)
        val addButton = findViewById<Button>(R.id.funcAdd)
        val subtractButton = findViewById<Button>(R.id.funcSubtract)
        val multiplyButton = findViewById<Button>(R.id.funcMultiply)
        val divideButton = findViewById<Button>(R.id.funcDivide)
        val clearButton = findViewById<Button>(R.id.funcClear)
        val equalsButton = findViewById<Button>(R.id.funcEquals)
        val dotButton = findViewById<Button>(R.id.dotButton)
        val backspaceButton = findViewById<Button>(R.id.backspaceButton)

        // Set click listeners for every button
        number1.setOnClickListener { inputPressed("1") }
        number2.setOnClickListener { inputPressed("2") }
        number3.setOnClickListener { inputPressed("3") }
        number4.setOnClickListener { inputPressed("4") }
        number5.setOnClickListener { inputPressed("5") }
        number6.setOnClickListener { inputPressed("6") }
        number7.setOnClickListener { inputPressed("7") }
        number8.setOnClickListener { inputPressed("8") }
        number9.setOnClickListener { inputPressed("9") }
        number0.setOnClickListener { inputPressed("0") }
        dotButton.setOnClickListener { inputPressed(".") }
        backspaceButton.setOnClickListener { backspace() }
        clearButton.setOnClickListener { clearDisplay() }
        addButton.setOnClickListener { doSomething("add") }
        subtractButton.setOnClickListener { doSomething("subtract") }
        multiplyButton.setOnClickListener { doSomething("multiply") }
        divideButton.setOnClickListener { doSomething("divide") }
        equalsButton.setOnClickListener { equalsFunction() }

        // Call update display to init the textview
        updateDisplay()
    }

    // Check and see if resetOnInput flag is set and cleardisplay if it is
    private fun resetOnInput(){
        if(resetOnInput) clearDisplay()
        resetOnInput = false
    }

    // Function to handle the backspace button
    private fun backspace() {
        // Check if the equalsfunction just ran and reset display on new input
        resetOnInput()
        if(currentNumber.length > 0){
            currentNumber = currentNumber.take(currentNumber.length - 1)
            updateDisplay()
            Log.d(TAG, "backspace hit")
        }
    }

    // Function to handle the equals button
    private fun equalsFunction() {
        // If the currentNumber is blank set it to 0
        if(currentNumber =="") currentNumber = "0"

        // Select an operation and do the math
        when(currentOperation){
            "none" -> return
            "add" -> {
                // Do the math
                currentNumber = (previousNumber.toDouble() + currentNumber.toDouble()).toString()
                // Check if decimal exists, if not chop it off
                chopDecimal()
            }

            "subtract" -> {
                // Do the math
                currentNumber = (previousNumber.toDouble() - currentNumber.toDouble()).toString()
                // Check if decimal exists, if not chop it off
                chopDecimal()
            }

            "multiply" -> {
                // Do the math
                currentNumber = (previousNumber.toDouble() * currentNumber.toDouble()).toString()
                // Check if decimal exists, if not chop it off
                chopDecimal()
            }
            "divide" -> {
                // Do the math
                currentNumber = (previousNumber.toDouble() / currentNumber.toDouble()).toString()
                // Check if decimal exists, if not chop it off
                chopDecimal()
            }
        }

        // Update display now with the new number
        updateDisplay()

        // Set operation to none so equals button cant be hit again
        currentOperation = "none"

        // Set flag to reset the currentNumber when any input key is pressed to start a new calculation
        resetOnInput = true
    }

    // Check if our currentNumber has a decimal and chop it off if its .0
    private fun chopDecimal() {
        // Check for a remainder to see if number has significant decimal
        if (currentNumber.toDouble().rem(1).equals(0.0)) {
            // If the number ends in .0 then take the string before the decimal only
            currentNumber = currentNumber.substringBefore(".")
        }
    }

    // Function used by onClickListeners to specify what the math function will be if the equals button is pressed
    private fun doSomething(something: String) {
        currentOperation = something
        if(currentNumber == "") currentNumber = "0"
        previousNumber = currentNumber
        clearDisplay()
    }

    // Function to handle all the numbers inputs and the decimal point
    private fun inputPressed(input: String) {
        // Check if the equals function just ran and reset display on new input
        resetOnInput()

        // Check if currentNumber has a decimal in it already, ignore new decimal if it does
        if(input == "." && "." in currentNumber ) return

        // Check if currentNumber is ONLY a decimal and add a 0 in front if it is
        if(currentNumber == ".") currentNumber = "0." + input

        // If currentNumber is 0 just replace it with the current input.
        else if(currentNumber == "0") currentNumber = input

        // Append input string to currentNumber
        else currentNumber += input

        // Update display now that currentNumber changed
        updateDisplay()
    }

    // Resets the current number to 0 and calls updateDisplay() for UI action
    private fun clearDisplay(){
        // Reset display to 0
        currentNumber = "0"
        updateDisplay()
        // Reset the resetOnInput flag if necessary
        resetOnInput = false
    }

    // UI call to update the display with the current number
    private fun updateDisplay(){
        display.text = currentNumber
    }
}