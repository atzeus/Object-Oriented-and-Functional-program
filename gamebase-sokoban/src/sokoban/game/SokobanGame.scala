package sokoban.game

import engine.GameBase
import engine.graphics.{Color, Point, Rectangle}
import sokoban.logic.{SokobanLogic, SokobanLogicMutable}
import engine.graphics.Color._
import processing.event.KeyEvent
import java.awt.event.KeyEvent._

import processing.core.{PApplet, PConstants}


class SokobanGame extends GameBase {

  // var gameLogic = new SokobanLogic()
  var gameLogic = new SokobanLogicMutable()
  val widthInPixels: Int = 1224
  val heightInPixels: Int = 800
  val screenArea: Rectangle = Rectangle(Point(0, 0), widthInPixels, heightInPixels)

  // this function is wrongly named draw by processing (is called on each update next to drawing)
  override def draw(): Unit = {
    drawGrid()
    if(gameLogic.allLevelsDone) drawAllLevelsDoneText()
    if (gameLogic.levelDone) drawLevelDoneText()
  }

  def drawAllLevelsDoneText(): Unit = {
    setFillColor(Green)
    drawTextCentered("This is absolutely ridiculous.... Did you check what time it is?", 20, screenArea.center)
  }

  def drawLevelDoneText(): Unit = {
    setFillColor(Red)
    drawTextCentered("Level finished!", 40, screenArea.center)
  }

  def drawGrid(): Unit = {

    val widthInCells = gameLogic.nrColumns
    val heightInCells = gameLogic.nrRows
    var widthPerCell = (screenArea.width / widthInCells) min SokobanGame.MaxCellWidth
    var heightPerCell = (screenArea.height / heightInCells) min SokobanGame.MaxCellHeigth
    val cellEdge = widthPerCell min heightPerCell
    val usedScreenWidthPixels = cellEdge * widthInCells
    val usedScreenHeightPixels = cellEdge * heightInCells
    val left = screenArea.left + (screenArea.width - usedScreenWidthPixels) / 2
    val top = screenArea.top + (screenArea.height - usedScreenHeightPixels) / 2
    val actualScreen = Rectangle(Point(left,top), usedScreenWidthPixels,usedScreenHeightPixels)

    def getCell(colIndex: Int, rowIndex: Int): Rectangle = {
      val leftUp = Point(actualScreen.left + colIndex * cellEdge,
        actualScreen.top + rowIndex * cellEdge)
      Rectangle(leftUp, cellEdge, cellEdge)
    }

    def getTriangleForDirection(dir: Direction, area: Rectangle) = {
      dir match {
        case West()   => area.trianglePointingLeft
        case North()  => area.trianglePointingUp
        case East()   => area.trianglePointingRight
        case South()  => area.trianglePointingDown
      }
    }

    def drawCell(area: Rectangle, cell: CellType): Unit = {
      cell match {
        case Player() =>
          setFillColor(Color.LawnGreen)
          drawTriangle(getTriangleForDirection(North(), area.grow(0.9f)))
        case Target()  =>
          setFillColor(Color.White)
          drawRectangle(area.grow(0.2f))
        case Box() =>
          val color = Color.Yellow
          setFillColor(color)
          drawRectangle(area.grow(0.97f))

        case Wall() =>
          setFillColor(Color.Red)
          drawRectangle(area)
        case Empty() => ()
      }
    }

    setFillColor(White)
    drawRectangle(screenArea)

    for (y <- 0 until gameLogic.nrRows;
         x <- 0 until gameLogic.nrColumns) {
      drawCell(getCell(x, y), gameLogic.cellType(x, y))
    }

  }

  /** Method that calls handlers for different key press events.
   * You may add extra functionality for other keys here.
   * See [[event.KeyEvent]] for all defined keycodes.
   *
   * @param event The key press event to handle
   */
  override def keyPressed(event: KeyEvent): Unit = {

    event.getKeyCode match {
      case VK_UP    =>  gameLogic.tryMove(North())
      case VK_DOWN  =>  gameLogic.tryMove(South())
      case VK_LEFT  =>  gameLogic.tryMove(West())
      case VK_RIGHT =>  gameLogic.tryMove(East())
      case VK_R     => gameLogic.stepBack()
      case _        => ()
    }

  }

  override def keyReleased(event: KeyEvent): Unit = {

  }

  override def settings(): Unit = {
    pixelDensity(displayDensity())
    size(widthInPixels, heightInPixels, PConstants.P2D)
    keyRepeatEnabled = true
  }

  override def setup(): Unit = {
    // Fonts are loaded lazily, so when we call text()
    // for the first time, there is significant lag.
    // This prevents it from happening during gameplay.
    text("", 0, 0)
    // This should be called last, since the game
    // clock is officially ticking at this point
  }



}


object SokobanGame {

  val MaxCellWidth : Int = 100
  val MaxCellHeigth : Int = MaxCellWidth

  def main(args: Array[String]): Unit = {
    // This is needed for Processing, using the name
    // of the class in a string is not very beautiful...
    PApplet.main("sokoban.game.SokobanGame")
  }

}