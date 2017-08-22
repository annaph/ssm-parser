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
  }

  test("Previous state Start") {
    new TestFixture {
      ???
    }
  }

  test("Previous state Start & invalid input") {
    new TestFixture {
      ???
    }
  }
}
