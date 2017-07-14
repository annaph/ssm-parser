package org.ssm.parser

import org.ssm.parser.domain.SSMMessage

sealed trait SSMProcess[I, S, O]

object SSMProcess {
  type Input = (Int, String)

  def parseSSMMessage(str: String): SSMMessage = ???

  def toJsonString(sSMMessage: SSMMessage): String = ???

  private case class Start[I, S, O](
      initState: S,
      next: SSMProcess[I, S, O])
    extends SSMProcess[I, S, O]

  private case class Await[I, S, O](
      recv: (Option[I], SSMProcess[I, S, O]) => SSMProcess[I, S, O])
    extends SSMProcess[I, S, O]

  private case class Parse[I, S, O](
      run: (I, S) => S,
      next: SSMProcess[I, S, O])
    extends SSMProcess[I, S, O]

  private def parse[I, S, O]: SSMProcess[I, S, O] = ???

  private def run(p: SSMProcess[Input, SSMMessage, SSMMessage]): SSMMessage =
    run(p)

}
