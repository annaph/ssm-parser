package org.ssm.parser.module

import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.model.SSMMessage
import org.ssm.parser.module.SSMModule._
import org.ssm.parser.util.PipeOps.toPipe

import scala.util.{Success, Try}

object MessageReferenceModule extends SSMModule {
  type R = String
  type F = String

  def canProcess(input: Input): Boolean = {
    matchTwoGroupLine(input._2, """(^\d{2}[A-Z]{3}\d{5}[A-Z]\d{3})(.*$)""".r)
  }

  def process(input: Input, state: SSMMessage): SSMMessage = {
    val msgReference = input |> extract |> format |> {
      _.get
    }
    state copy (messageReference = Some(msgReference))
  }

  private[module] def extract(input: Input): String =
    input._2 take 14

  private[module] def format(rawData: String): Try[String] =
    Success(rawData)
}
