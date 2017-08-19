package org.ssm.parser.module

import java.time.LocalDate.now
import java.time.{DayOfWeek, LocalDate, Month}

import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.domain._
import org.ssm.parser.util.PipeOps.toPipe

import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

object PeriodInformationModule extends SSMModule {
  type ExtractedData = (String, String, String, Option[String])

  type R = ExtractedData
  type F = PeriodInformation

  private val reg = """(^\d{2}[A-Z]{3}\d{0,2}\s)(\d{2}[A-Z]{3}\d{0,2}\s)(\d{1,7})(.*$)""".r

  def canProcess(input: Input): Boolean = input._2 match {
    case reg(fromDate, toDate, _, frequencyRate)
      if (frequencyRate.startsWith("/") && frequencyRate.length >= 3) || !frequencyRate.startsWith("/") =>
      (fromDate.length, toDate.length) match {
        case (6, 6) | (6, 8) | (8, 6) | (8, 8) =>
          true
        case _ =>
          false
      }
    case _ =>
      false
  }

  def process(input: Input, state: SSMMessage): SSMMessage =
    input |> extract |> format match {
      case Success(newPeriodInformation) =>
        val firstSubMessage = state.subMessages.head
        val newSubMessage = firstSubMessage copy (periodInformations =
          newPeriodInformation :: firstSubMessage.periodInformations)

        state copy (subMessages = newSubMessage :: (state.subMessages drop 1))
      case Failure(e) =>
        throw new Exception(e)
    }

  private[module] def extract(input: Input): ExtractedData = input._2 match {
    case reg(fromDate, toDate, daysOfOperation, rest) if rest.startsWith("/") && rest.length >= 3 =>
      (fromDate.trim(), toDate.trim(), daysOfOperation, Some(rest substring(1, 3)))
    case reg(fromDate, toDate, daysOfOperation, _) =>
      (fromDate.trim(), toDate.trim(), daysOfOperation, None)
  }

  private[module] def format(rawData: ExtractedData): Try[PeriodInformation] =
    for {
      (fromDate, toDate) <- formatDates(rawData._1, rawData._2)
      daysOfOperation <- formatDaysOfOperation(rawData._3)
      frequencyRate <- rawData._4 match {
        case Some(str) =>
          formatFrequencyRate(str)
        case None =>
          Success(OneWeekFrequencyRate)
      }
    } yield PeriodInformation(fromDate, toDate, daysOfOperation, frequencyRate)

  private[module] def formatDates(fromDate: String, toDate: String): Try[(LocalDate, LocalDate)] =
    Try {
      fromDate.toLocalDate -> toDate.toLocalDate
    } match {
      case Success((from, to)) if !(to isBefore from) =>
        Success(from -> to)
      case Success(_) =>
        Failure(new Exception(s"To date '$toDate' must be equal or greater than from date '$fromDate'"))
      case Failure(e) =>
        Failure(new Exception(s"From date '$fromDate' and/or to date '$toDate' invalid", e))
    }

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

  private[module] def formatFrequencyRate(frequencyRate: String): Try[FrequencyRate] = frequencyRate match {
    case "W1" =>
      Success(OneWeekFrequencyRate)
    case "W2" =>
      Success(TwoWeekFrequencyRate)
    case _ =>
      Failure(new Exception(s"Frequency rate in '$frequencyRate' invalid"))
  }

  object Date {
    def unapply(whole: String): Option[(Int, Month, Option[Int])] = {
      Some((whole.day, whole.month, whole.year))
    }
  }

  implicit def toDateOps(date: String): DateOps =
    new DateOps(date)

  class DateOps(date: String) {
    def toLocalDate: LocalDate = date match {
      case Date(day, month, Some(year)) =>
        LocalDate of(year, month, day)
      case Date(day, month, None) =>
        LocalDate of(now.getYear, month, day)
    }

    def day: Int =
      (date take 2).toInt

    def month: Month =
      SSMModule.toMonth(date substring(2, 5)).get

    def year: Option[Int] =
      if (date.length == 8) {
        Some(2000 + date.substring(6).toInt)
      } else {
        None
      }
  }

}
