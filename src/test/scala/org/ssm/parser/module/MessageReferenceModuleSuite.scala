package org.ssm.parser.module

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers
import org.scalatest.{FunSuite, Matchers}
import org.ssm.parser.domain.SSMMessage
import org.ssm.parser.module.MessageReferenceModule._

@RunWith(classOf[JUnitRunner])
class MessageReferenceModuleSuite extends FunSuite with Matchers with Checkers {

  test("Extract input - 24MAY00144E003/REF 123/449") {
    val actual: String = extract(0 -> "24MAY00144E003/REF 123/449")

    actual should be("24MAY00144E003")
  }

  test("Extract input - 24MAY00144E003") {
    val actual: String = extract(0 -> "24MAY00144E003")

    actual should be("24MAY00144E003")
  }

  test("Format raw data") {
    val actual: String = format("24MAY00144E003").get

    actual should be("24MAY00144E003")
  }

  test("Process input") {
    val input = 0 -> "24MAY00144E003/REF 123/449"
    val state = SSMMessage(None, List())

    val actual: SSMMessage = process(input, state)

    actual.messageReference should be(Some("24MAY00144E003"))
    actual.subMessages should be(Nil)
  }
}
