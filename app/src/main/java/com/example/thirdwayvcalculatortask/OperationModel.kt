package com.example.thirdwayvcalculatortask

class OperationModel {
    lateinit var Operation:String
    var Operand:Int=0

    constructor(Operation: String, Operand: Int) {
        this.Operation = Operation
        this.Operand = Operand
    }
}