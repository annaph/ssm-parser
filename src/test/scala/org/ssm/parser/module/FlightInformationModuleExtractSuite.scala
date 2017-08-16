package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.module.FlightInformationModule.extract

@RunWith(classOf[JUnitRunner])
class FlightInformationModuleExtractSuite extends FunSuite with Matchers {

  test("Extract input - LX983") {
    val actual: (String, String) = extract(0 -> "LX983")

    actual should be("LX" -> "983")
  }

  test("Extract input - LX983A 1/LX/LH") {
    val actual: (String, String) = extract(0 -> "LX983A 1/LX/LH")

    actual should be("LX" -> "983")
  }

  test("Extract input - LX1074A 1/LX/LH") {
    val actual: (String, String) = extract(0 -> "LX1074A 1/LX/LH")

    actual should be("LX" -> "1074")
  }

  test("Extract input - LXA983A 1/LX/LH") {
    val actual: (String, String) = extract(0 -> "LXA983A 1/LX/LH")

    actual should be("LXA" -> "983")
  }

  test("Extract input - LXA1074A 1/LX/LH") {
    val actual: (String, String) = extract(0 -> "LXA1074A 1/LX/LH")

    actual should be("LXA" -> "1074")
  }
}
