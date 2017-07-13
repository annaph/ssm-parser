package org.ssm.parser.domain

import java.time.{DayOfWeek, LocalDate}

sealed trait FrequencyRate
case object OneWeekFrequencyRate extends FrequencyRate
case object TwoWeekFrequencyRate extends FrequencyRate

case class SSMMessage(
  messageReference: String,
  subMessages: List[SubMessage])

case class SubMessage(
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
