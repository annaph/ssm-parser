package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.model.SSMMessage

@RunWith(classOf[JUnitRunner])
class NoExtractModuleSuite extends FunSuite with Matchers {

  trait TestFixture extends NoExtractModule {
    override def canProcess(input: (Int, String)): Boolean =
      true

    val input = (0 -> "")
    val state = SSMMessage(Some(""), List())
    val rawData = ()
  }

  test("Process input") {
    new TestFixture {
      val actual: SSMMessage = process(input, state)

      actual should be(state)
    }
  }

  test("Extract input") {
    new TestFixture {
      val actual: Unit = extract(input)

      actual should be(rawData)
    }
  }

  test("Format raw data") {
    new TestFixture {
      val actual: Unit = format(rawData).get

      actual should be(rawData)
    }
  }
}
