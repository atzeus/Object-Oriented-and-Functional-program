package snake.basic

import snake.logic.{North, South, West}
import snake.{ChangeDir, SnakeTestSuiteBase}

class DeathTests extends SnakeTestSuiteBase {


    test("testGameOver") {
        checkGame(
            List(
                TestFrame(0,
                    """OO>A..
                      |......
                      |......"""),
                TestFrame(6,
                    """.OO>..
                      |...A..
                      |......"""),
                TestFrame(6,
                    """.OOO>.
                      |...A..
                      |......"""),
                TestFrame(6,
                    """.OOOO>
                      |...A..
                      |......"""),
                TestFrame(6, List(ChangeDir(South())),
                    """.OOOOO
                      |...A.v
                      |......"""),
                TestFrame(6, List(ChangeDir(West())),
                    """..OOOO
                      |...A<O
                      |......"""),
                TestFrame(6, List(ChangeDir(North())),
                    GameOverDisplay())
            ), hint = "The game ends when as the head of the snake collides with the rest of the snake."
        )
    }


    test("testPreciselyDoesNotDie") {
        checkGame(
            List(
                TestFrame(0,
                    """OO>A..
                      |......"""),
                TestFrame(3,
                    """.OO>..
                      |A....."""),
                TestFrame(3,
                    """.OOO>.
                      |A....."""),
                TestFrame(3,
                    """.OOOO>
                      |A....."""),
                TestFrame(3,
                    """>OOOOO
                      |A....."""),
                TestFrame(3,
                    """O>OOOO
                      |A....."""),
                TestFrame(3,
                    """OO>OOO
                      |A....."""),
                TestFrame(3,
                    """OOO>OO
                      |A....."""),
                TestFrame(3,
                    """OOOO>O
                      |A....."""),
                TestFrame(3,
                    """OOOOO>
                      |A....."""),
            ), hint = "The game should not end if the tail of the snake is exactly at the next position of the head " +
              "(and the snake does not grow)"
        )
    }

    test("testNoEscapeGameOver") {
        checkGame(
            List(
                TestFrame(0,
                    """OO>A.
                      |.....
                      |.....
                      |....."""),
                TestFrame(0,
                    """AOO>.
                      |.....
                      |.....
                      |....."""),
                TestFrame(0, List(ChangeDir(South())),
                    """AOOO.
                      |...v.
                      |.....
                      |....."""),
                TestFrame(0, List(ChangeDir(West())),
                    """AOOO.
                      |..<O.
                      |.....
                      |....."""),
                TestFrame(0, List(ChangeDir(North())),
                    GameOverDisplay()),
                TestFrame(0, GameOverDisplay())
            ), hint = "The snake should not move anymore after the game is over, hence it should not be possible to" +
              " “escape” from a game over by moving though the snake."
        )
    }
}
