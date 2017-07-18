package org.ssm.parser

import SSMProcess._

class SSMReceiver extends Function2[Option[Input], SSMProcess, SSMProcess] {
  override def apply(input: Option[Input], prev: SSMProcess): SSMProcess = ???
}

object SSMReceiver {
  def apply(): SSMReceiver = new SSMReceiver()
}
