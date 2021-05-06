package sokoban.game

import sokoban.logic.Point


/** Given a direction `d` : you can decide
 * which one it is as follows:
 *
 * {{{
 * d match {
 *   case East() => ...
 *   case North() => ...
 *   case West() => ...
 *   case South() => ...
 * }
 * }}}
 */
sealed abstract class Direction {
  def opposite : Direction
  def toPoint : Point
}

case class East()   extends Direction  {
  def opposite = West()
  def toPoint = Point(1,0)
}
case class North()  extends Direction  {
  def opposite = South()
  def toPoint: Point = Point(0,-1)
}
case class West()   extends Direction  {
  def opposite = East()
  def toPoint = Point(-1,0)
}
case class South()  extends Direction  {
  def opposite = North()
  def toPoint = Point(0,1)
}
