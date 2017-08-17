package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.domain._
import org.ssm.parser.module.ActionModule.format

@RunWith(classOf[JUnitRunner])
class ActionModuleFormatSuite extends FunSuite with Matchers {

  test("Format raw data - NEW") {
    val actual: SubMessageAction = format("NEW").get

    actual should be(NEW)
  }

  test("Format raw data - CNL") {
    val actual: SubMessageAction = format("CNL").get

    actual should be(CNL)
  }

  test("Format raw data - TIM") {
    val actual: SubMessageAction = format("TIM").get

    actual should be(TIM)
  }

  test("Format raw data - EQT") {
    val actual: SubMessageAction = format("EQT").get

    actual should be(EQT)
  }

  test("Format raw data - RPL") {
    val actual: SubMessageAction = format("RPL").get

    actual should be(RPL)
  }

  test("Format raw data - SKD") {
    val actual: SubMessageAction = format("SKD").get

    actual should be(SKD)
  }

  test("Format raw data - ACK") {
    val actual: SubMessageAction = format("ACK").get

    actual should be(ACK)
  }

  test("Format raw data - ADM") {
    val actual: SubMessageAction = format("ADM").get

    actual should be(ADM)
  }

  test("Format raw data - CON") {
    val actual: SubMessageAction = format("CON").get

    actual should be(COV)
  }

  test("Format raw data - FLT") {
    val actual: SubMessageAction = format("FLT").get

    actual should be(FLT)
  }

  test("Format raw data - NAC") {
    val actual: SubMessageAction = format("NAC").get

    actual should be(NAC)
  }

  test("Format raw data - REV") {
    val actual: SubMessageAction = format("REV").get

    actual should be(REV)
  }

  test("Format raw data - RSD") {
    val actual: SubMessageAction = format("RSD").get

    actual should be(RSD)
  }
}
