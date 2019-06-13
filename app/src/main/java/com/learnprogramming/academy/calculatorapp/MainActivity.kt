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
    private var FinalCalc: Double = 0.0
    private val TAG = "MainActivity"

    // Variables To Hold Operand Terms
    private var operand1: Double? = null
    private var operand2: Double = 0.0
    private var pendingOperation = ""
    private lateinit var NumButtons: ArrayList<Button> // Numerical Number Buttons
    private lateinit var OperationButtons: ArrayList<Button> // Operation Buttons
    private lateinit var SpecialButtons: ArrayList<Button> // Special Buttons
    private val CurrentOperations =
        arrayListOf<String>() // ArrayList That Stores The Operations That Need To Be Performed
    private var OperationsMap = mapOf(
        "Cancel" to "C",
        "Division" to "/",
        "Multiplication" to "x",
        "Delete" to "DEL",
        "Minus" to "-",
        "Add" to "+",
        "Brackets" to "()",
        "Equals" to "="
    )
    //OperationsMap = mapOf("Cancel" to "C","Division" to "/","Multiplication" to "x","Delete" to "DEL","Minus" to "-","Add" to "+","Brackets" to "()","Equals" to "=")

    private fun SpecialOnClickListenr(SpecialOperation: String): View.OnClickListener {

        val _SpecialOnClickListner = View.OnClickListener { v ->
            val SpecialButton = v as Button
            var ButtonText: String = SpecialOperation
            val IndexOfLastOperator:Int = CalculatorScreen.text.indexOfLast { x -> x == '+' || x == '-' || x == '*' || x == '/' }
            if (SpecialButton.text.toString() == ".") {
                Log.d(TAG, "SpecialButton Text = ${ButtonText}")
                if (CalculatorScreen.text.lastIndex>-1 && CalculatorScreen.text[CalculatorScreen.text.lastIndex].isDigit()) {
                    if ((CalculatorScreen.text.indexOfLast { x -> x == '.' }) == -1 && (IndexOfLastOperator == -1)) {
                        CalculatorScreen.append(ButtonText)
                    } else {
                        if ((CalculatorScreen.text.indexOfLast { x -> x == '.' }) < (IndexOfLastOperator) && IndexOfLastOperator!=-1) {
                            CalculatorScreen.append(ButtonText)
                        }
                    }
                }

                //Implement Decimal Point
//
//                if((CalculatorScreen.text.find { x-> x =='.' })!='.')
//                {
//                    CalculatorScreen.text.append(ButtonText)
//                }
//                    if (CalculatorScreen.text.lastIndex == -1) // You Are Dealing With A Fraction Decimal Point Here
//                    {
//                        CalculatorScreen.text.append("0" + ButtonText)
//                    }
            }
            if (SpecialButton.text.toString() == "+/-") {
                //TODO: Implement Special Signed Number Behaviour
            }
        }

        return _SpecialOnClickListner
    }

    private fun OperationsOnClickListner(Operation: String): View.OnClickListener {
        //OperationsMap.get(Operation)
        val CurrentOnClickListner = View.OnClickListener { v ->
            val OperationButton = v as Button
            var ButtonText: String = Operation

            if (OperationButton.text.toString() != null) {
                //ButtonText = OperationButton.text.toString()
                Log.d(TAG, "ButtonText Is ${ButtonText}")
            }
            //val ButtonText:String = OperationsMap.get(OperationButton.text.toString())
            if (ButtonText != "C" || ButtonText != "DEL" || ButtonText != "=") {
                if (!CalculatorScreen.text.isEmpty()) {
                    val PreviousCharacter = CalculatorScreen.text[CalculatorScreen.text.lastIndex]
                    if (PreviousCharacter.isLetterOrDigit() && FinalCalc == 0.0) {
                        CalculatorScreen.append(ButtonText)
                    } else {
                        CalculatorScreen.text.clear()
                        CalculatorScreen.text.append(FinalCalc.toString() + ButtonText)
                        FinalCalc = 0.0
                    }
                }
                // Only Allow An Operation To Be Added To The Screen If The Previous Character Is Not An Operation

            }


            if (ButtonText == "C") {
                if (!CalculatorScreen.text.isEmpty()) {
                    CalculatorScreen.text.clear()
                }
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
            //TODO: Implement Support For Signed Sensitive Numbers Eg -45+27
            if (ButtonText == "=") {
                var Numbers: String = ""
                Numbers = CalculatorScreen.text.toString()
                var NewNumber = Numbers.split("+", "-", "*", "/", " ", "(", ")", "/0")
                //Numbers.removeSurrounding("0","1","2","3","4","5","6","7","8","9")
                //           var NewOperands = Numbers.split(" ","0","1","2","3","4","5","6","7","8","9","/0",ignoreCase = true)
                var NewOperands = (Numbers.filter { x -> !x.isLetterOrDigit() && x != '.' }).toList()
                var Result: Double = 0.0
                var Number_Of_Operations: Int = NewOperands.size
                var isInvalidCalculation: Boolean = true

                // Check If A Calculation Can Be Performed
                // We NEED At Least 1 Operation And 2 Operands Or Terms
                if (Number_Of_Operations <= NewNumber.size - 1 && NewNumber.isNotEmpty()) {
                    isInvalidCalculation = false
                    Log.d(TAG, " Calculation Is Valid = false")
                }
                if (!isInvalidCalculation) {
                    for (x in 0..NewNumber.size - 1) {
                        if (x == 0) {
                            Result = NewNumber.get(x).toDouble()
                            Log.d(TAG, " Result_1 = ${Result}")
                        } else {
                            // ******** ASCII "*" = 42, "+" = 43, "-" = 45, "/" = 47, "=" = 61
                            val ASCII_Operation = NewOperands.get(x - 1).toInt()
                            when (ASCII_Operation) {
                                // **** Multiplaction "*"
                                42 -> {
                                    Result = Result * (NewNumber.get(x)).toDouble()
                                }
                                // **** Add "+"
                                43 -> {
                                    Result = Result + (NewNumber.get(x)).toDouble()
                                }
                                // **** Subtract "-"
                                45 -> {
                                    Result = Result - (NewNumber.get(x)).toDouble()
                                }
                                // **** Divide "/"
                                47 -> {
                                    Result = Result / (NewNumber.get(x)).toDouble()
                                }
                            }
                        }

                    }
                    FinalCalc = Result
                    CalculatorScreen.text.clear()
                    CalculatorScreen.text.append("= ${FinalCalc}")
                }



                Log.d(TAG, "NewNumber Is ${NewNumber}")
                Log.d(TAG, "NewOperands Is ${NewOperands}")

            }


        }
        return CurrentOnClickListner

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CalculatorScreen = edtText_Calculation_Screen
        //OperationsMap = mapOf("Cancel" to "C","Division" to "/","Multiplication" to "x","Delete" to "DEL","Minus" to "-","Add" to "+","Brackets" to "()","Equals" to "=")
        //********** NUMPAD Button Declarations ***************
        NumButtons = ArrayList<Button>()
        for (x in 0..9) {
            val prefix_Button = "btn_Num"
            var button_ID = prefix_Button + "_$x"
            val Resource_ID = resources.getIdentifier(button_ID, "id", packageName)
            val Num_Button = findViewById<Button>(Resource_ID)

            // Establish onClickListners For All The Numpad Buttons
            val NumPadListner = View.OnClickListener { v ->
                val numButton =
                    v as Button // When the button is clicked a reference will be returned that needs to be casted as a button
                if (FinalCalc == 0.0) {
                    CalculatorScreen.append(numButton.text) // Display The Newly Added Number In The CalculatorScreen
                } else {
                    CalculatorScreen.text.clear()
                    CalculatorScreen.text.append(numButton.text)
                    FinalCalc = 0.0
                }
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

        // ************ SPECIAL Button Declarations  ****************
        lateinit var specialButton: Button
        specialButton = findViewById(R.id.btn_Special_Dot)
        val Prefix_Button_Operation = "btn_Special_"
        var Special_Resource_ID = Prefix_Button_Operation + "Dot"


        // ************ OPERATION Button Declarations ***************
        OperationButtons = ArrayList<Button>()
        SpecialButtons = ArrayList<Button>()
        val PrefixButtonOperations =
            listOf<String>("Cancel", "Division", "Multiplication", "Delete", "Minus", "Add", "Brackets", "Equals")
        val PrefixSpecialButton = listOf<String>("Dot", "Plus_Minus")
        for (x in PrefixButtonOperations) {
            val Prefix_Button_Operation = "btn_Operation_"
            val Operations_Button_ID = Prefix_Button_Operation + x
            val Resource_ID = resources.getIdentifier(Operations_Button_ID, "id", packageName)
            val operationButton = findViewById<Button>(Resource_ID)
            operationButton.setOnClickListener(OperationsOnClickListner(operationButton.text.toString()))
            OperationButtons.add(operationButton)
        }
        for (x in PrefixSpecialButton) {
            val Prefix_Special_Button_Operation = "btn_Special_"
            val Special_Button_ID = Prefix_Special_Button_Operation + x
            val Resource_ID = resources.getIdentifier(Special_Resource_ID, "id", packageName)
            val specialButton = findViewById<Button>(Resource_ID)
            specialButton.setOnClickListener(SpecialOnClickListenr(specialButton.text.toString()))
            SpecialButtons.add(specialButton)
        }


//        CalculatorScreen?.keyListener = null
/* CalculatorScreen?.keyListener = null */
    }
}
