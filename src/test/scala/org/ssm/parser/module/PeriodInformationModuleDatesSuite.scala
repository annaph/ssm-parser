package org.ssm.parser.module

import java.time.LocalDate
import java.time.Month.{JANUARY, SEPTEMBER}

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.module.PeriodInformationModule.formatDates

import scala.util.Try

@RunWith(classOf[JUnitRunner])
class PeriodInformationModuleDatesSuite extends FunSuite with Matchers {

  test("Format dates - 12AUG17 & 30SEP17") {
    val fromDate = "12AUG17"
    val toDate = "30SEP17"

    val (from, to): (LocalDate, LocalDate) = formatDates(fromDate, toDate).get

    from should be(LocalDate.of(2017, JANUARY, 12))
    to should be(LocalDate.of(2017, SEPTEMBER, 30))
  }

  test("Format dates - 12AUG17 & 12AUG17") {
    val fromDate = "12AUG17"
    val toDate = "12AUG17"

    val (from, to): (LocalDate, LocalDate) = formatDates(fromDate, toDate).get

    from should be(LocalDate.of(2017, JANUARY, 12))
    to should be(LocalDate.of(2017, JANUARY, 12))
  }

  test("Format dates with failure - 30SEP17 & 12AUG17") {
    val fromDate = "30SEP17"
    val toDate = "12AUG17"

    val dates: Try[(LocalDate, LocalDate)] = formatDates(fromDate, toDate)

    dates.isFailure should be(true)
  }
}
