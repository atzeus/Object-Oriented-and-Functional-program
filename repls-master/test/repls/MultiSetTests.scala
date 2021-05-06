// DO NOT MODIFY THIS FILE
package repls

import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import repls.infrastructure.TestBase

@RunWith(classOf[JUnitRunner])
class MultiSetTests extends TestBase {

    /*
    Basic construction
     */
    test("MultiSets can represent the empty Set") {
        assertResult(Seq.empty) {
            MultiSet(Seq.empty).toSeq
        }
    }

    test("Simple multiset character") {
        assertResult(Seq('a')) {
            MultiSet(Seq('a')).toSeq
        }
    }

    test("Simple multiset string") {
        assertResult(Seq("element")) {
            MultiSet(Seq("element")).toSeq
        }
    }

    test("Simple multiset Int") {
        assertResult(Seq(1)) {
            MultiSet(Seq(1)).toSeq
        }
    }

    test("Simple multiset double") {
        assertResult(Seq(1.0)) {
            MultiSet(Seq(1.0)).toSeq
        }
    }

    test("Multiple elements") {
        assertResult(Seq('a','b','c')) {
            MultiSet(Seq('a','b','c')).toSeq.sorted
        }
    }


    test("Same elements") {
        assertResult(Seq('a','a','a')) {
            MultiSet(Seq('a','a','a')).toSeq.sorted
        }
    }

    test("Different and same elements") {
        assertResult(Seq(1,1,2,2,3)) {
            MultiSet(Seq(1,1,2,2,3)).toSeq.sorted
        }
    }

    /*
    Stringify
     */
    test("toString representation empty") {
        assertResult("{}") {
            MultiSet(Seq.empty).toString
        }
    }

    test("toString representation numbered") {
        assertResult("{1,1,2}") {
            MultiSet(Seq(1,1,2)).toString
        }
    }

    test("toString representation characters") {
        assertResult("{a,a,a,b,c}") {
            MultiSet(Seq('a','b','c','a','a')).toString
        }
    }

    /*
    Summation
     */
    test("Sum of multiset with empty set") {
        assertResult(Seq('a')) {
            val singleElement = MultiSet(Seq('a'))
            val emptySet = MultiSet[Char](Seq.empty)

            (emptySet + singleElement).toSeq.sorted
        }
    }

    test("Sum of two multisets") {
        assertResult(Seq(1.0,1.0,1.2,1.2,2.0,2.2,4.0)) {
            val first = MultiSet(Seq(1.0,1.2,2.0))
            val second = MultiSet(Seq(1.0,1.2,2.2,4.0))

            (first + second).toSeq.sorted(Ordering.Double.TotalOrdering)
        }
    }

    test("Sum of three multisets") {
        assertResult(Seq(1,2,2,3,4,5,5)) {
            val first = MultiSet(Seq(1,2))
            val second = MultiSet(Seq(2,4,5))
            val third = MultiSet(Seq(3,5))

            (first + second + third).toSeq.sorted
        }
    }

    /*
    Difference
     */
    test("Difference of a multiset with empty set") {
        assertResult(Seq.empty) {
            val singleElement = MultiSet(Seq('a'))
            val emptySet = MultiSet[Char](Seq.empty)

            (emptySet - singleElement).toSeq.sorted
        }
    }

    test("Difference of two multisets") {
        assertResult(Seq(2.0)) {
            val first = MultiSet(Seq(1.0,1.2,2.0))
            val second = MultiSet(Seq(1.0,1.2,2.2,4.0))

            (first - second).toSeq.sorted(Ordering.Double.TotalOrdering)
        }
    }

    test("Difference of three multisets") {
        assertResult(Seq(1)) {
            val first = MultiSet(Seq(1,2,3))
            val second = MultiSet(Seq(2,4,5))
            val third = MultiSet(Seq(3,5))

            (first - second - third).toSeq.sorted
        }
    }

    /*
    Intersection
     */
    test("Intersection with empty set") {
        assertResult(Seq.empty) {
            val multiset = MultiSet(Seq(1,1,2,3))
            val emptySet = MultiSet[Int](Seq.empty)

            (multiset * emptySet).toSeq.sorted
        }
    }

    test("Intersection of two multisets") {
        assertResult(Seq(2,3)) {
            val first = MultiSet(Seq(1,2,2,3,3,4,4,4))
            val second = MultiSet(Seq(2,3,5,5,5))

            (first * second).toSeq.sorted
        }
    }

    test("Intersection of tree multisets") {
        assertResult(Seq(2)) {
            val first = MultiSet(Seq(1,2,3))
            val second = MultiSet(Seq(2,4,5))
            val third = MultiSet(Seq(2,3,5))

            (first * second * third).toSeq.sorted
        }
    }

    /*
    Expressions
     */
    test("Multiset expressions numeric", weight = 5) {
        assertResult(Seq(2,3)) {
            val first = MultiSet(Seq(1,2,3,4,4,4))
            val second = MultiSet(Seq(1,4,5,5,5))
            val third = MultiSet(Seq(1,1,1,2,3,3,4,5))
            val fourth = MultiSet(Seq(1,4,6,7,7,8))

            ((first - second) * (third - fourth)).toSeq.sorted
        }
    }

    test("Multiset expression characters", weight = 5) {
        assertResult(Seq('d','d','i','o')) {
            val first = MultiSet(Seq('d','d','d','g','h','i','i','i','o'))
            val second = MultiSet(Seq('a','b','d','g','i','i'))
            val third = MultiSet(Seq('d','d','d','h','i','l','o','o'))
            val fourth = MultiSet(Seq('d','d','i','i','o','o','p','p'))

            ((first - second) * third * fourth).toSeq.sorted
        }
    }
}
