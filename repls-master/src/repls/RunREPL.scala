// You do not need to modify this file
package repls

object RunREPL {
  def main(args: Array[String]): Unit = {
    val repl = {
      val classType = args(0)
      if (classType == "IntREPL") REPLFactory.makeIntREPL()
      else if (classType == "MultiSetREPL") REPLFactory.makeMultiSetREPL()
      else throw new Error("Not a valid REPL")
    }
    repl.run()
  }
}
