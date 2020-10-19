package com.example.thirdwayvcalculatortask

import android.graphics.Color
import android.media.VolumeShaper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.singleope.*

class MainActivity : AppCompatActivity() {
    var operation = ""
    var opeResult = 0
    var numOfStep = 0
    var redoSize = 0
    lateinit var arrayOfOperations: ArrayList<OperationModel>
    lateinit var gridViewStrings: ArrayList<String>
    lateinit var adapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gridViewStrings = ArrayList<String>()
        arrayOfOperations = ArrayList()
        //selectOperation() method for checking which operation selected
        selectOperation()
        clear.setOnClickListener {
            clear()
        }
        equal_btn.setOnClickListener {
            if (operand_editText.length() > 0 && operation != "") {
                calculate()
                redoSize = 0
                val operand = operand_editText.text.toString()
                val operationModel = OperationModel(operation, operand.toInt())
                arrayOfOperations.add(operationModel)
                numOfStep = arrayOfOperations.size
                result.text = opeResult.toString()
                initGridView(operationModel)
                operand_editText.text.clear()
                operation = ""
                divide_btn.setBackgroundColor(Color.TRANSPARENT)
                plus_btn.setBackgroundColor(Color.TRANSPARENT)
                minus_btn.setBackgroundColor(Color.TRANSPARENT)
                multiply_btn.setBackgroundColor(Color.TRANSPARENT)
            }
        }
        undo_btn.setOnClickListener {
            if (numOfStep > 0) {
                Undo()
                redoSize += 1
                numOfStep -= 1
            }
        }
        redo_btn.setOnClickListener {
            if (redoSize > 0) {
                Redo()
                numOfStep += 1
                redoSize -= 1
            }
        }
        grid.setOnItemClickListener { parent, view, position, id ->
            while (position <= numOfStep - 1) {
                Undo()
                redoSize += 1
                numOfStep -= 1
                break
            }
            while (position > numOfStep) {
                Redo()
                numOfStep += 1
                redoSize -= 1
                break
            }
            Log.w("myTag", "position $position step $numOfStep")
        }

    }

    private fun clear() {
        operation = ""
        opeResult = 0
        numOfStep = 0
        redoSize = 0
        arrayOfOperations.clear()
        gridViewStrings.clear()
        adapter.notifyDataSetChanged()
        operand_editText.text.clear()
        result.text = "0"
    }

    private fun initGridView(operation: OperationModel) {
        gridViewStrings.add(operation.Operation + operation.Operand.toString())
        adapter = ArrayAdapter<String>(this, R.layout.singleope, R.id.textView2, gridViewStrings)
        grid.adapter = adapter
    }

    private fun calculate() {
        when (operation) {
            "-" -> opeResult -= operand_editText.text.toString().toInt()
            "+" -> opeResult += operand_editText.text.toString().toInt()
            "*" -> opeResult *= operand_editText.text.toString().toInt()
            "/" -> opeResult /= operand_editText.text.toString().toInt()
        }
    }

    private fun Undo() {
        val num: Int = arrayOfOperations[numOfStep - 1].Operand
        val operation = arrayOfOperations[numOfStep - 1].Operation
        var ope = ""
        when (operation) {
            "-" -> {
                opeResult += num
                ope = "+"
            }
            "+" -> {
                opeResult -= num
                ope = "-"
            }
            "*" -> {
                opeResult /= num
                ope = "/"
            }
            "/" -> {
                opeResult *= num
                ope = "*"
            }

        }
        val operationModel = OperationModel(ope, num)
        arrayOfOperations.add(operationModel)
        initGridView(operationModel)
        Log.w("myTag", "Undo num$num ope$ope")
        result.text = opeResult.toString()
    }

    private fun Redo() {
        val num: Int = arrayOfOperations[numOfStep].Operand
        val operation = arrayOfOperations[numOfStep].Operation
        var ope = ""
        when (operation) {
            "-" -> {
                opeResult -= num
                ope = "-"
            }
            "+" -> {
                opeResult += num
                ope = "+"
            }
            "*" -> {
                opeResult *= num
                ope = "*"
            }
            "/" -> {
                opeResult /= num
                ope = "/"
            }

        }
        val operationModel = OperationModel(ope, num)
        arrayOfOperations.add(operationModel)
        initGridView(operationModel)
        Log.w("myTag", "Redo num$num ope$ope")

        result.text = opeResult.toString()
    }

    private fun selectOperation() {
        divide_btn.setOnClickListener {
            operation = "/"
            divide_btn.setBackgroundColor(Color.LTGRAY)
            plus_btn.setBackgroundColor(Color.TRANSPARENT)
            minus_btn.setBackgroundColor(Color.TRANSPARENT)
            multiply_btn.setBackgroundColor(Color.TRANSPARENT)
        }
        plus_btn.setOnClickListener {
            operation = "+"
            divide_btn.setBackgroundColor(Color.TRANSPARENT)
            plus_btn.setBackgroundColor(Color.LTGRAY)
            minus_btn.setBackgroundColor(Color.TRANSPARENT)
            multiply_btn.setBackgroundColor(Color.TRANSPARENT)
        }
        minus_btn.setOnClickListener {
            operation = "-"
            divide_btn.setBackgroundColor(Color.TRANSPARENT)
            plus_btn.setBackgroundColor(Color.TRANSPARENT)
            minus_btn.setBackgroundColor(Color.LTGRAY)
            multiply_btn.setBackgroundColor(Color.TRANSPARENT)
        }
        multiply_btn.setOnClickListener {
            operation = "*"
            divide_btn.setBackgroundColor(Color.TRANSPARENT)
            plus_btn.setBackgroundColor(Color.TRANSPARENT)
            minus_btn.setBackgroundColor(Color.TRANSPARENT)
            multiply_btn.setBackgroundColor(Color.LTGRAY)
        }
    }
}