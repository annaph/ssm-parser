package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.domain._
import org.ssm.parser.module.PeriodInformationModule.formatFrequencyRate

import scala.util.Try

@RunWith(classOf[JUnitRunner])
class PeriodInformationModuleFrequencyRateSuite extends FunSuite with Matchers {

  test("Format frequency rate - W1") {
    val frequencyRate = "W1"
    val actual: FrequencyRate = formatFrequencyRate(frequencyRate).get

    actual should be(OneWeekFrequencyRate)
  }

  test("Format frequency rate - W2") {
    val frequencyRate = "W2"
    val actual: FrequencyRate = formatFrequencyRate(frequencyRate).get

    actual should be(TwoWeekFrequencyRate)
  }


  test("Format frequency rate with failure - W3") {
    val frequencyRate = "W3"
    val actual: Try[FrequencyRate] = formatFrequencyRate(frequencyRate)

    actual.isFailure should be(true)
  }

}
