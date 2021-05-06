package snake.basic

import snake.SnakeTestSuiteBase

class GrowthTests extends SnakeTestSuiteBase {

    test("testGrow") {
        checkGame(
            List(
                TestFrame(1,
                    """OO>.A.....
                      |.........."""),
                TestFrame(1,
                    """.OO>A.....
                      |.........."""),
                TestFrame(10,
                    """..OO>.....
                      |...A......"""),
                TestFrame(10,
                    """..OOO>....
                      |...A......"""),
                TestFrame(10,
                    """..OOOO>...
                      |...A......"""),
                TestFrame(10,
                    """..OOOOO>..
                      |...A......"""),
                TestFrame(10,
                    """...OOOOO>.
                      |...A......"""),
                TestFrame(10,
                    """....OOOOO>
                      |...A......"""),
            ), hint = "After eating the apple, the snake should grow by one cell per step, for three steps. This means " +
              "that the tail should not be shortened for three steps. The new food should be placed as soon as the previous " +
              "food is eaten. The snake should grow after eating the apple, it should not already have grown in the " +
              "frame where the head of the snake eats the apple (and a new apple is placed)."
        )
    }

    test("testGrowTwice") {
        checkGame(
            List(
                TestFrame(0,
                    """OO>A..........
                      |.............."""),
                TestFrame(2,
                    """.OO>.A........
                      |.............."""),
                TestFrame(2,
                    """.OOO>A........
                      |.............."""),
                TestFrame(10,
                    """.OOOO>........
                      |.A............"""),
                TestFrame(10,
                    """.OOOOO>.......
                      |.A............"""),
                TestFrame(10,
                    """.OOOOOO>......
                      |.A............"""),
                TestFrame(10,
                    """.OOOOOOO>.....
                      |.A............"""),
                TestFrame(10,
                    """.OOOOOOOO>....
                      |.A............"""),
                TestFrame(10,
                    """..OOOOOOOO>...
                      |.A............"""),
                TestFrame(10,
                    """...OOOOOOOO>..
                      |.A............"""),
                TestFrame(10,
                    """....OOOOOOOO>.
                      |.A............"""),
            ), hint = "If a new apple is eaten before the snake has fully grown from the last apple, " +
              "the effect is stacked."
        )
    }
}
