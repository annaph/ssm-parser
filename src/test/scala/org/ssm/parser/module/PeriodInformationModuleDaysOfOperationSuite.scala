package org.ssm.parser.module

import java.time.DayOfWeek
import java.time.DayOfWeek._

import org.junit.runner.RunWith
import org.scalacheck.Prop.{BooleanOperators, forAll}
import org.scalacheck.{Arbitrary, Gen, Prop}
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.module.PeriodInformationModule.formatDaysOfOperation

import scala.util.{Failure, Success, Try}

@RunWith(classOf[JUnitRunner])
class PeriodInformationModuleDaysOfOperationSuite extends FunSuite with Matchers with Checkers {

  implicit val daysOfOperationAr: Arbitrary[String] = Arbitrary {
    val gen1 = Gen.oneOf(1, 2, 3)
    val gen2 = Gen.oneOf(2, 3, 4)
    val gen3 = Gen.oneOf(3, 4, 5)
    val gen4 = Gen.oneOf(4, 5, 6)
    val gen5 = Gen.oneOf(5, 6, 7)
    val gen6 = Gen.oneOf(6, 7, 1)
    val gen7 = Gen.oneOf(7, 1, 2)

    for {
      days <- Gen.someOf(gen1, gen2, gen3, gen4, gen5, gen6, gen7)
    } yield days mkString ""
  }

  test("Format days of operation - 1234567") {
    val daysOfOperation = "1234567"
    val actual: List[DayOfWeek] = formatDaysOfOperation(daysOfOperation).get

    actual should be(List(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY))
  }

  test("Format days of operation with failure - 321") {
    val daysOfOperation = "321"
    val actual: Try[List[DayOfWeek]] = formatDaysOfOperation(daysOfOperation)

    actual.isFailure should be(true)
  }

  test("Format days of operation") {
    val propDaysOfOperation: Prop = forAll { (str: String) =>
      (!str.isEmpty) ==> {
        formatDaysOfOperation(str) match {
          case Success(daysOfOperation) =>
            daysOfOperation.map(_.getValue).distinct.sorted == daysOfOperation.map(_.getValue)
          case Failure(_) =>
            str.toCharArray.map(_.toString.toInt).distinct.sorted != str.toCharArray.map(_.toString.toInt)
        }
      }
    }

    check(propDaysOfOperation)
  }
}
