package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.domain._
import org.ssm.parser.module.ActionModule._

@RunWith(classOf[JUnitRunner])
class ActionModuleSuite extends FunSuite with Matchers {

  trait TestFixture {
    val input = 0 -> "NEW"

    val messageReference = Some("24MAY00144E003")
    val subMessage = SubMessage(Some(CNL), Some(FlightDesignator("LX", 543)), List())

    val newSubMessage = SubMessage(Some(NEW), None, List())
  }

  test("Extract input - NEW") {
    val actual: String = extract(0 -> "NEW")

    actual should be("NEW")
  }

  test("Extract input - NEW XASM") {
    val actual: String = extract(0 -> "NEW XASM")

    actual should be("NEW")
  }

  test("Process input - state with empty SubMessages") {
    new TestFixture {
      val state = SSMMessage(messageReference, Nil)

      val actual: SSMMessage = process(input, state)

      actual.messageReference should be(state.messageReference)
      actual.subMessages should be(newSubMessage :: Nil)
    }
  }

  test("Process input - state with one SubMessage") {
    new TestFixture {
      val state = SSMMessage(messageReference, List(subMessage))

      val actual: SSMMessage = process(input, state)

      actual.messageReference should be(state.messageReference)
      actual.subMessages should be(newSubMessage :: subMessage :: Nil)
    }
  }
}
