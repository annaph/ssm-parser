package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.domain._
import org.ssm.parser.module.FlightInformationModule._

@RunWith(classOf[JUnitRunner])
class FlightInformationModuleSuite extends FunSuite with Matchers {

  trait TestFixture {
    val input: Input = 0 -> "LX544A 1/LX/LH 3/LX 4/LH 5/LX 9/LX"

    val subMessage1 = SubMessage(Some(NEW), None, List())
    val subMessage2 = SubMessage(Some(CNL), None, List())

    val messageReference = Some("24MAY00144E003")
    val newFlightDesignator = Some(FlightDesignator("LX", 544))
  }

  test("Format raw data - (LX, 983)") {
    val actual: FlightDesignator = format("LX" -> "983").get

    actual should be(FlightDesignator("LX", 983))
  }

  test("Process input - state with one SubMessage") {
    new TestFixture {
      val state = SSMMessage(messageReference, List(subMessage1))

      val actual: SSMMessage = process(input, state)

      actual.messageReference should be(state.messageReference)
      actual.subMessages should be(
        subMessage1.copy(flightDesignator = newFlightDesignator) :: Nil)
    }
  }

  test("Process input - state with two SubMessages") {
    new TestFixture {
      val state = SSMMessage(messageReference, List(subMessage1, subMessage2))

      val actual: SSMMessage = process(input, state)

      actual.messageReference should be(state.messageReference)
      actual.subMessages should be(
        subMessage1.copy(flightDesignator = newFlightDesignator) :: subMessage2 :: Nil)
    }
  }
}
