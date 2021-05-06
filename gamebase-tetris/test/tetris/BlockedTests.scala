package tetris

class BlockedTests extends TetrisTestSuiteBase {


  test("testMoveOutLeft") {
    checkGame( List(TestFrame(5,
      """...T...
        |..TTT..
        |......."""),
      TestFrame(5, List(Left),
        """..T....
          |.TTT...
          |......."""),
      TestFrame(5, List(Left),
        """.T.....
          |TTT....
          |......."""),
      TestFrame(5, List(Left),
        """.T.....
          |TTT....
          |......."""),
      TestFrame(5, List(Left, Left, Left, Left),
        """.T.....
          |TTT....
          |.......""")),
      hint = "Trying to move out of the screen on the left side has no effect")
  }

  test("testMoveOutRight") {
    checkGame( List(TestFrame(2,
      """...L..
        |.LLL..
        |......"""),
      TestFrame(2, List(Right, Right),
        """.....L
          |...LLL
          |......"""),
      TestFrame(2, List(Right),
        """.....L
          |...LLL
          |......"""),
      TestFrame(2, List(Right, Right, Right, Right, Right),
        """.....L
          |...LLL
          |......""")),
      hint = "Trying to move out of the screen on the right side has no effect")
  }

  test("testBlockedByBlocksLeft") {
    checkGame(initialBoard =
      """O.....
        |O.....
        |O.....""",
      List(TestFrame(2,
        """O..L..
          |OLLL..
          |O....."""),
        TestFrame(2, List(Left),
          """O..L..
            |OLLL..
            |O.....""")),
      hint = "The current tetromino cannot move into existing blocks—instead, the movement is ignored.")
  }


  test("testBlockedByBlocksRight") {
    checkGame(initialBoard =
      """.....J
        |......
        |......""",
      List(TestFrame(4,
        """..SS.J
          |.SS...
          |......"""),
        TestFrame(4, List(Right),
          """...SSJ
            |..SS..
            |......"""),
        TestFrame(4, List(Right),
          """...SSJ
            |..SS..
            |......""")),
      hint = "The current tetromino cannot move into existing blocks—instead, the movement is ignored.")
  }

  test("testBlockedByBlocksRotateLeft") {
    checkGame(initialBoard =
      """...
        |...
        |Z..""",
      List(TestFrame(1,
        """J..
          |JJJ
          |Z.."""),
        TestFrame(1, List(RotateLeft),
          """J..
            |JJJ
            |Z..""")),
      hint = "The current tetromino cannot rotate into existing blocks –– instead, the rotation is ignored.")
  }


  test("testBlockedByBlocksRotateRight") {
    checkGame(initialBoard =
      """...O..
        |......
        |......
        |......""",
      List(TestFrame(0,
        """...O..
          |.IIII.
          |......
          |......"""),
        TestFrame(0, List(RotateRight),
          """...O..
            |.IIII.
            |......
            |......"""),
        TestFrame(0, List(Left),
          """...O..
            |IIII..
            |......
            |......"""),
        TestFrame(0, List(RotateRight),
          """..IO..
            |..I...
            |..I...
            |..I...""")),
      hint = "The current tetromino cannot rotate into existing blocks –– instead, the rotation is ignored.")
  }

}
