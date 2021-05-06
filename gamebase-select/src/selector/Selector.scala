package selector

class Point(var x : Int, var y : Int) {

  def move(rhs : Point) : Unit = {
    x += rhs.x
    y += rhs.y
  }

  def ==(rhs : Point) : Boolean =
    x == rhs.x && y == rhs.y


  def length : Double = scala.math.sqrt(x * x + y * y)

  override def toString : String = "(" + x.toString + "," + y.toString + ")"
}




class Selector() {

  private var cursor : Point = new Point(0,0)

  private var target : Point = new Point(0,0)

  def move(d : Direction) : Unit = {
    val movePoint = pointForDirection(d)
    cursor.move(movePoint)
  }

  def setTarget() : Unit = {
    target = cursor
  }

  def getCellInfo(p : Point) : CellInfo =
    CellInfo(
      hasCursor = p == cursor,
      hasTarget = p == target)

  private def pointForDirection(d: Direction) : Point =
    d match {
      case Left   => new Point(-1, 0)
      case Right  => new Point( 1, 0)
      case Up     => new Point( 0,-1)
      case Down   => new Point( 0, 1)
    }
}



case class CellInfo(hasCursor : Boolean, hasTarget : Boolean)
