package snake.logic

import engine.random.RandomGenerator

/** To implement Snake, complete the ``TODOs`` below.
 *
 * If you need additional files,
 * please also put them in the ``snake`` package.
 */
case class AnApple(snake : Snake, random : RandomGenerator, gridDims : Dimensions){
  var position : Point = setApple()

  def isEaten : Boolean =
    if(snake.body.top.equals(position)) true else false

  def placeNewApple() : Unit = position = setApple()

  def setApple() : Point = {
    var field : SStack[Point] = SStack()
    for(i<- 0 until gridDims.height ; j<- 0 until gridDims.width)
      if(!snake.body.contains(Point(j,i))) field = field.pushBack(Point(j,i))
    if (field.isEmpty) return Point(-1,-1)
    val applePos = random.randomInt(field.size)
    field.index(applePos)
  }
}

case class Snake(gridDims: Dimensions){
  var body : SStack[Point] = SStack(List(Point(2,0), Point(1,0),Point(0,0)))
  var direction : Direction = East()
  var growing : Int = 0

  def grow() : Unit = {
    if(growing == 0){
      body = body.init
    }
    else growing-=1
  }

  def move() : Unit = {
    direction match {
      case East() => body = body.push(Point((body.top.x + 1) % gridDims.width, body.top.y))
      case West() => body = body.push(Point((body.top.x - 1 + gridDims.width) % gridDims.width, body.top.y))
      case North() => body = body.push(Point(body.top.x, (body.top.y - 1 + gridDims.height) % gridDims.height))
      case South() => body = body.push(Point(body.top.x, (body.top.y + 1) % gridDims.height))
    }
  }

  def gameOver : Boolean =
    if (body.pop.contains(body.top)) true else false

  def newDirection(d : Direction) : Unit = direction = d
}

class GameState(random: RandomGenerator, gridDims: Dimensions){
  var snake : Snake = Snake(gridDims)
  var apple : AnApple = AnApple(snake, random, gridDims)
  var newDir : Direction = _
  var reverseFrame : SStack[GameState] = SStack()

  def isGameOver: Boolean = snake.gameOver

  def steps(): Unit = {
    reverseFrame = reverseFrame.push(gameFrame())
    if (newDir != null) snake.newDirection(newDir)
    snake.grow()
    snake.move()
    if (apple.isEaten) {
      snake.growing += 3
      apple.placeNewApple()
    }
  }
  def changeDirection(d: Direction) : Unit =
    if(d != snake.direction.opposite) newDir = d

  def getCellType(p: Point): CellType ={
    if(snake.body.top.equals(p)) SnakeHead(snake.direction)
    else if(snake.body.contains(p)) SnakeBody()
    else if(apple.position.equals(p)) Apple()
    else Empty()
  }

  def gameFrame(): GameState={
    val currGameFrame =  new GameState(random, gridDims)
    currGameFrame.snake.body = snake.body
    currGameFrame.snake.growing = snake.growing
    currGameFrame.snake.direction = snake.direction
    currGameFrame.apple.position = apple.position
    currGameFrame
  }

  def reverseState(): Unit = {
    if(reverseFrame.nonEmpty){
      snake = reverseFrame.top.snake
      apple = reverseFrame.top.apple
      reverseFrame = reverseFrame.pop
    }
  }
}

class GameLogic(val random: RandomGenerator,
                val gridDims : Dimensions) {
  var currGameState: GameState = new GameState(random, gridDims)
  var reverseMode : Boolean = false

  def gameOver: Boolean = currGameState.isGameOver

  def step() : Unit = {
    if(!gameOver && !reverseMode) currGameState.steps()
    else if(reverseMode) currGameState.reverseState()
  }

  def setReverse(r: Boolean) : Unit = reverseMode = r

  def changeDir(d: Direction) : Unit = currGameState.changeDirection(d)

  def getCellType(p : Point) : CellType = currGameState.getCellType(p)
}

/** GameLogic companion object */
object GameLogic {
  val FramesPerSecond: Int = 5 // change this to increase/decrease speed of game

  val DrawSizeFactor = 1.0 // increase this to make the game bigger (for high-res screens)
  val DefaultGridDims
    : Dimensions =
    Dimensions(width = 25, height = 25)  // you can adjust these values to play on a different sized board
}


