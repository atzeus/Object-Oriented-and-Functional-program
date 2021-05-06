package sokoban.logic


case class SStack[A](l : List[A]) {
  def size : Int = l.length
  def isEmpty : Boolean = l.isEmpty
  def top : A = l.head
  def pop : SStack[A] = SStack(l.tail)
  def push(a : A) : SStack[A] = SStack(a :: l)
}

object SStack{
  def apply[A](a : A) : SStack[A] = SStack(List(a))
  def apply[A]() : SStack[A] = SStack(List())
}