package org.ssm.parser

import org.ssm.parser.SSMProcess.{Input, ParseResult}
import org.ssm.parser.model.SSMMessage

object SSMParser extends Function3[Input, SSMMessage, SSMParseKind, ParseResult] {
  override def apply(input: Input, state: SSMMessage, kind: SSMParseKind): ParseResult = ???
}
