package org.ssm.parser.module

import java.time.{DayOfWeek, LocalDate}

import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.domain.{FrequencyRate, PeriodInformation, SSMMessage}

import scala.util.Try

object PeriodInformationModule extends SSMModule {
  type ExtractedData = (String, String, String, Option[String])

  type R = ExtractedData
  type F = PeriodInformation

  private val reg = """(^\d{2}[A-Z]{3}\d{0,2}\s)(\d{2}[A-Z]{3}\d{0,2}\s)(\d{1,7})(.*$)""".r

  def canProcess(input: Input): Boolean = input._2 match {
    case reg(_, _, _, rest) if (rest.startsWith("/") && rest.size >= 3) =>
      true
    case reg(_, _, _, rest) if !rest.startsWith("/") =>
      true
    case _ =>
      false
  }

  def process(input: Input, state: SSMMessage): SSMMessage = ???

  private[module] def extract(input: Input): ExtractedData = input._2 match {
    case reg(fromDate, toDate, daysOfOperation, rest) if (rest.startsWith("/") && rest.length >= 3) =>
      (fromDate.trim(), toDate.trim(), daysOfOperation, Some(rest substring(1, 3)))
    case reg(fromDate, toDate, daysOfOperation, _) =>
      (fromDate.trim(), toDate.trim(), daysOfOperation, None)
  }

  private[module] def format(rawData: ExtractedData): Try[PeriodInformation] = ???

  private def formatDates(fromDate: String, toDate: String): Try[(LocalDate, LocalDate)] = ???

  private def formatDaysOfOperation(daysOfOperation: String): Try[Set[DayOfWeek]] = ???

  private[module] def formatFrequencyRate(frequencyRate: String): Try[FrequencyRate] = ???
}
