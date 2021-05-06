package snake.basic

import snake.{ReverseGame, SnakeTestSuiteBase}

class NoReverseTest extends SnakeTestSuiteBase {

    test("TestNoReverseMode") {
        checkGame(
            List(
                TestFrame(2,
                    """OO>.
                      |.A.."""),
                TestFrame(2,
                    """.OO>
                      |.A.."""),
                TestFrame(2,
                    """>.OO
                      |.A.."""),
                TestFrame(2,
                    """O>.O
                      |.A.."""),
                TestFrame(2, List(ReverseGame(true)),
                    """OO>.
                      |.A.."""),
                TestFrame(2,
                    """.OO>
                      |.A.."""),
                TestFrame(2,
                    """>.OO
                      |.A.."""),
                TestFrame(2, List(ReverseGame(false)),
                    """O>.O
                      |.A.."""),
                TestFrame(2,
                    """OO>.
                      |.A.."""),
                TestFrame(2,
                    """.OO>
                      |.A.."""),
                TestFrame(2, List(ReverseGame(true)),
                    """>.OO
                      |.A.."""),
                TestFrame(2,
                    """O>.O
                      |.A.."""),
                TestFrame(2,
                    """OO>.
                      |.A.."""),
                TestFrame(2, List(ReverseGame(false)),
                    """.OO>
                      |.A.."""),
            ), hint = "For assignment 2.1 your code should NOT implement reverse mode. This test is a reminder of this," +
              "and ensures that enabling reverse mode does nothing. If you already implemented reverse mode, do not" +
              " just disable reverse mode, remove any code that deals with it."
        )
    }
}
