// DO NOT MODIFY FOR BASIC SUBMISSION
// scalastyle:off

package snake.game

import java.awt.event

import processing.core.{PApplet, PConstants}
import processing.event.KeyEvent
import java.awt.event.KeyEvent._

import engine.GameBase
import engine.graphics.{Color, Point, Rectangle}
import snake.logic.{Point => GridPoint}
import snake.game.SnakeGame._
import engine.graphics.Color._
import engine.random.ScalaRandomGen
import snake.logic.{Apple, CellType, Direction, East, Empty, GameLogic, North, SnakeBody, SnakeHead, South, West}

class SnakeGame extends GameBase {

  var gameLogic = new GameLogic(new ScalaRandomGen(),GameLogic.DefaultGridDims)
  val updateTimer = new UpdateTimer(GameLogic.FramesPerSecond)
  val gridDimensions =  gameLogic.gridDims
  val widthInPixels: Int = (GameLogic.DrawSizeFactor * WidthCellInPixels * gridDimensions.width).ceil.toInt
  val heightInPixels: Int = (GameLogic.DrawSizeFactor *  HeightCellInPixels * gridDimensions.height).ceil.toInt
  val screenArea: Rectangle = Rectangle(Point(0, 0), widthInPixels, heightInPixels)

  // this function is wrongly named draw by processing (is called on each update next to drawing)
  override def draw(): Unit = {
    updateState()
    drawGrid()
    if (gameLogic.gameOver) drawGameOverScreen()
  }

  def drawGameOverScreen(): Unit = {
    setFillColor(Red)
    drawTextCentered("GAME OVER!", 20, screenArea.center)
  }

  def drawGrid(): Unit = {

    val widthPerCell = screenArea.width / gridDimensions.width
    val heightPerCell = screenArea.height / gridDimensions.height

    def getCell(p : GridPoint): Rectangle = {
      val leftUp = Point(screenArea.left + p.x * widthPerCell,
        screenArea.top + p.y * heightPerCell)
      Rectangle(leftUp, widthPerCell, heightPerCell)
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
        case SnakeHead(direction) =>
          setFillColor(Color.LawnGreen)
          drawTriangle(getTriangleForDirection(direction, area))
        case SnakeBody(p) =>
          val color = Color.LawnGreen.interpolate(p,Color.DarkGreen)
          setFillColor(color)
          drawRectangle(area)
        case Apple()  =>
          setFillColor(Color.Red)
          drawEllipse(area)
        case Empty() => ()
      }
    }

    setFillColor(White)
    drawRectangle(screenArea)

    for (p <- gridDimensions.allPointsInside) {
      drawCell(getCell(p), gameLogic.getCellType(p))
    }

  }

  /** Method that calls handlers for different key press events.
   * You may add extra functionality for other keys here.
   * See [[event.KeyEvent]] for all defined keycodes.
   *
   * @param event The key press event to handle
   */
  override def keyPressed(event: KeyEvent): Unit = {

    def changeDir(dir: Direction): Unit =
      gameLogic.changeDir(dir)

    event.getKeyCode match {
      case VK_UP    => changeDir(North())
      case VK_DOWN  => changeDir(South())
      case VK_LEFT  => changeDir(West())
      case VK_RIGHT => changeDir(East())
      case VK_R     => gameLogic.setReverse(true)
      case _        => ()
    }

  }

  override def keyReleased(event: KeyEvent): Unit = {
    event.getKeyCode match {
      case VK_R => gameLogic.setReverse(false)
      case _    => ()
    }
  }

  override def settings(): Unit = {
    pixelDensity(displayDensity())
    size(widthInPixels, heightInPixels, PConstants.P2D)
  }

  override def setup(): Unit = {
    // Fonts are loaded lazily, so when we call text()
    // for the first time, there is significant lag.
    // This prevents it from happening during gameplay.
    text("", 0, 0)
    // This should be called last, since the game
    // clock is officially ticking at this point
    updateTimer.init()
  }


  def updateState(): Unit = {
    if (updateTimer.timeForNextFrame()) {
      gameLogic.step()
      updateTimer.advanceFrame()
    }
  }

}


object SnakeGame {


  val WidthCellInPixels: Double = 20 * GameLogic.DrawSizeFactor
  val HeightCellInPixels: Double = WidthCellInPixels

  def main(args: Array[String]): Unit = {
    // This is needed for Processing, using the name
    // of the class in a string is not very beautiful...
    PApplet.main("snake.game.SnakeGame")
  }

}
