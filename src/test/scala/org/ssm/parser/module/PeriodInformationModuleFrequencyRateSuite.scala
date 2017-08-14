package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.domain._
import org.ssm.parser.module.PeriodInformationModule.formatFrequencyRate

import scala.util.{Success, Try}

@RunWith(classOf[JUnitRunner])
class PeriodInformationModuleFrequencyRateSuite extends FunSuite with Matchers {

  test("Format frequency rate - /W1") {
    val frequencyRate = "/W1"
    val actual: Try[FrequencyRate] = formatFrequencyRate(frequencyRate)

    actual should be(Success(OneWeekFrequencyRate))
  }

  test("Format frequency rate - /W1 6/LX545A/1") {
    val frequencyRate = "/W1 6/LX545A/1"
    val actual: Try[FrequencyRate] = formatFrequencyRate(frequencyRate)

    actual should be(Success(OneWeekFrequencyRate))
  }

  test("Format frequency rate - /W2") {
    val frequencyRate = "/W2"
    val actual: Try[FrequencyRate] = formatFrequencyRate(frequencyRate)

    actual should be(Success(TwoWeekFrequencyRate))
  }

  test("Format frequency rate - /W2 6/LX545A/1") {
    val frequencyRate = "/W2 6/LX545A/1"
    val actual: Try[FrequencyRate] = formatFrequencyRate(frequencyRate)

    actual should be(Success(TwoWeekFrequencyRate))
  }

  test("""Format default frequency rate - """"") {
    val frequencyRate = ""
    val actual: Try[FrequencyRate] = formatFrequencyRate(frequencyRate)

    actual should be(Success(OneWeekFrequencyRate))
  }

  test("Format default frequency rate - _6/LX545A/1") {
    val frequencyRate = " 6/LX545A/1"
    val actual: Try[FrequencyRate] = formatFrequencyRate(frequencyRate)

    actual should be(Success(OneWeekFrequencyRate))
  }

  test("Format frequency rate with failure - /") {
    val frequencyRate = "/"
    val actual: Try[FrequencyRate] = formatFrequencyRate(frequencyRate)

    actual.isFailure should be(true)
  }

  test("Format frequency rate with failure - /W3") {
    val frequencyRate = "/W3"
    val actual: Try[FrequencyRate] = formatFrequencyRate(frequencyRate)

    actual.isFailure should be(true)
  }

  test("Format frequency rate with failure - 6/LX545A/1") {
    val frequencyRate = "6/LX545A/1"
    val actual: Try[FrequencyRate] = formatFrequencyRate(frequencyRate)

    actual.isFailure should be(true)
  }
}
