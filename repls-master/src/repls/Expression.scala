package repls

import repls.REPLFunctions.operatorByName

sealed abstract class Expression {
  def eval() : Int
  def describe : String = eval().toString
}
case class Constant(n : Int) extends Expression {
  override def eval(): Int = n
  override def toString : String = n.toString
}
case class Variable (s: String) extends Expression {
  override def toString : String = s
  override def eval(): Int = 0
}
case class Negate(arg :Expression) extends Expression {
  override def eval(): Int = arg.eval()
}
case class Operator(lhs : Expression, operatorName : String,rhs : Expression ) extends  Expression {
  override def eval(): Int = {
    val l = lhs.eval()
    val r = rhs.eval()
    operatorByName(l, operatorName, r)
  }
  override def toString : String = "( " + lhs.toString+ " " + operatorName + " " + rhs.toString + " )"
}
