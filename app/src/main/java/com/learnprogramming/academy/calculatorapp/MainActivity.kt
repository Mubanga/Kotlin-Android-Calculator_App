package com.learnprogramming.academy.calculatorapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
    private lateinit var OperationButtons:ArrayList<Button> // Operation Buttons
    private val CurrentOperations = arrayListOf<String>() // ArrayList That Stores The Operations That Need To Be Performed
    private var OperationsMap = mapOf("Cancel" to "C","Division" to "/","Multiplication" to "x","Delete" to "DEL","Minus" to "-","Add" to "+","Brackets" to "()","Equals" to "=")
    //OperationsMap = mapOf("Cancel" to "C","Division" to "/","Multiplication" to "x","Delete" to "DEL","Minus" to "-","Add" to "+","Brackets" to "()","Equals" to "=")


    private fun OperationsOnClickListner(Operation : String) : View.OnClickListener
    {
        //OperationsMap.get(Operation)
        val CurrentOnClickListner = View.OnClickListener { v->
            val OperationButton = v as Button
            var ButtonText:String = Operation

            if(OperationButton.text.toString()!=null)
            {
                //ButtonText = OperationButton.text.toString()
                Log.d("MainActivity","ButtonText Is ${ButtonText}")
            }
            //val ButtonText:String = OperationsMap.get(OperationButton.text.toString())
            if(ButtonText!="C"||ButtonText!="DEL"||ButtonText!="=")
            {
                CalculatorScreen.append(ButtonText)
            }

                //TODO: Implement Delete and Equals OnClickListners
                if (ButtonText == "C") {
                    CalculatorScreen.text.clear()
                }
                if (ButtonText == "DEL") {
                    if (CalculatorScreen.text.lastIndex > 0) {
                        CalculatorScreen.text.delete(
                            CalculatorScreen.text.lastIndex,
                            CalculatorScreen.text.lastIndex + 1
                        )
                    } else {
                        CalculatorScreen.text.clear()
                    }
                }
            if(ButtonText == "=")
            {
                var Numbers:String = ""
                Numbers = CalculatorScreen.text.toString()
                var NewNumber = Numbers.split("+","-","*","/"," ","/0")
                //Numbers.removeSurrounding("0","1","2","3","4","5","6","7","8","9")
     //           var NewOperands = Numbers.split(" ","0","1","2","3","4","5","6","7","8","9","/0",ignoreCase = true)
                var NewOperands = Numbers.filter { x -> !x.isLetterOrDigit() }

                Log.d("MainActivity","NewNumber Is ${NewNumber}")
                Log.d("MainActivity","NewOperands Is ${NewOperands}")

            }


        }
        return CurrentOnClickListner

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CalculatorScreen = edtText_Calculation_Screen
        //OperationsMap = mapOf("Cancel" to "C","Division" to "/","Multiplication" to "x","Delete" to "DEL","Minus" to "-","Add" to "+","Brackets" to "()","Equals" to "=")
        //********** Numpad Button Declarations ***************
        NumButtons = ArrayList<Button>()
        for(x in 0..9)
        {
            val prefix_Button = "btn_Num"
            var button_ID = prefix_Button + "_$x"
            val Resource_ID = resources.getIdentifier(button_ID,"id",packageName)
            val Num_Button = findViewById<Button>(Resource_ID)

            // Establish onClickListners For All The Numpad Buttons
            val NumPadListner = View.OnClickListener { v ->
                val numButton = v as Button // When the button is clicked a reference will be returned that needs to be casted as a button
                CalculatorScreen.append(numButton.text) // Display The Newly Added Number In The CalculatorScreen
            }
            Num_Button.setOnClickListener(NumPadListner)
            NumButtons.add(Num_Button)
        }

        // ************ OPERATION OnClickListners ******************
//        val OperationClearListner = View.OnClickListener { v ->
//            val operationButton = v as Button
//            CalculatorScreen.text.clear() // Clear Calculator Screen And All Pending Operation Interactions
//            // TODO: Clear Pending Operand Terms And Operations *****
//        }
//
//        // ***** ADDITION *******
//        val OperationAddListner = View.OnClickListener { v ->
//            val operationButton = v as Button
//            CalculatorScreen.append("+")
//        }
//        // ***** DIVISION *******
//        val OperationDivisionListner = View.OnClickListener { v ->
//            val operationButton = v as Button
//            CalculatorScreen.append("/")
//        }

//        // ***** DIVISION *******
//        val OperationDivisionListner = View.OnClickListener { v ->
//            val operationButton = v as Button
//            CalculatorScreen.append("/")
//        }


        // ************ OPERATION Button Declarations ***************
        OperationButtons = ArrayList<Button>()
        val PrefixButtonOperations = listOf<String>("Cancel","Division","Multiplication","Delete","Minus","Add","Brackets","Equals")
        for(x in PrefixButtonOperations)
        {
            val Prefix_Button_Operation = "btn_Operation_"
            val Operations_Button_ID = Prefix_Button_Operation + x
            val Resource_ID = resources.getIdentifier(Operations_Button_ID,"id",packageName)
            val operationButton = findViewById<Button>(Resource_ID)
            operationButton.setOnClickListener(OperationsOnClickListner(operationButton.text.toString()))
            OperationButtons.add(operationButton)
        }



//        CalculatorScreen?.keyListener = null
/* CalculatorScreen?.keyListener = null */
    }
}
