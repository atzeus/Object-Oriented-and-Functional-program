package snake.logic

// you can alter this file!

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
}

case class East()   extends Direction  { def opposite : West   = West()  }
case class North()  extends Direction  { def opposite : South  = South() }
case class West()   extends Direction  { def opposite : East   = East()  }
case class South()  extends Direction  { def opposite : North  = North() }