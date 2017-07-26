package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.module.MessageReferenceModule._

@RunWith(classOf[JUnitRunner])
class MessageReferenceModuleSuite extends FunSuite with Matchers with Checkers {

  implicit val messageRefAr: Arbitrary[String] = Arbitrary {
    for {
      hasPrefix <- arbitrary[Boolean]
      hasSuffix <- arbitrary[Boolean]
      hasMessageRef <- arbitrary[Boolean]
      prefix <- if (hasPrefix) Gen.alphaStr else Gen.const("")
      suffix <- if (hasSuffix) Gen.alphaStr else Gen.const("")
      middle <- if (hasMessageRef) messageRefGen else Gen.alphaStr
    } yield prefix + middle + suffix
  }

  def messageRefGen: Gen[String] =
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
      firstChar <- if (hasFirstChar) Gen.alphaNumChar.map(_.toString) else Gen.const("")
    } yield firstChar

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
    ???
  }
}
