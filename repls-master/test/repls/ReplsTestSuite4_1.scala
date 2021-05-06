package repls

import org.junit.runner.RunWith
import org.scalatest.{Args, Status}
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ReplsTestSuite4_1 extends ReplsTestSuitesBase(
    new IntReplTests,
    new MultiSetTests,
) {
    val MinPointsToPass = 48

    override def run(testName: Option[String], args: Args): Status = {
        val (scoreCounter, res) = runWithScoreCounter(testName, args)
        printf("You got %d/%d points!\n", scoreCounter.points, scoreCounter.maxPoints)
        if (scoreCounter.points >= MinPointsToPass) printf("You pass assignment 4.1!")
        else printf("You do not pass assignment 4.1 yet")
        res
    }
}
