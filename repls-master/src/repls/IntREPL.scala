package repls

import repls.REPLFunctions.{isVariable, isSimplifyExpression}
import scala.collection.mutable.ArrayBuffer

class IntREPL extends REPLBase {
    type Base = Int
    override val replName: String = "IntREPL"
    var variablesMap : Map[String, String] = Map()
    override def readEval(command: String): String = {
        val elements = command.split("\\s") // split string based on whitespace
        handleInput(elements)
    }
    def handleInput (elements : Array[String]) : String ={
        if(isVariable(elements(0))) assignVariable(elements)
        else if(isSimplifyExpression(elements(0))) simplify(elements)
        else evaluate(elements)
    }
    def assignVariable(elements : Array[String]) : String = {
        var tmpEl :ArrayBuffer[String] = ArrayBuffer()
        for(element <- elements){
            if(isVariable(element)){
                if (variablesMap.contains(element)) tmpEl += variablesMap(element)
            }
            else tmpEl += element
        }
        val postFix : ArrayBuffer[String] = BuildPostfixNotation.build(tmpEl)
        variablesMap += (elements(0) -> ReversePolishInt.RPNToExpression(postFix).describe)
        elements(0) + " = " + variablesMap(elements(0))
    }
    def evaluate (elements : Array[String]) : String = {
        var tmpEl : ArrayBuffer[String] = ArrayBuffer()
        for(element <- elements) {
            if(isVariable(element)){
                if (variablesMap.contains(element)) tmpEl += variablesMap(element)
            }
            else tmpEl += element
        }
        val postFix : ArrayBuffer[String] = BuildPostfixNotation.build(tmpEl)
        ReversePolishInt.RPNToExpression(postFix).describe
    }
    def simplify(elements : Array[String]) :String = {
        var tmpEl : ArrayBuffer[String] = ArrayBuffer()
        for(element <- elements) {
            if(isVariable(element)){
                if (variablesMap.contains(element)) tmpEl += variablesMap(element)
                else tmpEl += element
            }
            else if(!isSimplifyExpression(element)) tmpEl += element
        }
        val posFix : ArrayBuffer[String] = BuildPostfixNotation.build(tmpEl)
        val tmpExp : Expression = ReversePolishInt.RPNToExpression(posFix)
        var f  = Simplify.simplifyInt(tmpExp)
        for(_ <- 0 until 10){
            f = Simplify.simplifyInt(f)
        }
        PrettyPrint.format(f).toString
    }
}