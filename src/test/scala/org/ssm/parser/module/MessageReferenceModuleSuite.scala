package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.module.MessageReferenceModule._
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Prop}

import scala.util.{Failure, Try, Success}

@RunWith(classOf[JUnitRunner])
class MessageReferenceModuleSuite extends FunSuite with Matchers with Checkers {

  implicit val messageRefAr: Arbitrary[String] = Arbitrary {
    for {
      hasPrefix <- arbitrary[Boolean]
      hasSuffix <- arbitrary[Boolean]
      hasMessageRef <- arbitrary[Boolean]
      prefix <- if (hasPrefix) Gen.alphaStr else Gen.const("")
      suffix <- if (hasSuffix) Gen.alphaStr else Gen.const("")
      middle <- validMessageRefGen
    } yield prefix + middle + suffix
  }

  def validMessageRefGen: Gen[String] = {
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
    } yield  (
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

  test("Should be able to process valid MessageReference line") {
    val input: Input = 0 -> "24MAY00144E003/REF 123/449"

    val actual = canProcess(input)
    val expected = true

    actual should be(expected)
  }

  test("Should not be able to process invalid MessageReference line") {
    val input: Input = 0 -> "invalid"

    val actual = canProcess(input)
    val expected = false

    actual should be(expected)
  }

  test("Can process line as MessageReference line") {
    val propMessageRefrence: Prop = forAll(messageRefAr.arbitrary) { (line: String) =>
      val canProcessLine = canProcess(0 -> line)

      if (line.size < 14)
        canProcessLine == false
      else {
        val str1 = line.substring(0, 1)
        val str2 = line.substring(5, 10)
        val str3 = line.substring(11, 14)

        println("line: " + line)
        println("str1: " + str1)
        println("str2: " + str2)
        println("str3: " + str3)
        println("---------------------------------")

        Try {
          str1.toInt
          str2.toInt
          str3.toInt
        } match {
          case Success(_) =>
            canProcessLine == true
          case Failure(_) =>
            canProcessLine == false
        }
      }
    }

    check(propMessageRefrence)
  }
}
