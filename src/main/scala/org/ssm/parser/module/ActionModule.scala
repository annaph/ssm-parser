package org.ssm.parser.module

import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.domain._
import org.ssm.parser.module.SSMModule.matchTwoGroupLine

import scala.util.{Success, Try}

object ActionModule extends SSMModule {
  type R = String
  type F = SubMessageAction

  def canProcess(input: Input): Boolean =
    matchTwoGroupLine(input._2, """(^NEW|CNL|RPL|SKD|ACK|ADM|CON|EQT|FLT|NAC|REV|RSD|TIM)(.*$)""".r)

  def process(input: Input, state: SSMMessage): SSMMessage = ???

  private[module] def extract(input: Input): String =
    input._2 take 3

  private[module] def format(rawData: String): Try[SubMessageAction] = rawData match {
    case "NEW" =>
      Success(NEW)
    case "CNL" =>
      Success(CNL)
    case "TIM" =>
      Success(TIM)
    case "EQT" =>
      Success(EQT)
    case "RPL" =>
      Success(RPL)
    case "SKD" =>
      Success(SKD)
    case "ACK" =>
      Success(ACK)
    case "ADM" =>
      Success(ADM)
    case "CON" =>
      Success(CON)
    case "FLT" =>
      Success(FLT)
    case "NAC" =>
      Success(NAC)
    case "REV" =>
      Success(REV)
    case _ =>
      Success(RSD)
  }
}
