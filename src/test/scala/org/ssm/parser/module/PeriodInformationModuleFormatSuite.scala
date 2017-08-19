package org.ssm.parser.module

import java.time.DayOfWeek.{MONDAY, TUESDAY, WEDNESDAY}
import java.time.LocalDate
import java.time.Month.{AUGUST, SEPTEMBER}

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.domain.{OneWeekFrequencyRate, PeriodInformation}
import org.ssm.parser.module.PeriodInformationModule.{ExtractedData, format}

import scala.util.Try

@RunWith(classOf[JUnitRunner])
class PeriodInformationModuleFormatSuite extends FunSuite with Matchers {

  trait TestFixture {
    val fromDate = "12AUG17"
    val toDate = "30SEP17"
    val invalidDate = "77JAN17"
    val daysOfOperation = "123"
    val invalidDaysOfOperation = "321"
    val w1FrequncyRate = "W1"
    val invalidFrequencyRate = "W3"
  }

  test("Format raw data - (12AUG17, 30SEP17, 123)") {
    new TestFixture {
      val rawData: ExtractedData = (fromDate, toDate, daysOfOperation, None)
      val actual: PeriodInformation = format(rawData).get

      actual should be(PeriodInformation(
        LocalDate.of(2017, AUGUST, 12),
        LocalDate.of(2017, SEPTEMBER, 30),
        List(MONDAY, TUESDAY, WEDNESDAY),
        OneWeekFrequencyRate))
    }
  }

  test("Format raw data - (12AUG17, 30SEP17, 123, W1)") {
    new TestFixture {
      val rawData: ExtractedData = (fromDate, toDate, daysOfOperation, Some(w1FrequncyRate))
      val actual: PeriodInformation = format(rawData).get

      actual should be(PeriodInformation(
        LocalDate.of(2017, AUGUST, 12),
        LocalDate.of(2017, SEPTEMBER, 30),
        List(MONDAY, TUESDAY, WEDNESDAY),
        OneWeekFrequencyRate))
    }
  }

  test("Format raw data with failure - from date invalid") {
    new TestFixture {
      val rawData: ExtractedData = (invalidDate, toDate, daysOfOperation, None)
      val actual: Try[PeriodInformation] = format(rawData)

      actual.isFailure should be(true)
    }
  }

  test("Format raw data with failure - days of operation invalid") {
    new TestFixture {
      val rawData: ExtractedData = (fromDate, toDate, invalidDaysOfOperation, None)
      val actual: Try[PeriodInformation] = format(rawData)

      actual.isFailure should be(true)
    }
  }

  test("Format raw data with failure - frequency rate invalid") {
    new TestFixture {
      val rawData: ExtractedData = (fromDate, toDate, daysOfOperation, Some(invalidFrequencyRate))
      val actual: Try[PeriodInformation] = format(rawData)

      actual.isFailure should be(true)
    }
  }
}
