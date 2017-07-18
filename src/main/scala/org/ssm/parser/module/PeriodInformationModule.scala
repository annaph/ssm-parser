package org.ssm.parser.module

import org.ssm.parser.SSMProcess.Input

import scala.util.Try
import org.ssm.parser.domain.{PeriodInformation, SSMMessage}

object PeriodInformationModule extends SSMModule {
  type R = String
  type F = PeriodInformation

  def canProcess(input: Input): Boolean = ???

  def process(input: Input, state: SSMMessage): SSMMessage = ???

  private[module] def extract(input: Input): String = ???

  private[module] def format(r: String): Try[PeriodInformation] = ???
}
