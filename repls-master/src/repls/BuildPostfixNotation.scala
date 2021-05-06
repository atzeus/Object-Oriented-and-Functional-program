package repls

import repls.REPLFunctions.isOperad
import scala.collection.mutable.ArrayBuffer

object BuildPostfixNotation {
  def build(elements : ArrayBuffer[String]) : ArrayBuffer[String]={
    val operatorsMap = Map("*"->3,"+"->2,"-"->2)
    var tmpOperatorsStack : SStack[(Int, String)] = SStack()
    var postfixBuffer : ArrayBuffer[String] = ArrayBuffer()

    def handleRightBracket() : Unit = {
      var foundRB = false
      while (tmpOperatorsStack.nonEmpty && !foundRB) {
        tmpOperatorsStack.top._2 match {
          case "(" => foundRB = true ; tmpOperatorsStack = tmpOperatorsStack.pop
          case operator : String => postfixBuffer.append(operator); tmpOperatorsStack = tmpOperatorsStack.pop
        }
      }
    }
    elements map {
      case element : String if isOperad(element) => postfixBuffer += element
      case element : String if element == "(" => tmpOperatorsStack = tmpOperatorsStack.push((-1, "("))
      case element : String if element == ")" => handleRightBracket()
      case element : String => operatorsMap.get(element) match{
        case Some(precedenceLevel:Int) if tmpOperatorsStack.isEmpty => tmpOperatorsStack = tmpOperatorsStack.push(precedenceLevel,element)
        case Some(precedenceLevel:Int) if precedenceLevel >= tmpOperatorsStack.top._1 =>
          tmpOperatorsStack = tmpOperatorsStack.push(precedenceLevel, element)
        case Some(precedenceLevel:Int) if precedenceLevel < tmpOperatorsStack.top._1 =>
          while(tmpOperatorsStack.nonEmpty && precedenceLevel<tmpOperatorsStack.top._1){
            postfixBuffer.append(tmpOperatorsStack.top._2)
            tmpOperatorsStack = tmpOperatorsStack.pop
          }
          tmpOperatorsStack = tmpOperatorsStack.push(precedenceLevel,element)
      }
    }
    while(tmpOperatorsStack.nonEmpty){
      postfixBuffer.append(tmpOperatorsStack.top._2)
      tmpOperatorsStack = tmpOperatorsStack.pop
    }
    postfixBuffer
  }
}
