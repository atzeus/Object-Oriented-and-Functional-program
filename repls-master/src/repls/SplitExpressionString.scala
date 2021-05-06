// You do not need to modify this file
package repls

object SplitExpressionString {

  // You can adjust this code if you want to

  /* Split strings in a way that is handy for this exercise.

  This is optional, you can also parse it in your own way.

  This will split any, sensible, form of a expression into a sequences of strings (operators, variables, or (negative) numbers)

  examples:

  ac+bc -> (ac,+,bc)
  ac + bc -> (ac,+,bc)
  (ac * 8) + 2 -> ((,ac,*,),+,2)

  negative number parsing:
  89-ff -> (89,-,ff)
  b - 11 -> (b, - , 11)
  if - directly borders on a character or digit on the right, but not on the left, is included in the string on the right
  b + -11 -> (b,+,-11)
  42 * -a -> (42,*,-a)

  set parsing: everything between { and } is taken as a single literal string

  {a,bc,d} + {df , sfd , a} -> ({a,bc,d}, + ,{df , sfd , a})


   */
  def splitExpressionString(expression : String) : Seq[String] = {
    val builder = Seq.newBuilder[String]
    var curString = ""

    var inLiteral = false
    def addNonemptyStringAndReset() : Unit =
      if(curString.nonEmpty) {
        builder.addOne(curString)
        curString = ""
      }

    for(character <- expression) {
      if(inLiteral) {
        curString+= character
        if(character == '}') {
          addNonemptyStringAndReset()
          inLiteral = false
        }
      } else {
        character match {
            // Start splitting for multiset representation
          case '{' => {addNonemptyStringAndReset() ; inLiteral = true; curString = "{" }
            // This part of the expression is done
          case ' ' | '\t' => addNonemptyStringAndReset()
            // If negative
          case '-' if curString.isEmpty => { addNonemptyStringAndReset(); curString = "-" }
            // Handle subtraction separately
          case '-' if curString.nonEmpty => { addNonemptyStringAndReset(); builder.addOne(character.toString) }
            // Is operator
          case '(' | ')' | ',' | '+' | '*' | '=' | '@' => {
            addNonemptyStringAndReset(); builder.addOne(character.toString)
          }
            // Add character or number to the string
          case _ if character.isLetterOrDigit => curString += character
            // Base:
          case _ => throw new Exception("Do not know how to parse " + character)
        }
      }
    }
    addNonemptyStringAndReset()
    builder.result()
  }

}
