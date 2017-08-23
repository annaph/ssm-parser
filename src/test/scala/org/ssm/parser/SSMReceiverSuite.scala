package org.ssm.parser

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.Process._
import org.ssm.parser.SSMProcess.SSMProcess
import org.ssm.parser.model.SSMMessage

@RunWith(classOf[JUnitRunner])
class SSMReceiverSuite extends FunSuite with Matchers {

  trait TestFixture {
    val ssmMessage = SSMMessage(None, Nil)

    val toAddressInput = 1 -> "QD AAABBCC"
    val fromAddressInput = 2 -> ".XXXYYZZ 111301"
    val identifierInput = 3 -> "SSM"
    val timeModeInput = 4 -> "UTC"
    val referenceInput = 5 -> "24MAY00144E003/REF 123/449"
    val actionInput = 6 -> "NEW XASM"

    val startProcess: SSMProcess = start {
      ssmMessage -> await(SSMReceiver)
    }
    val parseToAddressProcess = parse(ToAddress)(SSMParser)
    val parseFromAddressProcess = parse(FromAddress)(SSMParser)
    val parseIdentifierProcess = parse(Identifier)(SSMParser)
    val parseTimeModeProcess = parse(TimeMode)(SSMParser)
  }

  test("To Address input & previous process Start") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(toAddressInput), startProcess)

      actual match {
        case Parse(kind, run) =>
          kind should be(ToAddress)
          run should be(SSMParser)
        case _ =>
          fail()
      }
    }
  }

  test("Improper input & previous process Start") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(fromAddressInput), startProcess)

      actual match {
        case Halt(Some(input), Kill) =>
          input should be(fromAddressInput)
        case _ =>
          fail()
      }
    }
  }

  test("From Address input & previous process Parse-ToAddress") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(fromAddressInput), parseToAddressProcess)

      actual match {
        case Parse(kind, run) =>
          kind should be(FromAddress)
          run should be(SSMParser)
        case _ =>
          fail()
      }
    }
  }

  test("Improper input & previous process Parse-ToAddress") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(identifierInput), parseToAddressProcess)

      actual match {
        case Halt(Some(input), Kill) =>
          input should be(identifierInput)
        case _ =>
          fail()
      }
    }
  }

  test("Identifier input & previous process Parse-FromAddress") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(identifierInput), parseFromAddressProcess)

      actual match {
        case Parse(kind, run) =>
          kind should be(Identifier)
          run should be(SSMParser)
        case _ =>
          fail()
      }
    }
  }

  test("Improper input & previous process Parse-FromAddress") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(timeModeInput), parseFromAddressProcess)

      actual match {
        case Halt(Some(input), Kill) =>
          input should be(timeModeInput)
        case _ =>
          fail()
      }
    }
  }

  test("TimeMode input & previous process Parse-Identifier") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(timeModeInput), parseIdentifierProcess)

      actual match {
        case Parse(kind, run) =>
          kind should be(TimeMode)
          run should be(SSMParser)
        case _ =>
          fail()
      }
    }
  }

  test("Improper input & previous process Parse-Identifier") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(referenceInput), parseIdentifierProcess)

      actual match {
        case Halt(Some(input), Kill) =>
          input should be(referenceInput)
        case _ =>
          fail()
      }
    }
  }

  test("Reference input & previous process Parse-TimeMode") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(referenceInput), parseTimeModeProcess)

      actual match {
        case Parse(kind, run) =>
          kind should be(Reference)
          run should be(SSMParser)
        case _ =>
          fail()
      }
    }
  }

  test("Improper input & previous process Parse-TimeMode") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(actionInput), parseTimeModeProcess)

      actual match {
        case Halt(Some(input), Kill) =>
          input should be(actionInput)
        case _ =>
          fail()
      }
    }
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
