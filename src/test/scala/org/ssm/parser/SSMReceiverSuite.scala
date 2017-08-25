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
    val flightInformationInput = 7 -> "LX544A 1/LX/LH 3/LX 4/LH 5/LX 9/LX"
    val periodInformationInput = 8 -> "12AUG 30SEP 1234567/W2 6/LX545A/1"
    val otherInformationInput = 9 -> "G M80 FCYML/FNCN.FCM"
    val subActionInput = 10 -> "//"

    val startProcess: SSMProcess = start {
      ssmMessage -> await(SSMReceiver)
    }
    val parseToAddressProcess = parse(ToAddress)(SSMParser)
    val parseFromAddressProcess = parse(FromAddress)(SSMParser)
    val parseIdentifierProcess = parse(Identifier)(SSMParser)
    val parseTimeModeProcess = parse(TimeMode)(SSMParser)
    val parseReferenceProcess = parse(Reference)(SSMParser)
    val parseSubActionProcess = parse(SubAction)(SSMParser)
    val parseActionProcess = parse(Action)(SSMParser)
    val parseFlightInformationProcess = parse(FlightInformation)(SSMParser)
    val parsePeriodInformationProcess = parse(PeriodInformation)(SSMParser)
    val parseOtherInformationProcess = parse(OtherInformation)(SSMParser)
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

  test("Action input & previous process Parse-Reference") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(actionInput), parseReferenceProcess)

      actual match {
        case Parse(kind, run) =>
          kind should be(Action)
          run should be(SSMParser)
        case _ =>
          fail()
      }
    }
  }

  test("Action input & previous process Parse-SubAction") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(actionInput), parseSubActionProcess)

      actual match {
        case Parse(kind, run) =>
          kind should be(Action)
          run should be(SSMParser)
        case _ =>
          fail()
      }
    }
  }

  test("Improper input & previous process Parse-Reference") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(flightInformationInput), parseReferenceProcess)

      actual match {
        case Halt(Some(input), Kill) =>
          input should be(flightInformationInput)
        case _ =>
          fail()
      }
    }
  }

  test("Improper input & previous process Parse-SubAction") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(flightInformationInput), parseSubActionProcess)

      actual match {
        case Halt(Some(input), Kill) =>
          input should be(flightInformationInput)
        case _ =>
          fail()
      }
    }
  }

  test("Flight Information input & previous process Parse-Action") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(flightInformationInput), parseActionProcess)

      actual match {
        case Parse(kind, run) =>
          kind should be(FlightInformation)
          run should be(SSMParser)
        case _ =>
          fail()
      }
    }
  }

  test("Improper input & previous process Parse-Action") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(periodInformationInput), parseActionProcess)

      actual match {
        case Halt(Some(input), Kill) =>
          input should be(periodInformationInput)
        case _ =>
          fail()
      }
    }
  }

  test("Period Information input & previous process Parse-FlightInformation") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(periodInformationInput), parseFlightInformationProcess)

      actual match {
        case Parse(kind, run) =>
          kind should be(PeriodInformation)
          run should be(SSMParser)
        case _ =>
          fail()
      }
    }
  }

  test("Improper input & previous process Parse-FlightInformation") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(otherInformationInput), parseFlightInformationProcess)

      actual match {
        case Halt(Some(input), Kill) =>
          input should be(otherInformationInput)
        case _ =>
          fail()
      }
    }
  }

  test("Period Information input & previous process Parse-PeriodInformation") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(periodInformationInput), parsePeriodInformationProcess)

      actual match {
        case Parse(kind, run) =>
          kind should be(PeriodInformation)
          run should be(SSMParser)
        case _ =>
          fail()
      }
    }
  }

  test("Improper input & previous process Parse-PeriodInformation") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(subActionInput), parsePeriodInformationProcess)

      actual match {
        case Halt(Some(input), Kill) =>
          input should be(subActionInput)
        case _ =>
          fail()
      }
    }
  }

  test("Other Information input & previous process Parse-PeriodInformation") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(otherInformationInput), parsePeriodInformationProcess)

      actual match {
        case Parse(kind, run) =>
          kind should be(OtherInformation)
          run should be(SSMParser)
        case _ =>
          fail()
      }
    }
  }

  test("Other Information input & previous process Parse-OtherInformation") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(otherInformationInput), parseOtherInformationProcess)

      actual match {
        case Parse(kind, run) =>
          kind should be(OtherInformation)
          run should be(SSMParser)
        case _ =>
          fail()
      }
    }
  }

  test("Improper input & previous process Parse-OtherInformation") {
    new TestFixture {
      val actual: SSMProcess = SSMReceiver(Some(actionInput), parseOtherInformationProcess)

      actual match {
        case Halt(Some(input), Kill) =>
          input should be(actionInput)
        case _ =>
          fail()
      }
    }
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
