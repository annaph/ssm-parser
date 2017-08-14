package org.ssm.parser.module

import java.time.{DayOfWeek, LocalDate}

import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.domain._

import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

object PeriodInformationModule extends SSMModule {
  type ExtractedData = (String, String, String, Option[String])

  type R = ExtractedData
  type F = PeriodInformation

  private val reg = """(^\d{2}[A-Z]{3}\d{0,2}\s)(\d{2}[A-Z]{3}\d{0,2}\s)(\d{1,7})(.*$)""".r

  def canProcess(input: Input): Boolean = input._2 match {
    case reg(_, _, _, rest) if rest.startsWith("/") && rest.length >= 3 =>
      true
    case reg(_, _, _, rest) if !rest.startsWith("/") =>
      true
    case _ =>
      false
  }

  def process(input: Input, state: SSMMessage): SSMMessage = ???

  private[module] def extract(input: Input): ExtractedData = input._2 match {
    case reg(fromDate, toDate, daysOfOperation, rest) if rest.startsWith("/") && rest.length >= 3 =>
      (fromDate.trim(), toDate.trim(), daysOfOperation, Some(rest substring(1, 3)))
    case reg(fromDate, toDate, daysOfOperation, _) =>
      (fromDate.trim(), toDate.trim(), daysOfOperation, None)
  }

  private[module] def format(rawData: ExtractedData): Try[PeriodInformation] = ???

  private[module] def formatDates(fromDate: String, toDate: String): Try[(LocalDate, LocalDate)] = ???

  private[module] def formatDaysOfOperation(daysOfOperation: String): Try[List[DayOfWeek]] = {
    import scala.collection.mutable
    type Days = Try[mutable.ListBuffer[DayOfWeek]]

    @tailrec
    def go(cs: List[Char], prev: Int, acc: Days): Days = cs match {
      case Nil =>
        acc
      case h :: t =>
        Try {
          h.toString.toInt
        } match {
          case Success(i) if i > prev =>
            go(t, i, acc map {
              _ += DayOfWeek of i
            })
          case _ =>
            Failure(new Exception(s"Days of operation '$daysOfOperation' invalid"))
        }
    }

    go(daysOfOperation.toCharArray.toList, 0, Success {
      mutable.ListBuffer()
    }) map {
      _.toList
    }
  }

  private[module] def formatFrequencyRate(frequencyRate: String): Try[FrequencyRate] =
    if (frequencyRate.isEmpty || (frequencyRate startsWith " ")) {
      Success(OneWeekFrequencyRate)
    } else if (frequencyRate startsWith "/") {
      frequencyRate.take(3) match {
        case "/W1" =>
          Success(OneWeekFrequencyRate)
        case "/W2" =>
          Success(TwoWeekFrequencyRate)
        case _ =>
          Failure(new Exception(s"Frequency rate in '$frequencyRate' invalid"))
      }
    } else {
      Failure(new Exception(s"Frequency rate in '$frequencyRate' invalid"))
    }
}
