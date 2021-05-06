package repls

import repls.REPLFunctions.{operatorByName, operatorByNameSet}

object Simplify {
  def simplifyInt(expression: Expression) : Expression = {
    expression match {
      case Operator(Operator(a, "*", b),"+", Operator(c, "*", d)) if a==c =>
        simplifyInt(Operator(simplifyInt(a), "*", simplifyInt(Operator(simplifyInt(b), "+", simplifyInt(d)))))
      case Operator(Operator(a, "*", b),"+", Operator(c, "*", d)) if b==c =>
        simplifyInt(Operator(simplifyInt(b), "*", simplifyInt(Operator(simplifyInt(a), "+", simplifyInt(d)))))
      case Operator(Operator(a, "*", b),"+", Operator(c, "*", d)) if a==d =>
        simplifyInt(Operator(simplifyInt(a), "*", simplifyInt(Operator(simplifyInt(b), "+", simplifyInt(c)))))
      case Operator(Operator(a, "*", b),"+", Operator(c, "*", d)) if b==d =>
        simplifyInt(Operator(simplifyInt(b), "*", simplifyInt(Operator(simplifyInt(a), "+", simplifyInt(c)))))

      case Operator(e, "+", Constant(0)) => simplifyInt(e)
      case Operator(Constant(0), "+", e) => simplifyInt(e)
      case Operator(Constant(1), "*", e) => simplifyInt(e)
      case Operator(e, "*", Constant(1)) => simplifyInt(e)
      case Operator(_, "*", Constant(0)) => simplifyInt(Constant(0))
      case Operator(Constant(0), "*", _) => simplifyInt(Constant(0))
      case Operator(e, "-", q) if e == q => simplifyInt(Constant(0))
      case Negate(Negate(e)) => simplifyInt(e)
      case Negate(e) => simplifyInt(e)
      case Operator(Constant(l), op, Constant(r)) => simplifyInt(Constant(operatorByName(Constant(l).eval(),op,Constant(r).eval())))
      case Operator(l, op, r) => Operator(simplifyInt(l), op, simplifyInt(r))
      case _ => expression
    }
  }
  def simplifyMultiset(expressionSet: ExpressionMultiset) : ExpressionMultiset= {
    expressionSet match {
      case OperatorSet(e, "*", q) if e == q => simplifyMultiset(e)
      case OperatorSet(e, "*", _) if e == ConstantSet(MultiSet.empty) => simplifyMultiset(e)
      case OperatorSet(_, "*", q) if q == ConstantSet(MultiSet.empty) => simplifyMultiset(q)
      case OperatorSet(e, "+", q) if q == ConstantSet(MultiSet.empty) => simplifyMultiset(e)
      case OperatorSet(e, "+", q) if e == ConstantSet(MultiSet.empty) => simplifyMultiset(q)
      case OperatorSet(e, "-", q) if e == q => simplifyMultiset(ConstantSet(MultiSet.empty))
      case OperatorSet(ConstantSet(l), op, ConstantSet(r)) =>
        simplifyMultiset(ConstantSet(operatorByNameSet(ConstantSet(l).eval(),op,ConstantSet(r).eval())))
      case OperatorSet(l, op, r) => OperatorSet(simplifyMultiset(l), op, simplifyMultiset(r))
      case _ => expressionSet
    }

  }
}
