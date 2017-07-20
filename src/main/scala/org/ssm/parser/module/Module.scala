package org.ssm.parser.module

import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.domain.SSMMessage

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

object ToAddressModule extends NoExtractSSMModule {
  def canProcess(input: Input): Boolean = {
    val toAddressRegex = "(^Q)(.*$)".r

    input._2 match {
      case toAddressRegex(_, _) =>
        true
      case _ =>
        false
    }
  }
}

object FromAddressModule extends NoExtractSSMModule {
  def canProcess(input: Input): Boolean = ???
}

object IdentifierModule extends NoExtractSSMModule {
  def canProcess(input: Input): Boolean = ???
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
