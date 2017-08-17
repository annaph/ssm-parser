package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.domain._
import org.ssm.parser.module.ActionModule._

@RunWith(classOf[JUnitRunner])
class ActionModuleSuite extends FunSuite with Matchers with Checkers {

  trait TestFixture {
    private val flightDesignator = FlightDesignator("LX", 543)
    private val subMessage = SubMessage(Some(CNL), Some(flightDesignator), List())

    val state = SSMMessage(Some("24MAY00144E003"), List(subMessage))
  }

  test("Extract input - NEW") {
    val actual: String = extract(0 -> "NEW")

    actual should be("NEW")
  }

  test("Extract input - NEW XASM") {
    val actual: String = extract(0 -> "NEW XASM")

    actual should be("NEW")
  }

  test("Process input") {
    new TestFixture {
      val actual: SSMMessage = process(0 -> "NEW", state)

      actual.messageReference should be(state.messageReference)
      actual.subMessages should be(SubMessage(Some(NEW), None, List()) :: state.subMessages)
    }
  }
}
