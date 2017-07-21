package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Prop}
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.module.SubMessageModule._

@RunWith(classOf[JUnitRunner])
class SubMessageModuleSuite extends FunSuite with Matchers with Checkers {

  implicit val subMessageAr: Arbitrary[String] = Arbitrary {
    for {
      hasPrefix <- arbitrary[Boolean]
      hasSuffix <- arbitrary[Boolean]
      hasDoubleSlash <- arbitrary[Boolean]
      prefix <- if (hasPrefix) Gen.alphaStr else Gen.const("")
      suffix <- if (hasSuffix) Gen.alphaStr else Gen.const("")
      middle <- if (hasDoubleSlash) Gen.const("//") else Gen.alphaStr
    } yield prefix + middle + suffix
  }

  test("Should be able to process valid SubMessage line") {
    val input: Input = 0 -> "//"

    val actual = canProcess(input)
    val expected = true

    actual should be(expected)
  }

  test("Should not be able to process invalid SubMessage line") {
    val input: Input = 0 -> "invalid"

    val actual = canProcess(input)
    val expected = false

    actual should be(expected)
  }

  test("Can process line as SubMessage line") {
    val propSubMessage: Prop = forAll { (line: String) =>
      val canProcessLine = canProcess(0 -> line)

      if (line == "//")
        canProcessLine == true
      else
        canProcessLine == false
    }

    check(propSubMessage)
  }
}
