package org.ssm.parser.domain

case class PlainSSMMessage(
  messageReference: String,
  subMessages: List[PlainSubMessage])

case class PlainSubMessage(
  flightDesignator: PlainFlightDesignator,
  periodInformations: List[PlainPeriodInformation])

case class PlainFlightDesignator(
  airlineDesignator: String,
  flightNumber: String)

case class PlainPeriodInformation(
  fromDate: String,
  toDate: String,
  daysOfOperation: String,
  frequencyRate: String)
