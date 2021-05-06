package repls

import repls.REPLFunctions.{isSimplifyExpression, isVariable}
import scala.collection.mutable.ArrayBuffer

class MultiSetREPL extends REPLBase {
    override type Base = MultiSet[String]
    override val replName: String = "MultisetREPL"
    var variablesMapMultiset : Map[String, String] = Map()
    override def readEval(command: String): String = {
        val elements = command.split("\\s")
        handleInputMultiset(elements)
    }
    def handleInputMultiset (elements: Array[String]) : String = {
        if(isVariable(elements(0))) assignVariableMultiset(elements)
        else if(isSimplifyExpression(elements(0))) simplifyMultiset(elements)
        else evaluateMultiset(elements)
    }
    def assignVariableMultiset (elements : Array[String]): String = {
        var tmpEl : ArrayBuffer[String] = ArrayBuffer()
        for(element <- elements){
            if(isVariable(element)){
                if (variablesMapMultiset.contains(element)) tmpEl += variablesMapMultiset(element)
            }
            else tmpEl += element
        }
        val postFix : ArrayBuffer[String] = BuildPostfixNotation.build(tmpEl)
        variablesMapMultiset += (elements(0) -> ReversePolishMultiset.RPNToExpression(postFix).describe)
        elements(0) + " = " + variablesMapMultiset(elements(0))
    }
    def evaluateMultiset(elements : Array[String]) : String = {
        var tmpEl : ArrayBuffer[String] = ArrayBuffer()
        for(element <- elements) {
            if(isVariable(element)){
                if (variablesMapMultiset.contains(element)) tmpEl += variablesMapMultiset(element)
            }
            else tmpEl += element
        }
        val postFix : ArrayBuffer[String] = BuildPostfixNotation.build(tmpEl)
        ReversePolishMultiset.RPNToExpression(postFix).describe
    }
    def simplifyMultiset(elements : Array[String]) : String = {
        var tmpEl : ArrayBuffer[String] = ArrayBuffer()
        for(element <- elements) {
            if(isVariable(element)){
                if (variablesMapMultiset.contains(element)) tmpEl += variablesMapMultiset(element)
                else tmpEl += element
            }
            else if(!isSimplifyExpression(element)) tmpEl += element
        }
        val posFix : ArrayBuffer[String] = BuildPostfixNotation.build(tmpEl)
        val tmpExp : ExpressionMultiset = ReversePolishMultiset.RPNToExpression(posFix)
        var f  = Simplify.simplifyMultiset(tmpExp)
        for(_ <- 0 until 10){
            f = Simplify.simplifyMultiset(f)
        }
        PrettyPrint.formatMultiset(f).toString
    }
}