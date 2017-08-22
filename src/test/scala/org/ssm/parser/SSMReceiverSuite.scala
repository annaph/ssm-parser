package org.ssm.parser

import org.junit.runner.RunWith
import org.scalatest.{FunSuite, Matchers}
import org.scalatest.junit.JUnitRunner
import org.ssm.parser.SSMProcess.{Input, SSMProcess}
import Process._
import org.ssm.parser.model.SSMMessage

@RunWith(classOf[JUnitRunner])
class SSMReceiverSuite extends FunSuite with Matchers {

  trait TestFixture {
    val ssmMessage = SSMMessage(None, Nil)
  }

  test("Previous state Start & To Address input") {
    new TestFixture {
      val input: Input = 0 -> "QD AAABBCC"
      val prev: SSMProcess = start {
        ssmMessage -> await(SSMReceiver)
      }

      val actual: SSMProcess = SSMReceiver(Some(input), prev)

      actual match {
        case Parse(_, _) =>
          ???
        case _ =>
          ???
      }
    }
  }

  test("Previous state Start & improper input") {
    ???
  }

  test("Previous state Parse-ToAddress and From Address input") {
    ???
  }

  test("Previous state Parse-ToAddress & improper input") {
    ???
  }

  test("Previous state Parse-FromAddress & Identifier input") {
    ???
  }

  test("Previous state Parse-FromAddress & improper input") {
    ???
  }

  test("Previous state Parse-Identifier & TimeMode input") {
    ???
  }

  test("Previous state Parse-Identifier & improper input") {
    ???
  }

  test("Previous state Parse-TimeMode & Reference input") {
    ???
  }

  test("Previous state Parse-TimeMode & improper input") {
    ???
  }

  test("Previous state Parse-Reference & Action input") {
    ???
  }

  test("Previous state Parse-Reference & improper input") {
    ???
  }

  test("Previous state Parse-Action & Flight Information input") {
    ???
  }

  test("Previous state Parse-Action & improper input") {
    ???
  }

  test("Previous state Parse-FlightInformation & Period Information input") {
    ???
  }

  test("Previous state Parse-FlightInformation & improper input") {
    ???
  }

  test("Previous state Parse-PeriodInformation & Period Information input") {
    ???
  }

  test("Previous state Parse-PeriodInformation & Other Information input") {
    ???
  }

  test("Previous state Parse-PeriodInformation & improper input") {
    ???
  }

  test("Previous state Parse-OtherInformation & Other Information input") {
    ???
  }

  test("Previous state Parse-OtherInformation & Sub-Action input") {
    ???
  }

  test("Previous state Parse-OtherInformation & none input") {
    ???
  }

  test("Previous state Parse-OtherInformation & improper input") {
    ???
  }

  test("None input and previous state non Parse-OtherInformation") {
    ???
  }

  test("Previous state Await") {
    ???
  }

  test("Previous state Halt") {
    ???
  }
}
