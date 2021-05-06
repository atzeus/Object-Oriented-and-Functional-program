package snake.basic

import snake.logic.{North, South, West}
import snake.{ChangeDir, SnakeTestSuiteBase}

class WrapAroundTests extends SnakeTestSuiteBase {
    test("testWrapAroundEast") {
        checkGame(
            List(
                TestFrame(6,
                    """OO>...
                      |...A.."""),
                TestFrame(6,
                    """.OO>..
                      |...A.."""),
                TestFrame(6,
                    """..OO>.
                      |...A.."""),
                TestFrame(6,
                    """...OO>
                      |...A.."""),
                TestFrame(6,
                    """>...OO
                      |...A.."""),
                TestFrame(6,
                    """O>...O
                      |...A.."""),
                TestFrame(6,
                    """OO>...
                      |...A.."""),
                TestFrame(6,
                    """.OO>..
                      |...A.."""),
            ),
            hint = "If the snake leaves the screen, it re-emerges at the other end."
        )
    }

    // If the snake leaves the screen, it re-emerges at the other end.
    test("testWrapAroundSouth") {
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
                TestFrame(2,
                    """....
                      |.AO.
                      |..O.
                      |..v."""),
                TestFrame(2,
                    """..v.
                      |.A..
                      |..O.
                      |..O."""),
                TestFrame(2,
                    """..O.
                      |.Av.
                      |....
                      |..O."""),
                TestFrame(2,
                    """..O.
                      |.AO.
                      |..v.
                      |....""")
            ), hint = "If the snake leaves the screen, it re-emerges at the other end."
        )
    }

    // If the snake leaves the screen, it re-emerges at the other end.
    test("testWrapAroundNorth") {
        checkGame(
            List(
                TestFrame(6,
                    """OO>.
                      |....
                      |.A..
                      |...."""),
                TestFrame(6, List(ChangeDir(North())),
                    """.OO.
                      |....
                      |.A..
                      |..^."""),
                TestFrame(6,
                    """..O.
                      |....
                      |.A^.
                      |..O."""),
                TestFrame(6,
                    """....
                      |..^.
                      |.AO.
                      |..O."""),
                TestFrame(6,
                    """..^.
                      |..O.
                      |.AO.
                      |...."""),
            ), hint = "If the snake leaves the screen, it re-emerges at the other end."
        )
    }

    // If the snake leaves the screen, it re-emerges at the other end.
    test("testWrapAroundWest") {
        checkGame(
            List(
                TestFrame(2,
                    """OO>.
                      |.A..
                      |....
                      |...."""),
                TestFrame(2,
                    """.OO>
                      |.A..
                      |....
                      |...."""),
                TestFrame(2, List(ChangeDir(South())),
                    """..OO
                      |.A.v
                      |....
                      |...."""),
                TestFrame(2,
                    """...O
                      |.A.O
                      |...v
                      |...."""),
                TestFrame(2, List(ChangeDir(West())),
                    """....
                      |.A.O
                      |..<O
                      |...."""),
                TestFrame(2,
                    """....
                      |.A..
                      |.<OO
                      |...."""),
                TestFrame(2,
                    """....
                      |.A..
                      |<OO.
                      |...."""),
                TestFrame(2,
                    """....
                      |.A..
                      |OO.<
                      |...."""),
                TestFrame(2,
                    """....
                      |.A..
                      |O.<O
                      |...."""),
            ), hint = "If the snake leaves the screen, it re-emerges at the other end."
        )
    }
}
