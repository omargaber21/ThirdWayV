package com.example.thirdwayvcalculatortask

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.singleope.*

class MainActivity : AppCompatActivity() {
    var operation=""
    var opeResult=0
    var numOfStep=0
    var redoSize=0
    lateinit var arrayOfOperations:ArrayList<String>
    lateinit var arrayOfOperands:ArrayList<Int>
    lateinit var gridList:ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arrayOfOperands= ArrayList()
        arrayOfOperations= ArrayList()
        gridList= ArrayList()
        //selectOperation() method for checking which operation selected
        selectOperation()

        equal_btn.setOnClickListener {
            if(operand_editText.length()>0&&operation!=""){
                calculate()
                redoSize=0
                val operand=operand_editText.text.toString()
                arrayOfOperations.add(operation)
                arrayOfOperands.add(operand.toInt())
                numOfStep =arrayOfOperands.size
                result.text=opeResult.toString()
                initGridView(operation,operand)
                operand_editText.text.clear()
                operation=""
                divide_btn.setBackgroundColor(Color.TRANSPARENT)
                plus_btn.setBackgroundColor(Color.TRANSPARENT)
                minus_btn.setBackgroundColor(Color.TRANSPARENT)
                multiply_btn.setBackgroundColor(Color.TRANSPARENT)
            }
        }
        undo_btn.setOnClickListener {
            if(numOfStep > 0){
                Undo()
                redoSize+=1
                numOfStep-=1
            }
        }
        redo_btn.setOnClickListener {
            if(redoSize >0){
                  Redo()
                numOfStep+=1
                redoSize-=1
                Log.w("myTag","numofstep $numOfStep array ${arrayOfOperands.size}")
            }
        }
        grid.setOnItemClickListener { parent, view, position, id ->
            val operand=gridList[position]
            Log.w("myTag","position $position")
        }

    }

    private fun initGridView(operation: String,operand:String) {
        gridList.add(operation+operand)
        val adapter=ArrayAdapter<String>(this,R.layout.singleope,R.id.textView2,gridList)
        grid.adapter=adapter
    }

    private fun calculate() {
        when(operation){
            "-"-> opeResult -= operand_editText.text.toString().toInt()
            "+"-> opeResult+=operand_editText.text.toString().toInt()
            "*"-> opeResult *= operand_editText.text.toString().toInt()
            "/"-> opeResult/=operand_editText.text.toString().toInt()
        }
    }
    private fun Undo(){
        val num:Int= arrayOfOperands[numOfStep-1]
        val operation=arrayOfOperations[numOfStep-1]
        var ope=""
        when(operation){
            "-"->{opeResult+=num
            ope="+"}
            "+"->{opeResult-=num
                ope="-"}
            "*"->{opeResult/=num
                ope="/"}
            "/"->{opeResult*=num
                ope="*"}

        }
        arrayOfOperations.add(ope)
        arrayOfOperands.add(num)
        Log.w("myTag","Undo num$num ope$ope")
        result.text=opeResult.toString()
    }
    private fun Redo(){
        val num:Int= arrayOfOperands[numOfStep]
        val operation=arrayOfOperations[numOfStep]
        var ope=""
        when(operation){
            "-"->{opeResult-=num
                ope="-"}
            "+"->{opeResult+=num
                ope="+"}
            "*"->{opeResult*=num
                ope="*"}
            "/"->{opeResult/=num
                ope="/"}

        }
        arrayOfOperations.add(ope)
        arrayOfOperands.add(num)
        Log.w("myTag","Redo num$num ope$ope")

        result.text=opeResult.toString()
    }
    private fun selectOperation() {
    divide_btn.setOnClickListener {
        operation="/"
        divide_btn.setBackgroundColor(Color.LTGRAY)
        plus_btn.setBackgroundColor(Color.TRANSPARENT)
        minus_btn.setBackgroundColor(Color.TRANSPARENT)
        multiply_btn.setBackgroundColor(Color.TRANSPARENT)
    }
        plus_btn.setOnClickListener {
            operation="+"
            divide_btn.setBackgroundColor(Color.TRANSPARENT)
            plus_btn.setBackgroundColor(Color.LTGRAY)
            minus_btn.setBackgroundColor(Color.TRANSPARENT)
            multiply_btn.setBackgroundColor(Color.TRANSPARENT)
        }
        minus_btn.setOnClickListener {
            operation="-"
            divide_btn.setBackgroundColor(Color.TRANSPARENT)
            plus_btn.setBackgroundColor(Color.TRANSPARENT)
            minus_btn.setBackgroundColor(Color.LTGRAY)
            multiply_btn.setBackgroundColor(Color.TRANSPARENT)
        }
        multiply_btn.setOnClickListener {
            operation="*"
            divide_btn.setBackgroundColor(Color.TRANSPARENT)
            plus_btn.setBackgroundColor(Color.TRANSPARENT)
            minus_btn.setBackgroundColor(Color.TRANSPARENT)
            multiply_btn.setBackgroundColor(Color.LTGRAY)
        }
    }
}