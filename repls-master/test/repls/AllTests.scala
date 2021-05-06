// DO NOT MODIFY THIS FILE
package repls

import org.junit.runner.RunWith
import org.scalatest.{Args, Status, Suites}
import org.scalatestplus.junit.JUnitRunner
import repls.AllTests._
import repls.infrastructure.ScoreCounter

@RunWith(classOf[JUnitRunner])
class AllTests extends Suites(
    new IntReplTests,
    new MultiSetReplTests,
    new MultiSetTests
) {

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

object AllTests {
    val MaxGrade = 10
}
