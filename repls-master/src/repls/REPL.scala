// DO NOT MODIFY THIS FILE
package repls

trait REPL {
    val replName : String

    def readEval(command : String) : String

    def run() : Unit = {
        while(true) {
            println(s"repls.$replName>")
            val s = scala.io.StdIn.readLine().trim

            if(s == "exit") return
            println(readEval(s))
        }
    }

}
