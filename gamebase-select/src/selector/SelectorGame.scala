package selector

import engine.GameBase
import engine.graphics.{Color, Point, Rectangle}
import processing.core.{PApplet, PConstants}
import SelectorGame._
import processing.event.{KeyEvent, MouseEvent}
import java.awt.event.KeyEvent._
import selector.{Point => GridPoint}

class SelectorGame extends GameBase{

  val gameLogic : Selector = new Selector()
  val gridHeight = 6
  val gridWidth = 6
  val topMarginPixels : Int = 50
  val totalWidthInPixels: Int = WidthCellInPixels * gridWidth
  val totalHeightInPixels: Int = HeightCellInPixels * gridHeight
  val screenArea: Rectangle = Rectangle(Point(0, 0), totalWidthInPixels, totalHeightInPixels)

  override def draw() : Unit = {
    setFillColor(Color.White)
    drawGrid(screenArea)
  }

  def drawCellOutline(area: Rectangle): Unit = {
    setFillColor(Color.White)
    stroke(0,0,0,255)
    drawRectangle(area)
  }

  def drawCursor(rect: Rectangle) : Unit = {
    stroke(255,255,255,255)
    setFillColor(Color(0,255,0,128))
    drawRectangle(rect.grow(0.9f))
  }

  def drawTarget(rect: Rectangle) : Unit = {
    setFillColor(Color.Red)
    stroke(0,0,0,255)
    drawEllipse(rect.grow(0.7f))
  }


  def drawGrid(gameArea : Rectangle): Unit = {

    for (x <- 0 until gridWidth; y <- 0 until gridHeight) {
      val rect = getCell(gameArea,(x,y))
      drawCellOutline(rect)
      val cellInfo = gameLogic.getCellInfo(new GridPoint(x,y))
      if(cellInfo.hasCursor) drawCursor(rect)
      if(cellInfo.hasTarget) drawTarget(rect)
    }







  }


  def getCell(gameArea : Rectangle, p : (Int,Int)): Rectangle = {
    val leftUp = Point(gameArea.left + p._1 * WidthCellInPixels,
      gameArea.top + p._2 * HeightCellInPixels)
    Rectangle(leftUp, WidthCellInPixels, HeightCellInPixels)
  }




  override def keyPressed(event: KeyEvent): Unit = {

    event.getKeyCode match {
      case VK_LEFT => gameLogic.move(Left)
      case VK_RIGHT => gameLogic.move(Right)
      case VK_DOWN => gameLogic.move(Down)
      case VK_UP   => gameLogic.move(Up)
      case VK_SPACE    => gameLogic.setTarget()
      case _        => ()
    }

  }


  override def settings(): Unit = {
    pixelDensity(displayDensity())
    size(totalWidthInPixels, totalHeightInPixels, PConstants.P2D)
  }

  override def setup(): Unit = {
    // Fonts are loaded lazily, so when we call text()
    // for the first time, there is significant lag.
    // This prevents it from happening during gameplay.
    // text("", 0, 0)
  }

}



object SelectorGame {

  val WidthCellInPixels: Int = 100
  val HeightCellInPixels: Int = WidthCellInPixels

  def main(args:Array[String]): Unit = {
    PApplet.main("selector.SelectorGame")
  }

}