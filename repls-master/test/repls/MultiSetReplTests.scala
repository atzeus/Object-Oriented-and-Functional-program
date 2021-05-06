// DO NOT MODIFY THIS FILE
package repls

import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import repls.infrastructure.TestBase

@RunWith(classOf[JUnitRunner])
class MultiSetReplTests extends TestBase {

    /*
    Basic parsing and echoing
     */
    test("Echo simple") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("{a,b,c}")
        val expected = "{a,b,c}"

        assert(result == expected)
    }

    test("Echo brackets") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("( {a,b} )")
        val expected = "{a,b}"

        assert(result == expected)
    }



    /*
    Evaluate summations
     */
    test("Evaluate summation simple") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("{b,c} + {a,b,b} + {a,b,c}")
        val expected = "{a,a,b,b,b,b,c,c}"

        assert(result == expected)
    }

    test("Evaluate summation brackets") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("( {a,b} + {b,c,f} ) + ( {a,b} + {r,f,g} + {g,h,i} )")
        val expected = "{a,a,b,b,b,c,f,f,g,g,h,i,r}"

        assert(result == expected)
    }


    /*
    Evaluating with subtraction
     */
    test("Evaluate subtraction simple") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("{a} - {a,b}")
        val expected = "{}"

        assert(result == expected)
    }

    test("Evaluate subtraction brackets") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("( ( {a,b,c} - {a} ) - {a,b} ) - {m,n}")
        val expected = "{c}"

        assert(result == expected)
    }

    /*
    Evaluating intersections
     */
    test("Evaluate intersection simple") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("{a,a,b,c,c,e} * {a,b,f,f,g}")
        val expected = "{a,b}"

        assert(result == expected)
    }

    test("Evaluate intersection brackets") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("( {g,g,r,r,t} * {r,t,e,d} ) * ( {y,y,u,d,r} * {y,u,r,d} )")
        val expected = "{r}"

        assert(result == expected)
    }

    /*
     Evaluation expressions with different operations
      */
    test("Evaluate expressions simple") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("{a,a,b,d} + {r,r,t,d} * {i,j,k,d}")
        val expected = "{a,a,b,d,d}"

        assert(result == expected)
    }

    test("Evaluate expressions brackets") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("{r,w,w,y} * ( {r,r,t} + {q,q,w} * {t,t,w} ) + {y,u,r}")
        val expected = "{r,r,u,w,y}"

        assert(result == expected)
    }

    test("Evaluate expressions nested brackets", weight = 5) {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("( ( {h,h,r} - {h,r,f,g} * {h,r,e,r} ) + ( {t,t,t,y,f} - ( {t,t,y} ) * {t,e,e,r} ) )")
        val expected = "{f,h,t,t,y}"

        assert(result == expected)
    }

    test("Evaluate expressions advanced brackets", weight = 5) {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("{e} + ( {f,f,g,t} * ( ( {f,f,r,t} - {a,a,n,d} ) + ( {f,g} ) - {r,t,d,d} ) - ( {f,t,y} + {y,y,r} ) ) * {f,y,y,e}")
        val expected = "{e,f}"

        assert(result == expected)
    }


    /*
    Assigning variables
     */
    test("Assign variables simple") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("n = {a}")
        val expected = "n = {a}"

        assert(result == expected)
    }

    test("Assign variables and evaluate") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("n = {a,b,n} + {b,n,n} * {r,r,t,b}")
        val expected = "n = {a,b,b,n}"

        assert(result == expected)
    }

    test("Assign variables reuse") {
        val repl = REPLFactory.makeMultiSetREPL()

        repl.readEval("n = {a,b,c}")
        val result = repl.readEval("m = n + {b,b,m}")
        val expected = "m = {a,b,b,b,c,m}"


        assert(result == expected)
    }

    test("Assign variables reassign") {
        val repl = REPLFactory.makeMultiSetREPL()

        repl.readEval("p = {g,g,y}")
        val result = repl.readEval("p = ( {g,h,h,n} + p * {g,g,g,y,y,i} ) * {g,g,h,n,p} + p")
        val expected = "p = {g,g,g,g,h,n,y}"

        assert(result == expected)
    }

    test("Assign variables evaluate") {
        val repl = REPLFactory.makeMultiSetREPL()

        repl.readEval("q = {y,t,r}")
        val result = repl.readEval("{r,y,n} + q")
        val expected = "{n,r,r,t,y,y}"

        assert(result == expected)
    }

    test("Assign variables advanced", weight = 5) {
        val repl = REPLFactory.makeMultiSetREPL()

        repl.readEval("r = {g,g,g,r,r,c} * {c,c,c,g,g,d,d,e}")
        repl.readEval("r = ( r * {g,c,c} - {d,d,d,c} ) + {g,h,y}")
        val result = repl.readEval("{} + ( r * r ) - ( {h,n,m} + {y,m,m,r} )")
        val expected = "{g,g}"

        assert(result == expected)
    }


    /*
    Simplification with the basic rules and constant sets
     */

    test("Simplify simple") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ {a} + {b}")
        val expected = "{a,b}"

        assert(result == expected)
    }

    test("Simplify empty set left") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ {} + {a,b}")
        val expected = "{a,b}"

        assert(result == expected)
    }

    test("Simplify empty set right") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ {a} + {}")
        val expected = "{a}"

        assert(result == expected)
    }

    test("Simplify intersection by itself") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ {a} * {a}")
        val expected = "{a}"

        assert(result == expected)
    }

    test("Simplify intersection by empty set left") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ {} * {a}")
        val expected = "{}"

        assert(result == expected)
    }

    test("Simplify intersection by empty set right") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ {a} * {}")
        val expected = "{}"

        assert(result == expected)
    }

    test("Simplify brackets") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ ( {a,b,c} * {b,c,d} ) + ( {a,b,c} * {e,f,g} )")
        val expected = "{b,c}"

        assert(result == expected)
    }

    test("Simplify leading empty set", weight = 2) {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ {} * ( ( {b,n,g} * {f,g} ) + ( {b} * {r,t,f} ) ) + {b} + {a,n,b} * {a,n,b}")
        val expected = "{a,b,b,n}"

        assert(result == expected)
    }

    test("Simplify trailing empty set", weight = 2) {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ ( {g,h} + ( {b,n,n} * {b,n,n} ) ) * {a,a,e,r} + ( {u,u,i,p} * {} )")
        val expected = "{}"

        assert(result == expected)
    }


    /*
    Simplification with abstract variables
     */
    test("Simplify abstract simple") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ a + {a,b,b,c}")
        val expected = "a + {a,b,b,c}"

        assert(result == expected)
    }

    test("Simplify unneeded empty set on abstract left") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ {} + a")
        val expected = "a"

        assert(result == expected)
    }

    test("Simplify unneeded empty set on abstract right") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ a + {}")
        val expected = "a"

        assert(result == expected)
    }

    test("Simplify multiply by empty set on abstract left") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ {} * a")
        val expected = "{}"

        assert(result == expected)
    }

    test("Simplify multiply by empty set on abstract right") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ a * {}")
        val expected = "{}"

        assert(result == expected)
    }

    test("Simplify intersection by its abstract self") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ a * a")
        val expected = "a"

        assert(result == expected)
    }

    test("Simplify intersection by its abstract self compositie") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ ( x + y + z ) * ( x + y + z )")
        val expected = "x + y + z"

        assert(result == expected)
    }

    test("Simplify subtract abstract") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ ( x + y + z ) - ( x + y + z )")
        val expected = "{}"

        assert(result == expected)
    }

    test("Simplify subtract abstract nested") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ ( x + y + z ) - ( x + y + z ) * ( x + y + z )")
        val expected = "{}"

        assert(result == expected)
    }



    test("Simplify abstract brackets") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ a + ( b * b )")
        val expected = "a + b"

        assert(result == expected)
    }

    test("Simplify abstract advanced", weight = 5) {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ a + ( b * c ) * ( b * c )")
        val expected = "a + b * c"

        assert(result == expected)
    }


    test("Simplify abstract brackets 2") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ a + ( b + {} )")
        val expected = "a + b"

        assert(result == expected)
    }

    test("Simplify nested") {
        val repl = REPLFactory.makeMultiSetREPL()

        val result = repl.readEval("@ a + ( {} + ( {} + ( {} + {} ) ) )")
        val expected = "a"

        assert(result == expected)
    }

    test("Simplify advanced",weight = 10) {
        val repl = REPLFactory.makeMultiSetREPL()

        repl.readEval("z = {a,b,b}")
        repl.readEval("q = {a}")
        val result = repl.readEval("@ ( x - x ) + ( a * b + a * c ) - ( ( ( a + {} ) * ( b + {} ) + ( a * a ) * ( c * c ) ) * ( ( a * b ) + ( a * c ) + ( ( z + q ) * {} ) ) )")
        val expected = "{}"

        assert(result == expected)
    }



    /*
    Simplification with assigned variables
     */
    test("Simplify with assigned variables") {
        val repl = REPLFactory.makeMultiSetREPL()

        repl.readEval("a = {a,b,b}")
        val result = repl.readEval("@ a")
        val expected = "{a,b,b}"

        assert(result == expected)
    }




    test("Simplify with assigned variables brackets", weight = 2) {
        val repl = REPLFactory.makeMultiSetREPL()

        repl.readEval("a = {a,b,c,c,d}")
        val result = repl.readEval("@ ( {} + ( {b,c,e} * a ) ) * b")
        val expected = "{b,c} * b"

        assert(result == expected)
    }

    test("Simplify with assigned variables advanced", weight = 5) {
        val repl = REPLFactory.makeMultiSetREPL()

        repl.readEval("a = {a,c}")
        repl.readEval("b = a * {a,b}")
        repl.readEval("c = b + ( a * {c,b} )")

        val result = repl.readEval("@ ( ( a * b + {a} * b ) + c ) + d")
        val expected = "{a,a,a,c} + d"

        assert(result == expected)
    }
}