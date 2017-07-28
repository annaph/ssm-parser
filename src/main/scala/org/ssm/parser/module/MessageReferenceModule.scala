package org.ssm.parser.module

import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.domain.SSMMessage
import org.ssm.parser.module.SSMModule._

import scala.util.{Success, Try}

object MessageReferenceModule extends SSMModule {
  type R = String
  type F = String

  def canProcess(input: Input): Boolean = {
    matchTwoGroupLine(input._2, """(^\d{2}[A-Z]{3}\d{5}[A-Z]\d{3})(.*$)""".r)
  }

  def process(input: Input, state: SSMMessage): SSMMessage =
    SSMMessage((this format extract(input)).get, state.subMessages)

  private[module] def extract(input: Input): String =
    input._2 take 14

  private[module] def format(r: String): Try[String] =
    Success(r)
}
