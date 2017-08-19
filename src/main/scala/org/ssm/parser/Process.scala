package org.ssm.parser

import org.ssm.parser.model.SSMMessage

import scala.util.Properties.lineSeparator

sealed trait Process[I, S, O] {
  def apply(s: Stream[I]): O = ???
}

object Process {
  case object End extends Throwable
  case object Kill extends Throwable

  case class Start[I, S, O](
      init: () => (Option[S], Process[I, S, O]))
    extends Process[I, S, O]

  case class Await[I, S, O](
      recv: (Option[I], Process[I, S, O]) => Process[I, S, O])
    extends Process[I, S, O]

  case class Parse[K, I, S, O](
      kind: K,
      run: (I, S) => S,
      next: Process[I, S, O])
    extends Process[I, S, O]

  case class Emit[I, S, O](
      o: O,
      next: Process[I, S, O])
    extends Process[I, S, O]

  case class Halt[I, S, O](
      i: Option[I],
      e: Throwable)
    extends Process[I, S, O]

  def start[I, S, O](init: => (Option[S], Process[I, S, O])): Process[I, S, O] =
    Start(() => init)

  def await[I, S, O](recv: (Option[I], Process[I, S, O]) => Process[I, S, O]): Process[I, S, O] =
    Await(recv)

  def parse[K, I, S, O](kind: K)(next: Process[I, S, O])(run: (I, S) => S): Process[I, S, O] =
    Parse(kind, run, next)

  def emit[I, S, O](o: O)(next: Process[I, S, O] = end[I, S, O]): Process[I, S, O] =
    Emit(o, next)

  def end[I, S, O]: Process[I, S, O] =
    Halt(None, End)

  def kill[I, S, O](i: I): Process[I, S, O] =
    Halt(Some(i), Kill)
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
    ???
    //ssmProcess((str split lineSeparator).toStream)

  def toJsonString(ssmMessage: SSMMessage): String = ???

  private val ssmProcess: SSMProcess = ???
}
