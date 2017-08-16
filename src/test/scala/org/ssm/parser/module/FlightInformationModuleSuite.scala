package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.domain._
import org.ssm.parser.module.FlightInformationModule._

import scala.util.{Success, Try}

@RunWith(classOf[JUnitRunner])
class FlightInformationModuleSuite extends FunSuite with Matchers with Checkers {

  trait TestFixture {
    val input: Input = 0 -> "LX544A 1/LX/LH 3/LX 4/LH 5/LX 9/LX"

    val subMessage1 = SubMessage(Some(NEW), None, List())
    val subMessage2 = SubMessage(Some(CNL), None, List())
    val state = SSMMessage(Some("24MAY00144E003"), List(subMessage1, subMessage2))
  }

  test("Format raw data - (LX, 983)") {
    val actual: Try[FlightDesignator] = format("LX" -> "983")

    actual should be(Success(FlightDesignator("LX", 983)))
  }

  test("Process input") {
    new TestFixture {
      val actual: SSMMessage = process(input, state)

      actual.messageReference should be(state.messageReference)
      actual.subMessages should be(List(
        subMessage1.copy(flightDesignator = Some(FlightDesignator("LX", 544))),
        subMessage2))
    }
  }
}
