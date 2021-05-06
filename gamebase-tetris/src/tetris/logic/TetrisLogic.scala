package tetris.logic
import engine.random.{RandomGenerator, ScalaRandomGen}
import tetris.logic.TetrisLogic._
/** To implement Tetris, complete the ``TODOs`` below.
 *
 * If you need additional files,
 * please also put them in the ``tetris`` package.
 */

abstract class Tetromino {
  var cellType: CellType = _
  var cellBody: Array[Point] = new Array[Point](4)
  var newCellBody: Array[Point] = Array(Point(0, 0), Point(0, 0), Point(0, 0), Point(0, 0))
  var direction: String = "north"
  var newDir: String = "north"
  var anchorIndex: Int = 0
  def updateBody() : Unit= {
    direction = newDir
    cellBody = newCellBody.clone()
  }
  def rotateRight(): Unit = {
    for (i <- cellBody.indices)
      newCellBody(i) = Point(-(cellBody(i).y - cellBody(anchorIndex).y) + cellBody(anchorIndex).x,
        (cellBody(i).x - cellBody(anchorIndex).x) + cellBody(anchorIndex).y)
  }
  def rotateLeft(): Unit = {
    for (i <- cellBody.indices)
      newCellBody(i) = Point((cellBody(i).y - cellBody(anchorIndex).y) + cellBody(anchorIndex).x,
        -(cellBody(i).x - cellBody(anchorIndex).x) + cellBody(anchorIndex).y)
  }
  def moveRight():Unit = {
    newDir = direction
    for (i <- cellBody.indices)
      newCellBody(i) = Point(cellBody(i).x + 1, cellBody(i).y)
  }
  def moveLeft():Unit = {
    newDir = direction
    for (i <- cellBody.indices)
      newCellBody(i) = Point(cellBody(i).x - 1, cellBody(i).y)
  }
  def moveDown():Unit = {
    newDir = direction
    for (i <- cellBody.indices)
      newCellBody(i) = Point(cellBody(i).x, cellBody(i).y + 1)
  }
}
case class AnotherTetromino() extends Tetromino
case class OTetromino() extends Tetromino {
  override def rotateRight(): Unit = {newCellBody = cellBody.clone()}
  override def rotateLeft(): Unit = {newCellBody = cellBody.clone()}
}
case class ITetromino() extends Tetromino {
  override def rotateRight(): Unit = {
    val mapNewDirICellRight = Map("north"->"east", "east"->"south", "south"->"west", "west"->"north")
    newDir = mapNewDirICellRight(direction)
    setNewICellAnchor()
    for (i <- 1 until cellBody.length)
      newCellBody(i) = Point(-(cellBody(i).y - cellBody(anchorIndex).y) + newCellBody(anchorIndex).x,
        (cellBody(i).x - cellBody(anchorIndex).x) + newCellBody(anchorIndex).y)
  }
  override def rotateLeft(): Unit = {
    val mapNewDirICellLeft = Map("north"->"west", "west"->"south", "south"->"east", "east"->"north")
    newDir = mapNewDirICellLeft(direction)
    setNewICellAnchor()
    for (i <- 1 until cellBody.length)
      newCellBody(i) = Point((cellBody(i).y - cellBody(anchorIndex).y) + newCellBody(anchorIndex).x,
        -(cellBody(i).x - cellBody(anchorIndex).x) + newCellBody(anchorIndex).y)
  }
  def setNewICellAnchor(): Unit = {
    val mapAnchor = Map("north"->Point(cellBody(anchorIndex).x, cellBody(anchorIndex).y - 1),
                        "east"->Point(cellBody(anchorIndex).x + 1, cellBody(anchorIndex).y),
                        "south"->Point(cellBody(anchorIndex).x, cellBody(anchorIndex).y + 1),
                        "west"->Point(cellBody(anchorIndex).x - 1, cellBody(anchorIndex).y))
    newCellBody(anchorIndex) = mapAnchor(newDir)
  }
}
case class GameState(randomGen: RandomGenerator, gridDims: Dimensions, initialBoard: Seq[Seq[CellType]]) {
  var boardGame : Array[Array[CellType]] = Array.fill(gridDims.width, gridDims.height) {Empty}
  var currCell : Tetromino = _
  var gameOver : Boolean = false
  setBoardGame()
  setNewCell()
  def setBoardGame(): Unit = {
    for (i <- 0 until gridDims.width; j <- 0 until gridDims.height) {
      if (initialBoard(j)(i) != Empty)
        boardGame(i)(j) = initialBoard(j)(i)
    }
  }
  def setNewCell(): Unit = {
    if (!gameOver) {
      val index = randomGen.randomInt(7)
      val x = (scala.math.ceil(gridDims.width.toDouble / 2.0) - 1.0).toInt
      index match {
        case 0 => currCell = ITetromino();currCell.cellType = ICell; currCell.cellBody = Array(Point(x, 1), Point(x - 1, 1), Point(x + 1, 1), Point(x + 2, 1))
        case 1 => currCell = AnotherTetromino(); currCell.cellType = JCell; currCell.cellBody = Array(Point(x, 1), Point(x - 1, 0), Point(x - 1, 1), Point(x + 1, 1))
        case 2 => currCell = AnotherTetromino(); currCell.cellType = LCell; currCell.cellBody = Array(Point(x, 1), Point(x - 1, 1), Point(x + 1, 1), Point(x + 1, 0))
        case 3 => currCell = OTetromino(); currCell.cellType = OCell; currCell.cellBody = Array(Point(x, 1), Point(x, 0),  Point(x + 1, 1), Point(x + 1, 0))
        case 4 => currCell = AnotherTetromino();currCell.cellType = SCell; currCell.cellBody = Array(Point(x, 1), Point(x - 1, 1),  Point(x, 0), Point(x + 1, 0))
        case 5 => currCell = AnotherTetromino();currCell.cellType = TCell; currCell.cellBody = Array(Point(x, 1), Point(x - 1, 1),  Point(x + 1, 1), Point(x, 0))
        case 6 => currCell = AnotherTetromino(); currCell.cellType = ZCell;currCell.cellBody = Array(Point(x, 1), Point(x - 1, 0), Point(x, 0),  Point(x + 1, 1))
      }
      checkGameOver()
    }
  }
  def checkGameOver(): Unit = {
    for (i <- currCell.cellBody.indices) {
      if (boardGame(currCell.cellBody(i).x)(currCell.cellBody(i).y) != Empty) gameOver = true
    }
  }
  def colliding: Boolean = {
    for (i <- currCell.newCellBody.indices)
      if (boardGame(currCell.newCellBody(i).x)(currCell.newCellBody(i).y) != Empty) return true
    false
  }
  def outOfBounds: Boolean = {
    for (i <- currCell.newCellBody.indices) {
      if (currCell.newCellBody(i).x < 0 || currCell.newCellBody(i).x >= gridDims.width || currCell.newCellBody(i).y >= gridDims.height) return true
    }
    false
  }
  def allowToUpdateBody:Boolean = if(!outOfBounds && !colliding) true else false
  def rotateLeft(): Unit = {
    currCell.rotateLeft()
    if (allowToUpdateBody)currCell.updateBody()
  }
  def rotateRight(): Unit = {
    currCell.rotateRight()
    if (allowToUpdateBody) currCell.updateBody()
  }
  def hardDrop(): Unit = {
    while (moveDown()) {}
  }
  def moveRight(): Unit = {
    currCell.moveRight()
    if (allowToUpdateBody) currCell.updateBody()
  }
  def moveLeft(): Unit = {
    currCell.moveLeft()
    if (allowToUpdateBody) currCell.updateBody()
  }
  def moveDown(): Boolean = {
    currCell.moveDown()
    manageBoardGame
  }
  def manageBoardGame: Boolean = {
    if (allowToUpdateBody) {
      currCell.updateBody()
      true
    }
    else {
      updateBoard()
      for (i <- 0 until gridDims.height)
        clearLines(i)
      setNewCell()
      false
    }
  }
  def updateBoard(): Unit = {
    for (i <- currCell.cellBody.indices) {
      boardGame(currCell.cellBody(i).x)(currCell.cellBody(i).y) = currCell.cellType
    }
  }
  def clearLines(row: Int): Unit = {
    var isFull = true
    for (i <- 0 until gridDims.width) {
      if (boardGame(i)(row) == Empty) isFull = false
    }
    if (isFull) {
      for (i <- 0 until gridDims.width)
        boardGame(i)(row) = Empty
      moveBoard()
    }
    def moveBoard(): Unit = {
      for (i <- row - 1 to 0 by -1; j <- 0 until gridDims.width) {
        if (boardGame(j)(i) != Empty) {
          boardGame(j)(i + 1) = boardGame(j)(i)
          boardGame(j)(i) = Empty
        }
      }
    }
  }
  def getCellType(p: Point): CellType = {
    if (currCell.cellBody.contains(p)) currCell.cellType
    else boardGame(p.x)(p.y)
  }
}
class TetrisLogic(val randomGen: RandomGenerator,
                  val gridDims : Dimensions,
                  val initialBoard: Seq[Seq[CellType]]) {
  var gameState: GameState = GameState(randomGen, gridDims, initialBoard)
  def this(random: RandomGenerator, gridDims : Dimensions) =
    this(random, gridDims, makeEmptyBoard(gridDims))
  def this() =
    this(new ScalaRandomGen(), DefaultDims, makeEmptyBoard(DefaultDims))
  def rotateLeft(): Unit = gameState.rotateLeft()
  def rotateRight(): Unit = gameState.rotateRight()
  def moveLeft(): Unit = gameState.moveLeft()
  def moveRight(): Unit = gameState.moveRight()
  def moveDown(): Unit = gameState.moveDown()
  def doHardDrop(): Unit = gameState.hardDrop()
  def isGameOver: Boolean = gameState.gameOver
  def getCellType(p : Point): CellType = gameState.getCellType(p)
}
object TetrisLogic {
  val FramesPerSecond: Int = 5 // change this to speed up or slow down the game
  val DrawSizeFactor = 1.0 // increase this to make the game bigger (for high-res screens)
  // or decrease to make game smaller
  def makeEmptyBoard(gridDims : Dimensions): Seq[Seq[CellType]] = {
    val emptyLine = Seq.fill(gridDims.width)(Empty)
    Seq.fill(gridDims.height)(emptyLine)
  }
  val DefaultWidth: Int = 10
  val NrTopInvisibleLines: Int = 4
  val DefaultVisibleHeight: Int = 20
  val DefaultHeight: Int = DefaultVisibleHeight + NrTopInvisibleLines
  val DefaultDims : Dimensions = Dimensions(width = DefaultWidth, height = DefaultHeight)
  def apply() = new TetrisLogic(new ScalaRandomGen(),
    DefaultDims,
    makeEmptyBoard(DefaultDims))
}