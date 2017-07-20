package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Prop}
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.module.IdentifierModule._

@RunWith(classOf[JUnitRunner])
class IdentifierModuleSuite extends FunSuite with Matchers with Checkers {

  implicit val identifierAr: Arbitrary[String] = Arbitrary {
    for {
      hasPrefix <- arbitrary[Boolean]
      hasSuffix <- arbitrary[Boolean]
      prefix <- if (hasPrefix) Gen.alphaStr else Gen.const("")
      suffix <- if (hasSuffix) Gen.alphaStr else Gen.const("")
      n <- Gen.choose(1, 3)
      middle <- {
        if (n == 1)
          Gen.const("SSM")
        else if (n == 2)
          Gen.oneOf(List("ssm", "ssM", "sSm", "Ssm", "SSm", "sSM", "SsM"))
        else
          Gen.alphaStr
      }
    } yield prefix + middle + suffix
  }

  test("Should be able to process valid Identifier line") {
    val input: Input = 0 -> "SSM"

    val actual = canProcess(input)
    val expected = true

    actual should be(expected)
  }

  test("Should not be able to process invalid Identifier line") {
    val input: Input = 0 -> "invalid"

    val actual = canProcess(input)
    val expected = false

    actual should be(expected)
  }

  test("Can process line as Identifier line") {
    val propIdentifier: Prop = forAll { (line: String) =>
      val canProcessLine = canProcess(0 -> line)

      if (line == "SSM")
        canProcessLine == true
      else
        canProcessLine == false
    }

    check(propIdentifier)
  }
}
