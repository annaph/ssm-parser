package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Prop}
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.domain._
import org.ssm.parser.module.FlightInformationModule._

import scala.util.{Failure, Success, Try}

@RunWith(classOf[JUnitRunner])
class FlightInformationModuleSuite extends FunSuite with Matchers with Checkers {

  implicit val actionAr: Arbitrary[String] = Arbitrary {
    for {
      hasPrefix <- arbitrary[Boolean]
      hasSuffix <- arbitrary[Boolean]
      hasActionRef <- arbitrary[Boolean]
      isValidAction <- arbitrary[Boolean]
      prefix <- if (hasPrefix) Gen.alphaStr else Gen.const("")
      suffix <- if (hasSuffix) Gen.alphaStr else Gen.const("")
      middle <- {
        if (hasActionRef && isValidAction)
          validActionGen
        else if (hasActionRef && !isValidAction)
          invalidActionGen
        else
          Gen.const("")
      }
    } yield prefix + middle + suffix
  }

  trait TestFixture {
    val input: Input = 0 -> "LX544A 1/LX/LH 3/LX 4/LH 5/LX 9/LX"
    val invalidInput: Input = 0 -> "invalid"

    val subMessage1 = SubMessage(Some(NEW), None, List())
    val subMessage2 = SubMessage(Some(CNL), None, List())
    val state = SSMMessage(Some("24MAY00144E003"), List(subMessage1, subMessage2))
  }

  test("Should be able to process valid FlightInformation line") {
    new TestFixture {
      val actual: Boolean = canProcess(input)

      actual should be(true)
    }
  }

  test("Should not be able to process invalid FlightInformation line") {
    new TestFixture {
      val actual: Boolean = canProcess(invalidInput)

      actual should be(false)
    }
  }

  test("Can process line as FlightInformation line") {
    val propAction: Prop = forAll { (line: String) =>
      val canProcessLine = canProcess(0 -> line)

      if (line.size < 5)
        canProcessLine == false
      else {
        val isThirdCharUpper = line(2).isUpper
        val startIndex = if (isThirdCharUpper) 3 else 2

        Try {
          line.substring(startIndex, startIndex + 3).toInt
        } match {
          case Success(_) =>
            canProcessLine == true
          case Failure(_) =>
            canProcessLine == false
        }
      }
    }

    check(propAction)
  }

  test("Extract input - LX983") {
    val actual: (String, String) = extract(0 -> "LX983")

    actual should be("LX" -> "983")
  }

  test("Extract input - LX983A 1/LX/LH") {
    val actual: (String, String) = extract(0 -> "LX983A 1/LX/LH")

    actual should be("LX" -> "983")
  }

  test("Extract input - LX1074A 1/LX/LH") {
    val actual: (String, String) = extract(0 -> "LX1074A 1/LX/LH")

    actual should be("LX" -> "1074")
  }

  test("Extract input - LXA983A 1/LX/LH") {
    val actual: (String, String) = extract(0 -> "LXA983A 1/LX/LH")

    actual should be("LXA" -> "983")
  }

  test("Extract input - LXA1074A 1/LX/LH") {
    val actual: (String, String) = extract(0 -> "LXA1074A 1/LX/LH")

    actual should be("LXA" -> "1074")
  }

  test("Format raw data - (LX, 983)") {
    val actual: Try[FlightDesignator] = format("LX" -> "983")

    actual should be(Success(FlightDesignator("LX", 983)))
  }

  test("Process input") {
    new TestFixture {
      val actual: SSMMessage = process(input, state)

      actual.messageReference should be(state.messageReference)
      actual.subMessages should be(List(
        subMessage1.copy(flightDesignator = Some(FlightDesignator("LX", 544))),
        subMessage2))
    }
  }

  def validActionGen: Gen[String] =
    for {
      hasThirdChar <- arbitrary[Boolean]
      hasSeventhChar <- arbitrary[Boolean]
      firstChar <- Gen.alphaChar.map(_.toString)
      secondChar <- Gen.alphaChar.map(_.toString)
      thirdChar <- if (hasThirdChar) Gen.alphaUpperChar.map(_.toString) else Gen.const("")
      fourthChar <- Gen.numChar.map(_.toString)
      fifthChar <- Gen.numChar.map(_.toString)
      sixthChar <- Gen.numChar.map(_.toString)
      seventhChar <- if (hasSeventhChar) Gen.numChar.map(_.toString) else Gen.const("")
    } yield (
      firstChar
        + secondChar
        + thirdChar
        + fourthChar
        + fifthChar
        + sixthChar
        + seventhChar
      )

  def invalidActionGen: Gen[String] =
    for {
      hasFirstChar <- arbitrary[Boolean]
      hasSecondChar <- arbitrary[Boolean]
      hasThirdChar <- arbitrary[Boolean]
      hasFourthChar <- arbitrary[Boolean]
      hasFifthChar <- arbitrary[Boolean]
      hasSixthChar <- arbitrary[Boolean]
      hasSeventhChar <- arbitrary[Boolean]
      firstChar <- if (hasFirstChar) Gen.alphaChar.map(_.toString) else Gen.const("")
      secondChar <- if (hasSecondChar) Gen.alphaChar.map(_.toString) else Gen.const("")
      thirdChar <- if (hasThirdChar) Gen.alphaUpperChar.map(_.toString) else Gen.const("")
      fourthChar <- if (hasFourthChar) Gen.numChar.map(_.toString) else Gen.const("")
      fifthChar <- if (hasFifthChar) Gen.numChar.map(_.toString) else Gen.const("")
      sixthChar <- if (hasSixthChar) Gen.numChar.map(_.toString) else Gen.const("")
      seventhChar <- if (hasSeventhChar) Gen.numChar.map(_.toString) else Gen.const("")
    } yield (
      firstChar
        + secondChar
        + thirdChar
        + fourthChar
        + fifthChar
        + sixthChar
        + seventhChar
      )
}
