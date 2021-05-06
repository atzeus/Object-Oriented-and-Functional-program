package repls

import repls.Element.elem

object PrettyPrint {
  private val opGroups =
    Array(
      Set("+", "-"),
      Set("*")
    )
  private val precedence = {
    val assocs =
      for {
        i <- opGroups.indices
        op <- opGroups(i)
      } yield op -> i
    Map() ++ assocs
  }
  private def format(expression: Expression, enclPrec: Int): Element = {
    expression match {
      case Variable(name) => elem(name)
      case Constant(num) => elem(num.toString)
      case Operator(left, op, right) =>
        val opPrec = precedence(op)
        val l = format(left, opPrec)
        val r = format(right, opPrec)
        val oper = l beside elem(" "+ op +" ") beside r
        if (enclPrec <= opPrec) oper
        else elem("( ") beside oper beside elem(" )")
    }
  }

  private  def formatMultiset(expressionMultiset: ExpressionMultiset, enclPrec : Int): Element = {
    expressionMultiset match {
      case VariableSet(name) => elem(name)
      case ConstantSet(set) => elem(set.toString)
      case OperatorSet(left, op, right) =>
        val opPrec = precedence(op)
        val l = formatMultiset(left, opPrec)
        val r = formatMultiset(right, opPrec)
        val oper = l beside elem(" "+ op +" ") beside r
        if (enclPrec <= opPrec) oper
        else elem("( ") beside oper beside elem(" )")
    }
  }

  def format(e: Expression): Element = format(e, 0)
  def formatMultiset(e: ExpressionMultiset): Element = formatMultiset(e,0)
}
