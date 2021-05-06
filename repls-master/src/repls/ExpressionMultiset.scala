package repls

import repls.REPLFunctions.operatorByNameSet

sealed abstract class ExpressionMultiset {
  def eval(): MultiSet[String]
  def describe : String = eval().toString
}
case class ConstantSet (set : MultiSet[String]) extends ExpressionMultiset {
  override def eval(): MultiSet[String] = set
  override def toString : String = set.toString
}
case class VariableSet (s: String) extends ExpressionMultiset {
  override def eval(): MultiSet[String] = MultiSet.empty[String]
  override def toString : String = s
}
case class OperatorSet(lhs: ExpressionMultiset, operatorName: String, rhs: ExpressionMultiset) extends ExpressionMultiset {
  override def eval(): MultiSet[String] = {
    val l = lhs.eval()
    val r = rhs.eval()
    operatorByNameSet(l, operatorName, r)
  }

  override def toString : String = "( " + lhs.toString + " " + operatorName + " " + rhs.toString + " )"
}
