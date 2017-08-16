package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Prop}
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.module.FlightInformationModule.canProcess

import scala.util.{Failure, Success, Try}

@RunWith(classOf[JUnitRunner])
class FlightInformationModuleCanProcessSuite extends FunSuite with Matchers with Checkers {

  implicit val actionAr: Arbitrary[String] = Arbitrary {
    for {
      hasPrefix <- arbitrary[Boolean]
      hasSuffix <- arbitrary[Boolean]
      hasDesignatorNumber <- arbitrary[Boolean]
      isDesignatorNumberValid <- arbitrary[Boolean]
      prefix <- if (hasPrefix) Gen.alphaStr else Gen.const("")
      suffix <- if (hasSuffix) Gen.alphaStr else Gen.const("")
      middle <- {
        if (hasDesignatorNumber && isDesignatorNumberValid)
          validDesignatorNumberGen
        else if (hasDesignatorNumber && !isDesignatorNumberValid)
          invalidDesignatorNumberGen
        else
          Gen.const("")
      }
    } yield prefix + middle + suffix
  }

  test("Should be able to process valid FlightInformation line") {
    val actual: Boolean = canProcess(0 -> "LX544A 1/LX/LH 3/LX 4/LH 5/LX 9/LX")

    actual should be(true)
  }

  test("Should not be able to process invalid FlightInformation line") {
    val actual: Boolean = canProcess(0 -> "invalid")

    actual should be(false)
  }

  test("Can process line as FlightInformation line") {
    val propAction: Prop = forAll { (line: String) =>
      val canProcessLine = canProcess(0 -> line)

      if (line.length < 5)
        !canProcessLine
      else {
        val isThirdCharUpper = line(2).isUpper
        val startIndex = if (isThirdCharUpper) 3 else 2

        Try {
          line.substring(startIndex, startIndex + 3).toInt
        } match {
          case Success(_) =>
            canProcessLine
          case Failure(_) =>
            !canProcessLine
        }
      }
    }

    check(propAction)
  }

  def validDesignatorNumberGen: Gen[String] =
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

  def invalidDesignatorNumberGen: Gen[String] =
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
