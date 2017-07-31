package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalatest.{FunSuite, Matchers}
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers
import org.ssm.parser.SSMProcess.Input

import FlightInformationModule._

@RunWith(classOf[JUnitRunner])
class FlightInformationModuleSuite extends FunSuite with Matchers with Checkers {

  trait TestFixture {
    val input: Input = 0 -> "LX544A 1/LX/LH 3/LX 4/LH 5/LX 9/LX"
    val invalidInput: Input = 0 -> "invalid"
  }

  test("Should be able to process valid FlightInformation line") {
    new TestFixture {
      val actual: Boolean = canProcess(input)

      actual should be(true)
    }
  }

  test("Should not be able to process invalid FlightInformation line") {
    new TestFixture {
      val actual: Boolean = canProcess(invalidInput)

      actual should be(false)
    }
  }

  test("Can process line as FlightInformation line") {
    ???
  }
}
