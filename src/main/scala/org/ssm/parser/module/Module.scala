package org.ssm.parser.module

import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.domain.SSMMessage

import scala.util
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

  def canProcess(input: Input): Boolean

  def process(input: Input, state: SSMMessage): SSMMessage = ???

  override private[module] def extract(input: Input): Unit =
    ()

  override private[module] def format(r: Unit): Try[Unit] =
    Success(())
}
