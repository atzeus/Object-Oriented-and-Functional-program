package snake

import infrastructure.ScoreCounter
import org.junit.runner.RunWith
import org.scalatest.{Args, Status, Suites}
import org.scalatestplus.junit.JUnitRunner
import snake.BaseSnakeTestSuite.{BaseTests, MaxGrade}
import snake.basic.{DeathTests, GrowthTests, MovementTests, FullGameTests, NoReverseTest, PlacementTests, WrapAroundTests}
import snake.reverse.ReverseTests

abstract class SnakeTestSuite(suites: SnakeTestSuiteBase*) extends Suites(BaseTests ++ suites: _*) {
    override def run(testName: Option[String], args: Args): Status = {
        val scoreCounter = new ScoreCounter()
        val newArgs =
            args.copy(configMap = args.configMap.updated("scoreCounter",scoreCounter))
        val res = runDirect(testName,newArgs)
        printf("You got %d/%d points!\n", scoreCounter.points, scoreCounter.maxPoints)
        printf("Your grade for the repls exercise will be : %.2f\n",scoreCounter.fraction() * MaxGrade)
        res
    }

    // run without making a new scorecounter
    def runDirect(testName: Option[String], args: Args): Status = {
        super.run(testName, args)
    }
}

object BaseSnakeTestSuite {
    val MaxGrade = 10
    val BaseTests: Seq[SnakeTestSuiteBase] = Seq[SnakeTestSuiteBase](
      new PlacementTests,
      new MovementTests,
      new GrowthTests,
      new  WrapAroundTests,
      new DeathTests,
      new FullGameTests
    )
}


