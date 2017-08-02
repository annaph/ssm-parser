package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalatest.{FunSuite, Matchers}
import org.scalatest.junit.JUnitRunner
import org.ssm.parser.SSMProcess.Input

import PeriodInformationModule.canProcess

@RunWith(classOf[JUnitRunner])
class PeriodInformationModuleCanProcessSuite extends FunSuite with Matchers {

  trait TestFixture {
    val input1: Input = 0 -> "12AUG17 30SEP17 1234567/W1 6/LX545A/1"
    val input2: Input = 0 -> "12AUG 30SEP 123/W2 6/LX545A/1"
    val input3: Input = 0 -> "12AUG 30SEP 12 6/LX545A/1"

    val invalidInput = 0 -> "invalid"
  }

  test("Should be able to process valid PeriodInformation line - 12AUG17 30SEP17 1234567/W1 6/LX545A/1") {
    new TestFixture {
      val actual: Boolean = canProcess(input1)

      actual should be(true)
    }
  }

  test("Should be able to process valid PeriodInformation line - 12AUG 30SEP 123/W2 6/LX545A/1") {
    new TestFixture {
      val actual: Boolean = canProcess(input2)

      actual should be(true)
    }
  }

  test("Should be able to process valid PeriodInformation line - 12AUG 30SEP 12 6/LX545A/1") {
    new TestFixture {
      val actual: Boolean = canProcess(input3)

      actual should be(true)
    }
  }

  test("Should not be able to process invalid PeriodInformation line") {
    new TestFixture {
      val actual: Boolean = canProcess(invalidInput)

      actual should be(false)
    }
  }
}
