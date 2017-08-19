package org.ssm.parser.module

import java.time.DayOfWeek.MONDAY
import java.time.LocalDate
import java.time.Month.{AUGUST, FEBRUARY, JANUARY, SEPTEMBER}

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.domain._
import org.ssm.parser.module.PeriodInformationModule._

@RunWith(classOf[JUnitRunner])
class PeriodInformationModuleProcessSuite extends FunSuite with Matchers {

  trait TestFixture {
    val input: Input = 0 -> "12AUG17 30SEP17 1/W1 6/LX545A/1"
    val invaliInput: Input = 0 -> "77AUG17 30SEP17 1/W1 6/LX545A/1"

    val messageReference = Some("24MAY00144E003")
    val periodInformation = PeriodInformation(
      LocalDate.of(2017, JANUARY, 7),
      LocalDate.of(2017, FEBRUARY, 1),
      List(MONDAY),
      OneWeekFrequencyRate)
    val newPeriodInformation = PeriodInformation(
      LocalDate.of(2017, AUGUST, 12),
      LocalDate.of(2017, SEPTEMBER, 30),
      List(MONDAY),
      OneWeekFrequencyRate)

    val subMessage1 = SubMessage(Some(NEW), None, List())
    val subMessage2 = SubMessage(Some(CNL), None, List(periodInformation))
  }

  test("Process input - state with one SubMessage and empty PeriodInformation") {
    new TestFixture {
      val state = SSMMessage(messageReference, List(subMessage1))

      val actual: SSMMessage = process(input, state)

      actual.messageReference should be(state.messageReference)
      actual.subMessages should be(
        subMessage1.copy(periodInformations = List(newPeriodInformation)) :: Nil)
    }
  }

  test("Process input - state with two SubMessages and one PeriodInformation") {
    new TestFixture {
      val state = SSMMessage(messageReference, List(subMessage2, subMessage1))

      val actual: SSMMessage = process(input, state)

      actual.messageReference should be(state.messageReference)
      actual.subMessages should be(
        subMessage2.copy(periodInformations = newPeriodInformation :: periodInformation :: Nil) :: subMessage1 :: Nil)
    }
  }

  test("Process input with failure - from date invalid") {
    new TestFixture {
      intercept[Exception] {
        val state = SSMMessage(messageReference, List(subMessage1))
        process(invaliInput, state)
      }
    }
  }
}
