package repls

import scala.collection.mutable.ArrayBuffer

/*
    The parent class of IntREPL and MultiSetREPL.
 */
abstract class REPLBase extends REPL {
    // This Base type is to make a generic REPL. In the Readme you can find more details on how this can be used.
    type Base
}