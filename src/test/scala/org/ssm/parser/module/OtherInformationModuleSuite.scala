package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Prop}
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.module.OtherInformationModule._

@RunWith(classOf[JUnitRunner])
class OtherInformationModuleSuite extends FunSuite with Matchers with Checkers {

  implicit val timeModeAr: Arbitrary[String] = Arbitrary {
    for {
      isEmpty <- arbitrary[Boolean]
      line <- if (isEmpty) Gen.const("") else Gen.alphaStr
    } yield line
  }

  test("Should be able to process valid OtherInformation line") {
    val input: Input = 0 -> "G M80 FCYML/FNCN.FCM"

    val actual = canProcess(input)
    val expected = true

    actual should be(expected)
  }

  test("Should not be able to process invalid OtherInformation line") {
    val input: Input = 0 -> "//"

    val actual = canProcess(input)
    val expected = false

    actual should be(expected)
  }

  test("Can process line as OtherInformation line") {
    val propOtherInformation: Prop = forAll { (line: String) =>
      val canProcessLine = canProcess(0 -> line)

      if (!line.isEmpty && !(line startsWith "//")) {
        canProcessLine
      } else {
        !canProcessLine
      }
    }

    check(propOtherInformation)
  }
}
