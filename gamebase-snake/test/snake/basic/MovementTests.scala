package snake.basic

import snake.logic.{East, North, South, West}
import snake.{ChangeDir, SnakeTestSuiteBase}

class MovementTests extends SnakeTestSuiteBase {

    test("testMove") {
        checkGame(
            List(
                TestFrame(3,
                    """OO>...
                      |A....."""),
                TestFrame(3,
                    """.OO>..
                      |A....."""),
                TestFrame(3,
                    """..OO>.
                      |A....."""),
            ), hint = "When moving the snake is extended from the head in the current direction (and he snake is " +
              "shortened from the tail if it is not currently growing)."
        )
    }

    test("testChangeDirs") {
        checkGame(
            List(
                TestFrame(2,
                    """OO>.
                      |.A..
                      |....
                      |...."""),
                TestFrame(2, List(ChangeDir(South())),
                    """.OO.
                      |.Av.
                      |....
                      |...."""),
                TestFrame(2,
                    """..O.
                      |.AO.
                      |..v.
                      |...."""),
                TestFrame(2, List(ChangeDir(West())),
                    """....
                      |.AO.
                      |.<O.
                      |...."""),
                TestFrame(2,
                    """....
                      |.A..
                      |<OO.
                      |...."""),
                TestFrame(2, List(ChangeDir(North())),
                    """....
                      |^A..
                      |OO..
                      |...."""),
                TestFrame(2,
                    """^...
                      |OA..
                      |O...
                      |...."""),
                TestFrame(2, List(ChangeDir(East())),
                    """O>..
                      |OA..
                      |....
                      |...."""),
                TestFrame(2,
                    """OO>.
                      |.A..
                      |....
                      |....""")
            ), hint = "You can change direction."
        )
    }

    test("testIgnoreEatHead") {
        checkGame(
            List(
                TestFrame(5,
                    """OO>...
                      |..A..."""),
                TestFrame(5, List(ChangeDir(West())),
                    """.OO>..
                      |..A..."""),
                TestFrame(5, List(ChangeDir(West())),
                    """..OO>.
                      |..A...""")
            ), hint = "It should not be possible to “crash into yourself”: If the snake is currently traveling " +
              "eastwards, it should not be possible to change the current direction to west (and then die in the next " +
              "step). This generalizes to other directions."
        )
    }

    test("testQuickSwitch") {
        checkGame(
            List(
                TestFrame(4,
                    """OO>.
                      |...A
                      |...."""),
                TestFrame(4,
                    """.OO>
                      |...A
                      |...."""),
                TestFrame(4, List(ChangeDir(North()), ChangeDir(West())),
                    """..OO
                      |...A
                      |...^"""),
            ), hint = "It should not be possible to crash into yourself by changing the direction multiple times " +
              "before the next step. For example, if the snake came from the west, and the we changed the current " +
              "direction to north, it still should not be possible to change the direction to to west before taking a step."
        )
    }

    test("testQuickSwitch2") {
        checkGame(
            List(
                TestFrame(4,
                    """OO>.
                      |...A
                      |...."""),
                TestFrame(4,
                    """.OO>
                      |...A
                      |...."""),
                TestFrame(4, List(ChangeDir(West()), ChangeDir(North())),
                    """..OO
                      |...A
                      |...^"""),
            ), hint = "To prevent crashing into yourself, you should not just pick the first change of direction." +
              "Instead, you should take the last valid change of direction (i.e. not a change in the direction where" +
              "the snake came from "
        )
    }

}
