package repls

import org.junit.runner.RunWith
import org.scalatest.{Args, Status}
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ReplsTestSuite4_2 extends ReplsTestSuitesBase(
    new IntReplTests,
    new MultiSetTests,
    new MultiSetReplTests,
) {
    val MaxPoints = 5.5

    override def run(testName: Option[String], args: Args): Status = {
        val (scoreCounter, res) = runWithScoreCounter(testName, args)
        printf("You got %d/%d points!\n", scoreCounter.points, scoreCounter.maxPoints)
        printf("Your base grade for the repls exercise will be: %.2f\n", scoreCounter.fraction() * MaxPoints)
        res
    }
}
