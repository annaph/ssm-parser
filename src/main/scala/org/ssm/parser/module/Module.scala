package org.ssm.parser.module

import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.domain.SSMMessage

import scala.util.matching.Regex
import scala.util.{Success, Try}

trait Module[I, S] {
  type R
  type F

  def canProcess(input: I): Boolean

  def process(input: I, state: S): S

  private[module] def extract(input: I): R

  private[module] def format(r: R): Try[F]
}

trait SSMModule extends Module[Input, SSMMessage]

trait NoExtractSSMModule extends SSMModule {
  type R = Unit
  type F = Unit

  override def process(input: Input, state: SSMMessage): SSMMessage = ???

  override private[module] def extract(input: Input): Unit =
    ()

  override private[module] def format(r: Unit): Try[Unit] =
    Success(())
}

object SSMModule {
  def matchOneGroupLine(line: String, regex: Regex): Boolean = line match {
    case regex(_) =>
      true
    case _ =>
      false
  }

  def matchTwoGroupLine(line: String, regex: Regex): Boolean = line match {
    case regex(_, _) =>
      true
    case _ =>
      false
  }
}

import org.ssm.parser.module.SSMModule._

object ToAddressModule extends NoExtractSSMModule {
  def canProcess(input: Input): Boolean =
    matchTwoGroupLine(input._2, "(^Q)(.*$)".r)
}

object FromAddressModule extends NoExtractSSMModule {
  def canProcess(input: Input): Boolean =
    matchTwoGroupLine(input._2, "(^[.])(.*$)".r)
}

object IdentifierModule extends NoExtractSSMModule {
  def canProcess(input: Input): Boolean =
    matchOneGroupLine(input._2, "(^SSM$)".r)
}

object TimeModeModule extends NoExtractSSMModule {
  def canProcess(input: Input): Boolean = ???
}

object OtherInformationModule extends NoExtractSSMModule {
  def canProcess(input: Input): Boolean = ???
}

object SubMessageModule extends NoExtractSSMModule {
  def canProcess(input: Input): Boolean = ???
}
