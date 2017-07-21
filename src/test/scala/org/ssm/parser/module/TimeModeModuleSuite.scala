package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Prop}
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.module.TimeModeModule._

@RunWith(classOf[JUnitRunner])
class TimeModeModuleSuite extends FunSuite with Matchers with Checkers {

  implicit val timeModeAr: Arbitrary[String] = Arbitrary {
    for {
      hasPrefix <- arbitrary[Boolean]
      hasSuffix <- arbitrary[Boolean]
      prefix <- if (hasPrefix) Gen.alphaStr else Gen.const("")
      suffix <- if (hasSuffix) Gen.alphaStr else Gen.const("")
      n <- Gen.choose(1, 5)
      middle <- {
        if (n == 1)
          Gen.const("UTC")
        else if (n == 2)
          Gen.oneOf(List("utc", "utC", "uTc", "Utc", "UTc", "uTC", "UtC"))
        else if (n == 3)
          Gen.const("LT")
        else if (n == 4)
          Gen.oneOf(List("lt", "lT", "Lt"))
        else
          Gen.alphaStr
      }
    } yield prefix + middle + suffix
  }

  test("Should be able to process valid TimeMode line - UTC") {
    val input: Input = 0 -> "UTC"

    val actual = canProcess(input)
    val expected = true

    actual should be(expected)
  }

  test("Should be able to process valid TimeMode line - LT") {
    val input: Input = 0 -> "LT"

    val actual = canProcess(input)
    val expected = true

    actual should be(expected)
  }

  test("Should not be able to process invalid TimeMode line") {
    val input: Input = 0 -> "invalid"

    val actual = canProcess(input)
    val expected = false

    actual should be(expected)
  }

  test("Can process line as TimeMode line") {
    val propTimeMode: Prop = forAll { (line: String) =>
      val canProcessLine = canProcess(0 -> line)

      if (line == "UTC" || line == "LT")
        canProcessLine == true
      else
        canProcessLine == false
    }

    check(propTimeMode)
  }
}
