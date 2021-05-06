package repls

object REPLFunctions {
  def isNumber(s : String) : Boolean = s.toSeq.forall(_.isDigit)
  def isSet(s : String): Boolean = if(s(0) == '{' && s.last == '}') true else false
  def isNegateNum(s :String) :Boolean = if(s(0) == '-') true else false
  def isOperator(s : String) : Boolean = s == "*" || s == "-" || s == "+"
  def isParentheses(s : String) : Boolean = s == "(" || s == ")"
  def isSimplifyExpression(s: String) : Boolean = s == "@"
  def isVariable(s : String) : Boolean = !isNumber(s) && !isSet(s) && !isOperator(s) && !isParentheses(s) && !isSimplifyExpression(s) && !isNegateNum(s)
  def isOperad(s:String) :Boolean = !isOperator(s) && !isParentheses(s) && !isSimplifyExpression(s)
  def operatorByName(l : Int, name : String, r : Int) : Int = {
    name match {
      case "+" => l + r
      case "-" => l - r
      case "*" => l * r
    }
  }
  def operatorByNameSet(l: MultiSet[String], name : String, r : MultiSet[String]): MultiSet[String] = {
    name match {
      case "+" => l + r
      case "-" => l - r
      case "*" => l * r
    }
  }
  def stringToSeq(s: String): Seq[String] = {
    var seq  :Seq[String] = Seq()
    for(i<-s) if(i != ',' && i != '{' && i != '}') seq = seq :+ i.toString
    seq
  }
}

