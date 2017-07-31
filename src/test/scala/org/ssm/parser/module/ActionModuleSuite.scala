package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Prop}
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.domain._
import org.ssm.parser.module.ActionModule._

import scala.util.{Success, Try}

@RunWith(classOf[JUnitRunner])
class ActionModuleSuite extends FunSuite with Matchers with Checkers {

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

  trait TestFixture {
    val newInput: Input = 0 -> "NEW"
    val cnlInput: Input = 0 -> "CNL"
    val timInput: Input = 0 -> "TIM"
    val eqtInput: Input = 0 -> "EQT"
    val rplInput: Input = 0 -> "RPL"
    val skdInput: Input = 0 -> "SKD"
    val ackInput: Input = 0 -> "ACK"
    val admInput: Input = 0 -> "ADM"
    val conInput: Input = 0 -> "CON"
    val fltInput: Input = 0 -> "FLT"
    val nacInput: Input = 0 -> "NAC"
    val revInput: Input = 0 -> "REV"
    val rsdInput: Input = 0 -> "RSD"
    val invalidInput: Input = 0 -> "invalid"
    val newXASMInput: Input = 0 -> "NEW XASM"

    private val flightDesignator = FlightDesignator("LX", 543)
    private val subMessage = SubMessage(Some(CNL), Some(flightDesignator), List())
    val state = SSMMessage(Some("24MAY00144E003"), List(subMessage))
  }

  test("Should be able to process NEW Action line") {
    new TestFixture {
      val actual = canProcess(newInput)

      actual should be(true)
    }
  }

  test("Should be able to process CNL Action line") {
    new TestFixture {
      val actual = canProcess(cnlInput)

      actual should be(true)
    }
  }

  test("Should be able to process TIM Action line") {
    new TestFixture {
      val actual = canProcess(timInput)

      actual should be(true)
    }
  }

  test("Should be able to process EQT Action line") {
    new TestFixture {
      val actual = canProcess(eqtInput)

      actual should be(true)
    }
  }

  test("Should be able to process RPL Action line") {
    new TestFixture {
      val actual = canProcess(rplInput)

      actual should be(true)
    }
  }

  test("Should be able to process SKD Action line") {
    new TestFixture {
      val actual = canProcess(skdInput)

      actual should be(true)
    }
  }

  test("Should be able to process ACK Action line") {
    new TestFixture {
      val actual = canProcess(ackInput)

      actual should be(true)
    }
  }

  test("Should be able to process ADM Action line") {
    new TestFixture {
      val actual = canProcess(admInput)

      actual should be(true)
    }
  }

  test("Should be able to process CON Action line") {
    new TestFixture {
      val actual = canProcess(conInput)

      actual should be(true)
    }
  }

  test("Should be able to process FLT Action line") {
    new TestFixture {
      val actual = canProcess(fltInput)

      actual should be(true)
    }
  }

  test("Should be able to process NAC Action line") {
    new TestFixture {
      val actual = canProcess(nacInput)

      actual should be(true)
    }
  }

  test("Should be able to process REV Action line") {
    new TestFixture {
      val actual = canProcess(revInput)

      actual should be(true)
    }
  }

  test("Should be able to process RSD Action line") {
    new TestFixture {
      val actual = canProcess(rsdInput)

      actual should be(true)
    }
  }

  test("Should not be able to process invalid Action line") {
    new TestFixture {
      val actual = canProcess(invalidInput)

      actual should be(false)
    }
  }

  test("Can process line as MessageReference line") {
    val propMessageRefrence: Prop = forAll(actionAr.arbitrary) { (line: String) =>
      val canProcessLine = canProcess(0 -> line)

      if (line.startsWith("NEW") || line.startsWith("CNL") || line.startsWith("TIM") ||
        line.startsWith("EQT") || line.startsWith("RPL") || line.startsWith("SKD") ||
        line.startsWith("ACK") || line.startsWith("ADM") || line.startsWith("CON") ||
        line.startsWith("FLT") || line.startsWith("NAC") || line.startsWith("REV") ||
        line.startsWith("RSD"))
        canProcessLine == true
      else
        canProcessLine == false
    }

    check(propMessageRefrence)
  }

  test("Extract input - NEW") {
    new TestFixture {
      val actual: String = extract(newInput)

      actual should be(newInput._2)
    }
  }

  test("Extract input - NEW XASM") {
    new TestFixture {
      val actual: String = extract(newXASMInput)

      actual should be(newXASMInput._2 take 3)
    }
  }

  test("Format raw data - NEW") {
    new TestFixture {
      val actual: Try[SubMessageAction] = format(newInput._2)

      actual should be(Success(NEW))
    }
  }

  test("Format raw data - CNL") {
    new TestFixture {
      val actual: Try[SubMessageAction] = format(cnlInput._2)

      actual should be(Success(CNL))
    }
  }

  test("Format raw data - TIM") {
    new TestFixture {
      val actual: Try[SubMessageAction] = format(timInput._2)

      actual should be(Success(TIM))
    }
  }

  test("Format raw data - EQT") {
    new TestFixture {
      val actual: Try[SubMessageAction] = format(eqtInput._2)

      actual should be(Success(EQT))
    }
  }

  test("Format raw data - RPL") {
    new TestFixture {
      val actual: Try[SubMessageAction] = format(rplInput._2)

      actual should be(Success(RPL))
    }
  }

  test("Format raw data - SKD") {
    new TestFixture {
      val actual: Try[SubMessageAction] = format(skdInput._2)

      actual should be(Success(SKD))
    }
  }

  test("Format raw data - ACK") {
    new TestFixture {
      val actual: Try[SubMessageAction] = format(ackInput._2)

      actual should be(Success(ACK))
    }
  }

  test("Format raw data - ADM") {
    new TestFixture {
      val actual: Try[SubMessageAction] = format(admInput._2)

      actual should be(Success(ADM))
    }
  }

  test("Format raw data - CON") {
    new TestFixture {
      val actual: Try[SubMessageAction] = format(conInput._2)

      actual should be(Success(COV))
    }
  }

  test("Format raw data - FLT") {
    new TestFixture {
      val actual: Try[SubMessageAction] = format(fltInput._2)

      actual should be(Success(FLT))
    }
  }

  test("Format raw data - NAC") {
    new TestFixture {
      val actual: Try[SubMessageAction] = format(nacInput._2)

      actual should be(Success(NAC))
    }
  }

  test("Format raw data - REV") {
    new TestFixture {
      val actual: Try[SubMessageAction] = format(revInput._2)

      actual should be(Success(REV))
    }
  }

  test("Format raw data - RSD") {
    new TestFixture {
      val actual: Try[SubMessageAction] = format(rsdInput._2)

      actual should be(Success(RSD))
    }
  }

  test("Process input") {
    new TestFixture {
      val actual: SSMMessage = process(newInput, state)

      actual.messageReference should be(state.messageReference)
      actual.subMessages should be(SubMessage(Some(NEW), None, List()) :: state.subMessages)
    }
  }
}
