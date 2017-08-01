package org.ssm.parser.module

import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.domain.{FlightDesignator, SSMMessage}
import org.ssm.parser.module.SSMModule.matchThreeGroupLine
import org.ssm.parser.util.PipeOps.toPipe

import scala.util.{Success, Try}

object FlightInformationModule extends SSMModule {
  type R = (String, String)
  type F = FlightDesignator

  private val reg = """(^.{2}[A-Z]?)(\d{3}\d?)(.*$)""".r

  def canProcess(input: Input): Boolean =
    matchThreeGroupLine(input._2, reg)

  def process(input: Input, state: SSMMessage): SSMMessage = {
    val flightDesignator = input |> extract |> format |> {
      _.get
    }
    val subMessage = state.subMessages(0).copy(flightDesignator = Some(flightDesignator))

    state.copy(subMessages = subMessage :: state.subMessages.drop(1))
  }

  private[module] def extract(input: Input): (String, String) = input._2 match {
    case reg(airlineDesignator, flightNumber, _) =>
      airlineDesignator -> flightNumber
  }

  private[module] def format(rawData: (String, String)): Try[FlightDesignator] =
    Success(FlightDesignator(rawData._1, rawData._2.toInt))
}
