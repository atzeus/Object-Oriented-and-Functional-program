package repls

import repls.REPLFunctions.{isNegateNum, isNumber, isOperator, isSet, isVariable, stringToSeq}
import scala.collection.mutable.ArrayBuffer

object ReversePolishInt {
  def handleElement(initS : SStack[Expression], el : String) : SStack[Expression] = {
    var s = initS
    if(isOperator(el)) {
      val rhs = s.top
      s = s.pop
      val lhs = s.top
      s = s.pop
      val res = Operator(lhs, el, rhs)
      s.push(res)
    } else if(isNumber(el)) s.push(Constant(el.toInt))
    else if (isVariable(el)) s.push(Variable(el))
    else if(isNegateNum(el)) s.push(Negate(Constant(el.toInt)))
    else throw new Error("Unknown expression element " + el)
  }
  def RPNToExpression(expression : ArrayBuffer[String]) : Expression =
    expression.foldLeft(SStack[Expression]())(handleElement).top
}
object ReversePolishMultiset {
  def handleElement(initS : SStack[ExpressionMultiset], el : String) : SStack[ExpressionMultiset] = {
    var s = initS
    if(isOperator(el)) {
      val rhs = s.top
      s = s.pop
      val lhs = s.top
      s = s.pop
      val res = OperatorSet(lhs, el, rhs)
      s.push(res)
    } else if(isSet(el)) s.push(ConstantSet(MultiSet(stringToSeq(el))))
    else if (isVariable(el)) s.push(VariableSet(el))
    else throw new Error("Unknown expression element " + el)
  }
  def RPNToExpression(expression : ArrayBuffer[String]) : ExpressionMultiset =
    expression.foldLeft(SStack[ExpressionMultiset]())(handleElement).top
}