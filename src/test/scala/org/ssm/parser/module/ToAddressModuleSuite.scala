package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Prop}
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.module.ToAddressModule._

@RunWith(classOf[JUnitRunner])
class ToAddressModuleSuite extends FunSuite with Matchers with Checkers {

  implicit val toAddressAr: Arbitrary[String] = Arbitrary {
    for {
      isQ <- arbitrary[Boolean]
      rest <- Gen.alphaStr
    } yield {
      if (isQ)
        'Q' + rest
      else
        rest
    }
  }

  test("Should be able to process valid ToAddress line") {
    val input: Input = 0 -> "QD AAABBCC"

    val actual = canProcess(input)
    val expected = true

    actual should be(expected)
  }

  test("Should not be able to process invalid ToAddress line") {
    val input: Input = 0 -> "D AAABBCC"

    val actual = canProcess(input)
    val expected = false

    actual should be(expected)
  }

  test("Can process line as ToAddress line") {
    val propToAddress: Prop = forAll { (line: String) =>
      val canProcessLine = canProcess(0 -> line)

      if (line.startsWith("Q"))
        canProcessLine == true
      else
        canProcessLine == false
    }

    check(propToAddress)
  }
}
