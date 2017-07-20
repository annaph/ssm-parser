package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Prop}
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.module.FromAddressModule.canProcess

@RunWith(classOf[JUnitRunner])
class FromAddressModuleSuite extends FunSuite with Matchers with Checkers {

  implicit val fromAddressAr: Arbitrary[String] = Arbitrary {
    for {
      hasDot <- arbitrary[Boolean]
      rest <- Gen.alphaStr
    } yield {
      if (hasDot)
        '.' + rest
      else
        rest
    }
  }

  test("Should be able to process valid FromAddress line") {
    val input: Input = 0 -> ".XXXYYZZ 111301"

    val actual = canProcess(input)
    val expected = true

    actual should be(expected)
  }

  test("Should not be able to process invalid FromAddress line") {
    val input: Input = 0 -> "XXXYYZZ 111301"

    val actual = canProcess(input)
    val expected = false

    actual should be(expected)
  }

  test("Can process line as FromAddress line") {
    val propFromAddress: Prop = forAll { (line: String) =>
      val canProcessLine = canProcess(0 -> line)

      if (line.startsWith("."))
        canProcessLine == true
      else
        canProcessLine == false
    }

    check(propFromAddress)
  }
}
