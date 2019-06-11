package com.learnprogramming.academy.calculatorapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var CalculatorScreen:EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CalculatorScreen = edtText_Calculation_Screen
//        CalculatorScreen?.keyListener = null
/* CalculatorScreen?.keyListener = null */
    }
}
