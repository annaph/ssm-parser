package org.ssm.parser.module

import org.ssm.parser.SSMProcess.Input

import scala.util.Try
import org.ssm.parser.domain.{PeriodInformation, SSMMessage}
import org.ssm.parser.module.SSMModule.matchThreeGroupLine

object PeriodInformationModule extends SSMModule {
  type R = String
  type F = PeriodInformation

  private val reg = """(^\d{2}[A-Z]{3}\d{0,2}\b)(.\d{2}[A-Z]{3}\d{0,2}\b)(.*$)""".r

  def canProcess(input: Input): Boolean =
    matchThreeGroupLine(input._2, reg)

  def process(input: Input, state: SSMMessage): SSMMessage = ???

  private[module] def extract(input: Input): String = ???

  private[module] def format(r: String): Try[PeriodInformation] = ???
}
