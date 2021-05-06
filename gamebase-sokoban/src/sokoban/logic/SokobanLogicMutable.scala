package sokoban.logic

import sokoban.game.{Box, Direction, Empty, CellType, Player, Target}
import sokoban.logic.snake.logic.LevelsObject

import scala.collection.mutable

class SokobanLogicMutable {

  private var nextLevels : List[Seq[Seq[CellType]]] = LevelsObject.Levels
  private var currentLevel : MutableSokobanLevel = new MutableSokobanLevel(nextLevels.head)

  def levelDone : Boolean = currentLevel.levelDone

  def allLevelsDone : Boolean = nextLevels.isEmpty

  def tryMove(dir : Direction) : Unit  =
    if(levelDone) gotoNextLevel() else currentLevel.tryMove(dir)

  def stepBack() : Unit = currentLevel.stepBack()


  def nrColumns : Int = currentLevel.width
  def nrRows : Int = currentLevel.height

  def cellType(x : Int, y : Int) : CellType = currentLevel.cellTypeAt(Point(x,y))


  private def gotoNextLevel() : Unit = {
    if(allLevelsDone) return
    nextLevels = nextLevels.tail
    currentLevel = new MutableSokobanLevel(nextLevels.head)
  }

}

class GameCell(val target : Boolean, var celltype : CellType) {
  def resetCell() : Unit = {
    if(target) celltype = Target()
    else celltype = Empty()
  }

  def isEmpty: Boolean = celltype.isEmpty
}


case class Move(dir : Direction, pushedBox : Boolean)

class MutableSokobanLevel(
     private val field : Array[Array[GameCell]],
     private var player : Point,
     private val nrBoxes : Int
 ) {

  private var nrBoxesOnTarget : Int = 0
  private val moveHistory : mutable.Stack[Move] = new mutable.Stack[Move]

  def this(initField : Seq[Seq[CellType]]) {
    this(
      field= SokobanLogicMutable.makeArray(initField),
      player = SokobanLogicMutable.findPlayer(initField),
      nrBoxes = SokobanLogicMutable.nrBoxes(initField))
  }

  val width: Int = field.head.length
  val height: Int = field.length


  def cellTypeAt(p : Point) : CellType = fieldAt(p).celltype

  def levelDone: Boolean = nrBoxes == nrBoxesOnTarget

  def tryMove(dir: Direction): Unit = {
    val moveTo = player + dir.toPoint
    // if there is a box in that direction,
    // where will it be pushed to?
    val boxPushedTo = moveTo + dir.toPoint
    if(canMoveBox(from = moveTo,to = boxPushedTo)) {
      moveBox(from = moveTo,to = boxPushedTo)
      movePlayer(moveTo)
      moveHistory.push(Move(dir,pushedBox = true))

    } else if(fieldAt(moveTo).isEmpty) {
      movePlayer(moveTo)
      moveHistory.push(Move(dir,pushedBox = false))
    }
  }

  def stepBack(): Unit = {
    if(moveHistory.isEmpty) return
    val lastMove : Move = moveHistory.pop()
    // Example:

    // Before : _P__
    // lastMove = Move(dir = East, pushedBox = False)
    // After :  __P_

    // Before : __PX__
    // lastMove = Move(dir = East, pushedBox = True)
    // After  : ___PX_

    val afterPlayer  = player
    val beforePlayer = afterPlayer + lastMove.dir.opposite.toPoint



    movePlayer(beforePlayer)
    if(lastMove.pushedBox){
      val beforeBox = afterPlayer
      val afterBox = afterPlayer + lastMove.dir.toPoint
      moveBox(from=afterBox, to = beforeBox)
    }
  }

  private def fieldAt(p : Point) : GameCell = field(p.y)(p.x)

  private def canMoveBox(from : Point, to : Point) : Boolean =
    fieldAt(from).celltype == Box() && fieldAt(to).isEmpty

  private def moveBox(from : Point, to : Point): Unit = {
    val moveToCell : GameCell = fieldAt(to)
    moveToCell.celltype = Box()
    if(moveToCell.target) {
      nrBoxesOnTarget = nrBoxesOnTarget  +1
    }
    val movesFromCell : GameCell = fieldAt(from)
    if(movesFromCell.target)  {
      nrBoxesOnTarget = nrBoxesOnTarget - 1
    }
    movesFromCell.resetCell()
  }

  private def movePlayer(to : Point) : Unit = {
    fieldAt(player).resetCell()
    val moveToCell = fieldAt(to)
    moveToCell.celltype = Player()
    player = to
  }



}






object SokobanLogicMutable{

  def makeArray(field : Seq[Seq[CellType]]) : Array[Array[GameCell]] = {
    def makeCell(celltype : CellType): GameCell = {
      new GameCell(celltype == Target(), celltype)
    }
    field.map(_.map(makeCell).toArray).toArray
  }

  def nrBoxes(field : Seq[Seq[CellType]]) : Int =
    field.flatten.count(_ == Box())

  def findPlayer(field : Seq[Seq[CellType]]): Point = {
    for((row,y) <- field.zipWithIndex)
      for((col,x) <- row.zipWithIndex)
        if(col == Player())
          return Point(x,y)
    throw new Error("No player found!")
  }
}