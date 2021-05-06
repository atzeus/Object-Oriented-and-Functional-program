package sokoban.game

sealed abstract class CellType {
  val isEmpty : Boolean
}
case class Player() extends CellType {
  override val isEmpty: Boolean = false
}
case class Box() extends CellType {
  override val isEmpty: Boolean = false
}
case class Wall() extends CellType {
  override val isEmpty: Boolean = false
}
case class Target() extends CellType {
  override val isEmpty: Boolean = true
}
case class Empty() extends CellType {
  override val isEmpty: Boolean = true
}

object CellType {
  def nonEmptyCellTypes : Set[CellType] = Set(Player(), Box(), Wall(), Target())
}
