package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.module.PeriodInformationModule.{ExtractedData, extract}

@RunWith(classOf[JUnitRunner])
class PeriodInformationModuleExtractSuite extends FunSuite with Matchers {

  test("Extract input - 12AUG 30SEP 1234567") {
    val actual: ExtractedData = extract(0 -> "12AUG 30SEP 1234567")

    actual should be("12AUG", "30SEP", "1234567", None)
  }

  test("Extract input - 12AUG 30SEP 1234567 6/LX545A/1") {
    val actual: ExtractedData = extract(0 -> "12AUG 30SEP 1234567 6/LX545A/1")

    actual should be("12AUG", "30SEP", "1234567", None)
  }

  test("Extract input - 12AUG 30SEP 1234567/W1") {
    val actual: ExtractedData = extract(0 -> "12AUG 30SEP 1234567/W1")

    actual should be("12AUG", "30SEP", "1234567", Some("W1"))
  }

  test("Extract input - 12AUG 30SEP 1234567/W1 6/LX545A/1") {
    val actual: ExtractedData = extract(0 -> "12AUG 30SEP 1234567/W1 6/LX545A/1")

    actual should be("12AUG", "30SEP", "1234567", Some("W1"))
  }

  test("Extract input - 12AUG 30SEP 1234567/W2 6/LX545A/1") {
    val actual: ExtractedData = extract(0 -> "12AUG 30SEP 1234567/W2 6/LX545A/1")

    actual should be("12AUG", "30SEP", "1234567", Some("W2"))
  }
}
