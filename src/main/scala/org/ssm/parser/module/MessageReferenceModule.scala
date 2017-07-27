package org.ssm.parser.module

import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.domain.SSMMessage

import scala.util.Try

import SSMModule._

object MessageReferenceModule extends SSMModule {
  type R = String
  type F = String

  def canProcess(input: Input): Boolean = {
    matchTwoGroupLine(input._2, """(^\d{2}[A-Z]{3}\d{5}[A-Z]\d{3})(.*$)""".r)
  }

  def process(input: Input, state: SSMMessage): SSMMessage = ???

  private[module] def extract(input: Input): String = ???

  private[module] def format(r: String): Try[String] = ???
}
