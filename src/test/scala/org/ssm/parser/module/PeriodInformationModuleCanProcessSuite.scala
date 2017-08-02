package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalatest.{FunSuite, Matchers}
import org.scalatest.junit.JUnitRunner
import org.ssm.parser.SSMProcess.Input
import PeriodInformationModule.canProcess
import org.scalacheck.Gen
import org.scalacheck.Arbitrary.arbitrary

@RunWith(classOf[JUnitRunner])
class PeriodInformationModuleCanProcessSuite extends FunSuite with Matchers {

  test("Should be able to process valid PeriodInformation line") {
    val actual: Boolean = canProcess(0 -> "12AUG17 30SEP17 1234567/W1 6/LX545A/1")

    actual should be(true)

  }

  test("Should not be able to process invalid PeriodInformation line") {
    val actual: Boolean = canProcess(0 -> "invalid")

    actual should be(false)
  }

  test("Can process valid FlightInformation line") {
    ???
  }

  test("Cannot process invalid FlightInformation line") {
    ???
  }

  def validPeriodInformationLineGen: Gen[String] =
    for {
      fromDate <- dateGen
      toDate <- dateGen
      daysOfOperation <- daysOfOperationGen
      rest <- restGen
    } yield fromDate + toDate + daysOfOperation + rest

  def invalidPeriodInformationLineGen: Gen[String] =
    for {
      hasFromDate <- arbitrary[Boolean]
      hasToDate <- arbitrary[Boolean]
      hasDaysOfOperation <- arbitrary[Boolean]
      fromDate <- if (hasFromDate) dateGen else Gen.const("")
      toDate <- if (hasToDate) dateGen else Gen.const("")
      daysOfOperation <- if (hasDaysOfOperation) daysOfOperationGen else Gen.const("")
      rest <- restGen
    } yield {
      if (hasFromDate && hasToDate && hasDaysOfOperation)
        rest
      else
        fromDate + toDate + daysOfOperation + rest
    }

  def dateGen: Gen[String] =
    for {
      hasYearChars <- arbitrary[Boolean]
      firstChar <- Gen.numChar.map(_.toString)
      secondChar <- Gen.numChar.map(_.toString)
      thirdChar <- Gen.alphaUpperChar.map(_.toString)
      fourthChar <- Gen.alphaUpperChar.map(_.toString)
      fifthChar <- Gen.alphaUpperChar.map(_.toString)
      sixthChar <- if (hasYearChars) Gen.numChar.map(_.toString) else Gen.const("")
      seventhChar <- if (hasYearChars) Gen.numChar.map(_.toString) else Gen.const("")
    } yield (
      firstChar
        + secondChar
        + thirdChar
        + fourthChar
        + fifthChar
        + sixthChar
        + seventhChar
      )

  def daysOfOperationGen: Gen[String] =
    for {
      hasFirstChar <- arbitrary[Boolean]
      hasSecondChar <- arbitrary[Boolean]
      hasThirdChar <- arbitrary[Boolean]
      hasFourthChar <- arbitrary[Boolean]
      hasFifthChar <- arbitrary[Boolean]
      hasSixthChar <- arbitrary[Boolean]
      hasSeventhChar <- arbitrary[Boolean]
      firstChar <- if (hasFirstChar) Gen.const("1") else Gen.const("")
      secondChar <- if (hasSecondChar) Gen.const("2") else Gen.const("")
      thirdChar <- if (hasThirdChar) Gen.const("3") else Gen.const("")
      fourthChar <- if (hasFourthChar) Gen.const("4") else Gen.const("")
      fifthChar <- if (hasFifthChar) Gen.const("5") else Gen.const("")
      sixthChar <- if (hasSixthChar) Gen.const("6") else Gen.const("")
      seventhChar <- if (hasSeventhChar) Gen.const("7") else Gen.const("")
    } yield (
      firstChar
        + secondChar
        + thirdChar
        + fourthChar
        + fifthChar
        + sixthChar
        + seventhChar
      )

  def restGen: Gen[String] =
    for {
      hasFrequencyRateChar <- arbitrary[Boolean]
      rest <- {
        if (hasFrequencyRateChar)
          Gen.oneOf(List(true, false)) flatMap {
            if (_) Gen.const("/W1") else Gen.const("/W2")
          }
        else
          Gen.oneOf(List(true, false)) flatMap {
            if (_) Gen.alphaStr else Gen.const("")
          }
      }
    } yield rest
}
