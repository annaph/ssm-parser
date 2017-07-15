package org.ssm.parser

import org.ssm.parser.domain.SSMMessage

sealed trait Process[I, S, O] {
  def apply(): O = ???
}

object Process {

  private case class Start[I, S, O](
      initState: S,
      next: Process[I, S, O])
    extends Process[I, S, O]

  private case class Await[I, S, O](
      recv: (Option[I], Process[I, S, O]) => Process[I, S, O])
    extends Process[I, S, O]

  private case class Parse[K, I, S, O](
      kind: K,
      run: (I, S) => S,
      next: Process[I, S, O])
    extends Process[I, S, O]
}

sealed trait SSMParseKind
case object FromAddress extends SSMParseKind
case object ToAddress extends SSMParseKind
case object Identifier extends SSMParseKind
case object TimeMode extends SSMParseKind
case object Reference extends SSMParseKind
case object Action extends SSMParseKind
case object FlightInformation extends SSMParseKind
case object PeriodInformation extends SSMParseKind
case object OtherInformation extends SSMParseKind
case object SubAction extends SSMParseKind

object SSMProcess {
  type Input = (Int, String)
  type SSMProcess = Process[Input, SSMMessage, SSMMessage]

  def parseSSMMessage(str: String): SSMMessage =
    ssmProcess()

  def toJsonString(sSMMessage: SSMMessage): String = ???

  private val ssmProcess: SSMProcess = ???

  private val ssmReceiver: (Option[Input], SSMProcess) => SSMProcess =
    (input, prev) => {
      ???
    }
}
