package org.ssm.parser.module

import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.domain.{SSMMessage, SubMessageAction}

import scala.util.Try

object ActionModule extends SSMModule {
  type R = String
  type F = SubMessageAction

  def canProcess(input: Input): Boolean = ???

  def process(input: Input, state: SSMMessage): SSMMessage = ???

  private[module] def extract(input: Input): String = ???

  private[module] def format(r: String): Try[SubMessageAction] = ???
}
