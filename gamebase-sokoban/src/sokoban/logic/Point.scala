package sokoban.logic

case class Point(x : Int, y : Int) {
  def +(rhs : Point) : Point = Point(x + rhs.x, y + rhs.y)
}
