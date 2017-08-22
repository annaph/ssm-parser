package org.ssm.parser

import org.ssm.parser.SSMProcess._

object SSMReceiver extends Function2[Option[Input], SSMProcess, SSMProcess] {
  override def apply(input: Option[Input], prev: SSMProcess): SSMProcess = {
    ???
  }
}
