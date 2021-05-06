package repls

case class SStack[A](l : List[A]) {
  def size : Int = l.length
  def isEmpty : Boolean = l.isEmpty
  def nonEmpty: Boolean = l.nonEmpty
  def contains(a:A) : Boolean = l.contains(a)
  def top : A = l.head
  def pop : SStack[A] = SStack(l.tail)
  def push(a : A) : SStack[A] = SStack(a :: l)
  def pushBack(a: A): SStack[A] = SStack(l:+ a)
  def index(n :Int): A = l(n)
  def init: SStack[A] = SStack(l.init)
}

object SStack{
  def apply[A](a : A) : SStack[A] = SStack(List(a))
  def apply[A]() : SStack[A] = SStack(List())
}
