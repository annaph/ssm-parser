package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Prop}
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.module.MessageReferenceModule.canProcess

import scala.util.{Failure, Success, Try}

@RunWith(classOf[JUnitRunner])
class MessageReferenceModuleCanProcessSuite extends FunSuite with Matchers with Checkers {

  implicit val messageRefAr: Arbitrary[String] = Arbitrary {
    for {
      hasPrefix <- arbitrary[Boolean]
      hasSuffix <- arbitrary[Boolean]
      hasMessageRef <- arbitrary[Boolean]
      isValidMessageRef <- arbitrary[Boolean]
      prefix <- if (hasPrefix) Gen.alphaStr else Gen.const("")
      suffix <- if (hasSuffix) Gen.alphaStr else Gen.const("")
      middle <- {
        if (hasMessageRef && isValidMessageRef)
          validMessageRefGen
        else if (hasMessageRef && !isValidMessageRef)
          invalidMessageRefGen
        else
          Gen.const("")
      }
    } yield prefix + middle + suffix
  }

  test("Should be able to process valid MessageReference line - 24MAY00144E003/REF 123/449") {
    val actual = canProcess(0 -> "24MAY00144E003/REF 123/449")

    actual should be(true)
  }

  test("Should be able to process valid MessageReference line - 24MAY00144E003") {
    val actual = canProcess(0 -> "24MAY00144E003")

    actual should be(true)
  }

  test("Should not be able to process invalid MessageReference line") {
    val actual = canProcess(0 -> "invalid")

    actual should be(false)
  }

  test("Can process line as MessageReference line") {
    val propMessageRefrence: Prop = forAll { (line: String) =>
      val canProcessLine = canProcess(0 -> line)

      if (line.length < 14)
        !canProcessLine
      else {
        Try {
          line.substring(0, 1).toInt
          line.substring(5, 10).toInt
          line.substring(11, 14).toInt
        } match {
          case Success(_) =>
            canProcessLine
          case Failure(_) =>
            !canProcessLine
        }
      }
    }

    check(propMessageRefrence)
  }

  def validMessageRefGen: Gen[String] =
    for {
      firstChar <- Gen.numChar.map(_.toString)
      secondChar <- Gen.numChar.map(_.toString)
      thirdChar <- Gen.alphaUpperChar.map(_.toString)
      fourthChar <- Gen.alphaUpperChar.map(_.toString)
      fifthChar <- Gen.alphaUpperChar.map(_.toString)
      sixthChar <- Gen.numChar.map(_.toString)
      seventhChar <- Gen.numChar.map(_.toString)
      eighthChar <- Gen.numChar.map(_.toString)
      ninthChar <- Gen.numChar.map(_.toString)
      tenthChar <- Gen.numChar.map(_.toString)
      eleventhChar <- Gen.alphaUpperChar.map(_.toString)
      twelfthChar <- Gen.numChar.map(_.toString)
      thirteenthChar <- Gen.numChar.map(_.toString)
      fourteenthChar <- Gen.numChar.map(_.toString)
    } yield (
      firstChar
        + secondChar
        + thirdChar
        + fourthChar
        + fifthChar
        + sixthChar
        + seventhChar
        + eighthChar
        + ninthChar
        + tenthChar
        + eleventhChar
        + twelfthChar
        + thirteenthChar
        + fourteenthChar
      )

  def invalidMessageRefGen: Gen[String] =
    for {
      hasFirstChar <- arbitrary[Boolean]
      hasSecondChar <- arbitrary[Boolean]
      hasThirdChar <- arbitrary[Boolean]
      hasFourthChar <- arbitrary[Boolean]
      hasFifthChar <- arbitrary[Boolean]
      hasSixthChar <- arbitrary[Boolean]
      hasSeventhChar <- arbitrary[Boolean]
      hasEighthChar <- arbitrary[Boolean]
      hasNinthChar <- arbitrary[Boolean]
      hasTenthChar <- arbitrary[Boolean]
      hasEleventhChar <- arbitrary[Boolean]
      hasTwelfthChar <- arbitrary[Boolean]
      hasThirteenthChar <- arbitrary[Boolean]
      hasFourteenthChar <- arbitrary[Boolean]
      firstChar <- if (hasFirstChar) Gen.numChar.map(_.toString) else Gen.const("")
      secondChar <- if (hasSecondChar) Gen.numChar.map(_.toString) else Gen.const("")
      thirdChar <- if (hasThirdChar) Gen.alphaUpperChar.map(_.toString) else Gen.const("")
      fourthChar <- if (hasFourthChar) Gen.alphaUpperChar.map(_.toString) else Gen.const("")
      fifthChar <- if (hasFifthChar) Gen.alphaUpperChar.map(_.toString) else Gen.const("")
      sixthChar <- if (hasSixthChar) Gen.numChar.map(_.toString) else Gen.const("")
      seventhChar <- if (hasSeventhChar) Gen.numChar.map(_.toString) else Gen.const("")
      eighthChar <- if (hasEighthChar) Gen.numChar.map(_.toString) else Gen.const("")
      ninthChar <- if (hasNinthChar) Gen.numChar.map(_.toString) else Gen.const("")
      tenthChar <- if (hasTenthChar) Gen.numChar.map(_.toString) else Gen.const("")
      eleventhChar <- if (hasEleventhChar) Gen.alphaUpperChar.map(_.toString) else Gen.const("")
      twelfthChar <- if (hasTwelfthChar) Gen.numChar.map(_.toString) else Gen.const("")
      thirteenthChar <- if (hasThirteenthChar) Gen.numChar.map(_.toString) else Gen.const("")
      fourteenthChar <- if (hasFourteenthChar) Gen.numChar.map(_.toString) else Gen.const("")
    } yield (
      firstChar
        + secondChar
        + thirdChar
        + fourthChar
        + fifthChar
        + sixthChar
        + seventhChar
        + eighthChar
        + ninthChar
        + tenthChar
        + eleventhChar
        + twelfthChar
        + thirteenthChar
        + fourteenthChar
      )
}
