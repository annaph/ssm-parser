package org.ssm.parser.module

import org.ssm.parser.SSMProcess._
import org.ssm.parser.domain.SSMMessage

import scala.util.Try

trait SSMModule[I, S] {
  type R
  type F

  def detect(input: I): Boolean

  def parse(input: I, state: S): S

  private[module] def extract(input: I): R

  private[module] def format(r: R): Try[F]

}
