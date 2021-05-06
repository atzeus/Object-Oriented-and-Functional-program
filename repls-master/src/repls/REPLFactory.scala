package repls
// You do not need to modify this file
object REPLFactory {
    def makeIntREPL() : REPL = new IntREPL()
    def makeMultiSetREPL() : REPL = new MultiSetREPL()
}
