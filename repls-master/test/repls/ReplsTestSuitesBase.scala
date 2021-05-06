package repls

import org.scalatest.{Args, Status, Suites}
import repls.infrastructure.{ScoreCounter, TestBase}

class ReplsTestSuitesBase(suites: TestBase*) extends Suites(suites: _*) {

    def runWithScoreCounter(testName: Option[String], args: Args): (ScoreCounter, Status) = {
        val scoreCounter = new ScoreCounter()
        val newArgs = args.copy(configMap = args.configMap.updated("scoreCounter", scoreCounter))
        val res = runDirect(testName, newArgs)
        (scoreCounter, res)
    }

    def runDirect(testName: Option[String], args: Args): Status = {
        super.run(testName, args)
    }
}
