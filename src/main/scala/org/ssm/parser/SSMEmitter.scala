package org.ssm.parser

import org.ssm.parser.SSMProcess.Result
import org.ssm.parser.model.SSMMessage

object SSMEmitter extends Function1[SSMMessage, Result] {
  override def apply(state: SSMMessage): Result = ???
}
