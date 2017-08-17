package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Prop}
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.module.ActionModule.canProcess

@RunWith(classOf[JUnitRunner])
class ActionModuleCanProcessSuite extends FunSuite with Matchers with Checkers {

  implicit val actionAr: Arbitrary[String] = Arbitrary {
    for {
      hasPrefix <- arbitrary[Boolean]
      hasSuffix <- arbitrary[Boolean]
      hasMiddle <- arbitrary[Boolean]
      prefix <- if (hasPrefix) Gen.alphaStr else Gen.const("")
      suffix <- if (hasSuffix) Gen.alphaStr else Gen.const("")
      middle <- {
        if (hasMiddle)
          Gen.oneOf(List(
            "NEW", "CNL", "TIM", "EQT", "RPL", "SKD", "ACK", "ADM", "CON", "FLT", "NAC", "REV", "RSD"))
        else
          Gen.const("")
      }
    } yield prefix + middle + suffix
  }

  test("Should be able to process NEW Action line") {
    val actual: Boolean = canProcess(0 -> "NEW")

    actual should be(true)
  }

  test("Should be able to process CNL Action line") {
    val actual: Boolean = canProcess(0 -> "CNL")

    actual should be(true)
  }

  test("Should be able to process TIM Action line") {
    val actual: Boolean = canProcess(0 -> "TIM")

    actual should be(true)
  }

  test("Should be able to process EQT Action line") {
    val actual: Boolean = canProcess(0 -> "EQT")

    actual should be(true)
  }

  test("Should be able to process RPL Action line") {
    val actual: Boolean = canProcess(0 -> "RPL")

    actual should be(true)
  }

  test("Should be able to process SKD Action line") {
    val actual: Boolean = canProcess(0 -> "SKD")

    actual should be(true)
  }

  test("Should be able to process ACK Action line") {
    val actual: Boolean = canProcess(0 -> "ACK")

    actual should be(true)
  }

  test("Should be able to process ADM Action line") {
    val actual: Boolean = canProcess(0 -> "ADM")

    actual should be(true)
  }

  test("Should be able to process CON Action line") {
    val actual: Boolean = canProcess(0 -> "CON")

    actual should be(true)
  }

  test("Should be able to process FLT Action line") {
    val actual: Boolean = canProcess(0 -> "FLT")

    actual should be(true)
  }

  test("Should be able to process NAC Action line") {
    val actual: Boolean = canProcess(0 -> "NAC")

    actual should be(true)
  }

  test("Should be able to process REV Action line") {
    val actual: Boolean = canProcess(0 -> "REV")

    actual should be(true)
  }

  test("Should be able to process RSD Action line") {
    val actual: Boolean = canProcess(0 -> "RSD")

    actual should be(true)
  }

  test("Should not be able to process invalid Action line") {
    val actual: Boolean = canProcess(0 -> "invalid")

    actual should be(false)
  }

  test("Can process line as MessageReference line") {
    val propMessageRefrence: Prop = forAll(actionAr.arbitrary) { (line: String) =>
      val canProcessLine = canProcess(0 -> line)

      if (line.startsWith("NEW") || line.startsWith("CNL") || line.startsWith("TIM") ||
        line.startsWith("EQT") || line.startsWith("RPL") || line.startsWith("SKD") ||
        line.startsWith("ACK") || line.startsWith("ADM") || line.startsWith("CON") ||
        line.startsWith("FLT") || line.startsWith("NAC") || line.startsWith("REV") ||
        line.startsWith("RSD")) {
        canProcessLine
      } else {
        !canProcessLine
      }
    }

    check(propMessageRefrence)
  }
}
