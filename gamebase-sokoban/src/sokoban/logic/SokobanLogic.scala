package sokoban.logic

import sokoban.game.{Box, Direction, Empty, CellType, Player, Target, Wall}
import sokoban.logic.snake.logic.LevelsObject


class SokobanLogic {

  private var nextLevels : List[GameFrame] = SokobanLogic.Levels
  private var currentLevel : SokobanLevel = new SokobanLevel(nextLevels.head)

  def levelDone : Boolean = currentLevel.levelDone

  def allLevelsDone : Boolean = nextLevels.isEmpty

  def tryMove(dir : Direction) : Unit  =
    if(levelDone) nextLevel() else currentLevel.tryMove(dir)

  def stepBack() : Unit = currentLevel.stepBack()

  def nextLevel() : Unit = {
    if(allLevelsDone) return
    nextLevels = nextLevels.tail
    currentLevel = new SokobanLevel(nextLevels.head)
  }

  def nrColumns : Int = currentDimensions._1
  def nrRows : Int = currentDimensions._2

  def currentDimensions : (Int,Int) = currentLevel.dimensions

  def getCellType(x : Int, y : Int) : CellType = currentLevel.cellTypeAt(x,y)

}

class SokobanLevel(private val start : GameFrame) {

  private var frames : SStack[GameFrame] = SStack[GameFrame](start)

  private def currentFrame: GameFrame = frames.top

  def levelDone: Boolean = currentFrame.levelDone

  def tryMove(dir: Direction): Unit = {
    frames = frames.push(currentFrame.tryMove(dir))
  }

  def stepBack() : Unit  =
    if(frames.size >= 1) frames = frames.pop

  def cellTypeAt(x: Int, y: Int): CellType =
    currentFrame.cellTypeAt(Point(x, y))

  def dimensions: (Int, Int) = currentFrame.dimensions
}

case class GameFrame(
   dimensions : (Int,Int),
   walls : Set[Point],
   targets : Set[Point],
   boxes : Set[Point],
   player : Point) {


  def tryMove(dir : Direction) : GameFrame = {
    val moveTo = player + dir.toPoint
    // if there is a box in that direction,
    // where will it be pushed to?
    val boxPushedTo = moveTo + dir.toPoint

    if(canMoveBox(from = moveTo,to = boxPushedTo)) {
      val newBoxes = boxesAfterMove(from = moveTo, to = boxPushedTo)
      copy(player = moveTo, boxes = newBoxes)
    }
    else if(isEmpty(moveTo))  copy(player = moveTo)
    else this
  }

  def levelDone : Boolean = boxes == targets

  def cellTypeAt(p : Point): CellType =
    if      (isWall(p))   Wall()
    else if (isPlayer(p)) Player()
    else if (isBox(p))    Box()
    else if (isTarget(p)) Target()
    else                  Empty()

  private def isEmpty(p : Point)  : Boolean = !isWall(p) && !isBox(p)
  private def isWall(p : Point)   : Boolean = walls contains p
  private def isBox(p : Point)    : Boolean = boxes contains p
  private def isTarget(p : Point) : Boolean = targets contains p
  private def isPlayer(p : Point) : Boolean = player == p

  private def canMoveBox(from : Point, to : Point) : Boolean =
    isBox(from) && isEmpty(to)

  private def boxesAfterMove(from : Point, to : Point) : Set[Point] =
    boxes - from + to

}

object SokobanLogic{

  val Levels : List[GameFrame] =
    LevelsObject.Levels.map(gridToGameFrame)

  def gridToGameFrame(grid : Seq[Seq[CellType]]): GameFrame = {
    val map = mapPointToGrid(gridToMap(grid), CellType.nonEmptyCellTypes)
    GameFrame(
      dimensions = (grid.map(_.size).max,grid.size),
      walls   = map(Wall()),
      player  = map(Player()).head,
      boxes   = map(Box()),
      targets = map(Target()))
  }

  def gridToMap(grid : Seq[Seq[CellType]]) : Map[Point,CellType] = (
    for((row, rowIndex) <- grid.zipWithIndex;
        (cell,cellIndex) <- row.zipWithIndex)
      yield (Point(cellIndex, rowIndex), cell)
    ).toMap

  def mapPointToGrid(m : Map[Point,CellType], gridTypes: Set[CellType]) : Map[CellType,Set[Point]] = (
    for(gridType <- gridTypes)
      yield (gridType,m.filter(_._2 == gridType).keySet)
    ).toMap


  val MaxWidth = 50
  val MaxHeight = 50
}