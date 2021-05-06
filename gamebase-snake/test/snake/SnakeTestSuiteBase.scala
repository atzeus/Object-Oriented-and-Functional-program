// DO NOT MODIFY FOR BASIC SUBMISSION
// scalastyle:off

package snake

import engine.random.RandomGenerator
import generic.{CellTypeInterface, GameLogicInterface, GameTestSuite}
import snake.logic.{Apple, CellType, Dimensions, Direction, East, Empty, GameLogic, North, Point, SnakeBody, SnakeHead, South, West}


sealed abstract class SnakeAction
case class ChangeDir(dir: Direction) extends SnakeAction
case class ReverseGame(enable: Boolean) extends SnakeAction
case object Step extends SnakeAction


case class SnakeGridTypeWrapper(gridType: CellType)  extends CellTypeInterface[SnakeGridTypeWrapper] {
  override def conforms(rhs: SnakeGridTypeWrapper): Boolean = (gridType, rhs.gridType) match {
    case (SnakeBody(_), SnakeBody(_)) => true
      // this is such that the student cannot succeed at all test by overriding equality!
    case (Empty(), Empty()) => true
    case (Apple(), Apple()) => true
    case (SnakeHead(dl),SnakeHead(dr)) =>
      (dl, dr) match  {
        case (East(), East()) => true
        case (West(), West()) => true
        case (South(), South()) => true
        case (North(), North()) => true
        case _ => false
      }
    case _ => false
  }

  override def toChar: Char = gridType match {
    case Empty() => '.'
    case SnakeHead(West()) => '<'
    case SnakeHead(East()) => '>'
    case SnakeHead(North()) => '^'
    case SnakeHead(South()) => 'v'
    case SnakeBody(_) => 'O'
    case Apple() => 'A'
  }
}


case class SnakeLogicWrapper(logic: GameLogic)
  extends GameLogicInterface[SnakeAction, SnakeGridTypeWrapper] {

  override def performAction(action: SnakeAction): Unit = action match {
    case ChangeDir(d) => logic.changeDir(d)
    case Step => logic.step()
    case ReverseGame(enable) => logic.setReverse(enable)
  }

  def getCell(x : Int, y : Int ): SnakeGridTypeWrapper =
    SnakeGridTypeWrapper(logic.getCellType(Point(x,y)))

  override def nrRows: Int = logic.gridDims.height
  override def nrColumns: Int = logic.gridDims.width
  override def isGameOver: Boolean = logic.gameOver

}

abstract class SnakeTestSuiteBase extends GameTestSuite
  [SnakeAction, SnakeGridTypeWrapper, SnakeLogicWrapper,Dimensions]() {


  override def makeGame(r: RandomGenerator, info: Dimensions): SnakeLogicWrapper =
    SnakeLogicWrapper(new GameLogic(r,info))

  override def gameLogicName: String = "SnakeLogic"

  def charToGridType(char: Char): SnakeGridTypeWrapper =
    SnakeGridTypeWrapper(char match {
      case '.' => Empty()
      case '<' => SnakeHead(West())
      case '>' => SnakeHead(East())
      case '^' => SnakeHead(North())
      case 'v' => SnakeHead(South())
      case 'O' => SnakeBody(0.5f)
      case 'A' => Apple()
      case _ => Empty()
    })


  private def toTestRecording(frames : Seq[TestFrame]) : TestRecording = {
    val dimensions: Dimensions = frames.head.display match {
      case grid: GridDisplay => Dimensions(grid.nrColumns, grid.nrRows)
      case _ => throw new Error("No grid display in test")
    }

    def addStep(input : FrameInput) : FrameInput = FrameInput(input.randomNumber, input.actions  :+ Step)

    val framesWithStep : Seq[TestFrame] =
      frames.head +: frames.tail.map(frame => TestFrame(addStep(frame.input), frame.display))

    TestRecording(dimensions,framesWithStep)
  }


  def checkGame(frames : Seq[TestFrame], hint : String = "" ) : Unit = {
     checkGame(toTestRecording(frames), hint )

  }

  def checkInterleave(framesA : Seq[TestFrame] , framesB : Seq[TestFrame]) : Unit = {
    checkInterleave(toTestRecording(framesA), toTestRecording(framesB))
  }


}
