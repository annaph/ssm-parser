package org.ssm.parser.module

import java.time.Month
import java.time.Month._

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.module.SSMModule.toMonth

@RunWith(classOf[JUnitRunner])
class SSMModuleToMonthSuite extends FunSuite with Matchers {

  test("January converstion") {
    val str = "JAN"
    val actual: Month = toMonth(str).get

    actual.getValue should be(JANUARY.getValue)
  }

  test("February converstion") {
    val str = "FEB"
    val actual: Month = toMonth(str).get

    actual.getValue should be(FEBRUARY.getValue)
  }

  test("MARCH converstion") {
    val str = "MAR"
    val actual: Month = toMonth(str).get

    actual.getValue should be(MARCH.getValue)
  }

  test("April converstion") {
    val str = "APR"
    val actual: Month = toMonth(str).get

    actual.getValue should be(APRIL.getValue)
  }

  test("May converstion") {
    val str = "MAY"
    val actual: Month = toMonth(str).get

    actual.getValue should be(MAY.getValue)
  }

  test("June converstion") {
    val str = "JUN"
    val actual: Month = toMonth(str).get

    actual.getValue should be(JUNE.getValue)
  }

  test("July converstion") {
    val str = "JUL"
    val actual: Month = toMonth(str).get

    actual.getValue should be(JULY.getValue)
  }

  test("August converstion") {
    val str = "AUG"
    val actual: Month = toMonth(str).get

    actual.getValue should be(AUGUST.getValue)
  }

  test("September converstion") {
    val str = "SEP"
    val actual: Month = toMonth(str).get

    actual.getValue should be(SEPTEMBER.getValue)
  }

  test("October converstion") {
    val str = "OCT"
    val actual: Month = toMonth(str).get

    actual.getValue should be(OCTOBER.getValue)
  }

  test("November converstion") {
    val str = "NOV"
    val actual: Month = toMonth(str).get

    actual.getValue should be(NOVEMBER.getValue)
  }

  test("December converstion") {
    val str = "DEC"
    val actual: Month = toMonth(str).get

    actual.getValue should be(DECEMBER.getValue)
  }

  test("Non-existing month converstion") {
    val str = "non-existing"
    val actual: Option[Month] = toMonth(str)

    actual should be(None)
  }
}
