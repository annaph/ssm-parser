package org.ssm.parser

import org.ssm.parser.Process.{Start, kill, parse}
import org.ssm.parser.SSMProcess._
import org.ssm.parser.module._
import org.ssm.parser.Process.Parse

object SSMReceiver extends Function2[Option[Input], SSMProcess, SSMProcess] {
  implicit val ssmParser = SSMParser

  override def apply(input: Option[Input], prev: SSMProcess): SSMProcess = (input, prev) match {
    case (Some(in), Start(_)) =>
      if (ToAddressModule canProcess in) parse(ToAddress) else kill(in)
    case (Some(in), Parse(ToAddress, _)) =>
      if (FromAddressModule canProcess in) parse(FromAddress) else kill(in)
    case (Some(in), Parse(FromAddress, _)) =>
      if (IdentifierModule canProcess in) parse(Identifier) else kill(in)
    case (Some(in), Parse(Identifier, _)) =>
      if (TimeModeModule canProcess in) parse(TimeMode) else kill(in)
    case (Some(in), Parse(TimeMode, _)) =>
      if (MessageReferenceModule canProcess in) parse(Reference) else kill(in)
    case _ =>
      ???
  }
}
