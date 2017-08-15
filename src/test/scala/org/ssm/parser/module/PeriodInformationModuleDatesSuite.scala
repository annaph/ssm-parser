package org.ssm.parser.module

import java.time.LocalDate
import java.time.LocalDate.now
import java.time.Month.{AUGUST, SEPTEMBER}

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

    from should be(LocalDate.of(2017, AUGUST, 12))
    to should be(LocalDate.of(2017, SEPTEMBER, 30))
  }

  test("Format dates - 12AUG17 & 12AUG17") {
    val fromDate = "12AUG17"
    val toDate = "12AUG17"

    val (from, to): (LocalDate, LocalDate) = formatDates(fromDate, toDate).get

    from should be(LocalDate.of(2017, AUGUST, 12))
    to should be(LocalDate.of(2017, AUGUST, 12))
  }

  test("Format dates with failure - 30SEP17 & 12AUG17") {
    val fromDate = "30SEP17"
    val toDate = "12AUG17"

    val dates: Try[(LocalDate, LocalDate)] = formatDates(fromDate, toDate)

    dates.isFailure should be(true)
  }

  test("Format dates - 12AUG & 30SEP") {
    val fromDate = "12AUG"
    val toDate = "30SEP"

    val (from, to): (LocalDate, LocalDate) = formatDates(fromDate, toDate).get

    from should be(LocalDate.of(now.getYear, AUGUST, 12))
    to should be(LocalDate.of(now.getYear, SEPTEMBER, 30))
  }

  test("Format dates - 12AUG & 12AUG") {
    val fromDate = "12AUG"
    val toDate = "12AUG"

    val (from, to): (LocalDate, LocalDate) = formatDates(fromDate, toDate).get

    from should be(LocalDate.of(now.getYear, AUGUST, 12))
    to should be(LocalDate.of(now.getYear, AUGUST, 12))
  }

  test("Format dates with failure - 30SEP & 12AUG") {
    val fromDate = "30SEP"
    val toDate = "12AUG"

    val dates: Try[(LocalDate, LocalDate)] = formatDates(fromDate, toDate)

    dates.isFailure should be(true)
  }

  test("Format dates - 12AUG17 & 30SEP") {
    val fromDate = "12AUG17"
    val toDate = "30SEP"

    val (from, to): (LocalDate, LocalDate) = formatDates(fromDate, toDate).get

    from should be(LocalDate.of(2017, AUGUST, 12))
    to should be(LocalDate.of(2017, SEPTEMBER, 30))
  }

  test("Format dates - 12AUG & 30SEP17") {
    val fromDate = "12AUG"
    val toDate = "30SEP17"

    val (from, to): (LocalDate, LocalDate) = formatDates(fromDate, toDate).get

    from should be(LocalDate.of(2017, AUGUST, 12))
    to should be(LocalDate.of(2017, SEPTEMBER, 30))
  }

  test("Format dates with failure - 30SEP17 & 12AUG") {
    val fromDate = "30SEP17"
    val toDate = "12AUG"

    val dates: Try[(LocalDate, LocalDate)] = formatDates(fromDate, toDate)

    dates.isFailure should be(true)
  }

  test("Format dates with failure - 30SEP & 12AUG17") {
    val fromDate = "30SEP"
    val toDate = "12AUG17"

    val dates: Try[(LocalDate, LocalDate)] = formatDates(fromDate, toDate)

    dates.isFailure should be(true)
  }
}
