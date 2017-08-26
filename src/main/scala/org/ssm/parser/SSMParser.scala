package org.ssm.parser

import org.ssm.parser.SSMProcess.{Input, Result}
import org.ssm.parser.model.SSMMessage

object SSMParser extends Function3[Input, SSMMessage, SSMParseKind, Result] {
  override def apply(input: Input, state: SSMMessage, kind: SSMParseKind): Result = ???
}
