package com.learnprogramming.academy.calculatorapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var CalculatorScreen: EditText
    private var FinalCalc : Double = 0.0

    // Variables To Hold Operand Terms
    private var operand1:Double? = null
    private var operand2:Double = 0.0
    private var pendingOperation = ""
    private lateinit var NumButtons:ArrayList<Button> // Numerical Number Buttons
    private lateinit var button1:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CalculatorScreen = edtText_Calculation_Screen

        // Numpad Button Declarations
        NumButtons = ArrayList<Button>()
        for(x in 0..9)
        {
            val prefix_Button = "btn_Num"
            var button_ID = prefix_Button + "_$x"
            val Resource_ID = resources.getIdentifier(button_ID,"id",packageName)
            val Num_Button = findViewById<Button>(Resource_ID)

            // Establish onClickListners For All The Numpad Buttons
            NumButtons.add(Num_Button)

        }
//        CalculatorScreen?.keyListener = null
/* CalculatorScreen?.keyListener = null */
    }
}
