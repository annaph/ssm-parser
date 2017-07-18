package org.ssm.parser.domain

import java.time.{DayOfWeek, LocalDate}

sealed trait FrequencyRate
case object OneWeekFrequencyRate extends FrequencyRate
case object TwoWeekFrequencyRate extends FrequencyRate

sealed trait SubMessageAction
case object NEW extends SubMessageAction
case object CNL extends SubMessageAction
case object TIM extends SubMessageAction
case object EQT extends SubMessageAction
case object RPL extends SubMessageAction
case object SKD extends SubMessageAction
case object ACK extends SubMessageAction
case object ADM extends SubMessageAction
case object CON extends SubMessageAction
case object FLT extends SubMessageAction
case object NAC extends SubMessageAction
case object REV extends SubMessageAction
case object RSD extends SubMessageAction

case class SSMMessage(
  messageReference: String,
  subMessages: List[SubMessage])

case class SubMessage(
  action: SubMessageAction,
  flightDesignator: FlightDesignator,
  periodInformations: List[PeriodInformation])

case class FlightDesignator(
  airlineDesignator: String,
  flightNumber: Int)

case class PeriodInformation(
  fromDate: LocalDate,
  toDate: LocalDate,
  daysOfOperation: Set[DayOfWeek],
  frequencyRate: FrequencyRate)
