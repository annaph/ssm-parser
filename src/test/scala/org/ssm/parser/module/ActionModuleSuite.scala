package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalatest.{FunSuite, Matchers}
import org.scalatest.junit.JUnitRunner
import org.ssm.parser.SSMProcess.Input

import ActionModule._

@RunWith(classOf[JUnitRunner])
class ActionModuleSuite extends FunSuite with Matchers {

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
    ???
  }

  test("Should be able to process NAC Action line") {
    ???
  }

  test("Should be able to process REV Action line") {
    ???
  }

  test("Should be able to process RSD Action line") {
    ???
  }

  test("Should not be able to process invalid Action line") {

  }
}
