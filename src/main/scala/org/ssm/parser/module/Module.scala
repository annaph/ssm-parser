package org.ssm.parser.module

import java.time.Month
import java.time.Month._

import org.ssm.parser.SSMProcess.Input
import org.ssm.parser.model.SSMMessage

import scala.util.matching.Regex
import scala.util.{Success, Try}

trait Module[I, S] {
  type R
  type F

  def canProcess(input: I): Boolean

  def process(input: I, state: S): S

  private[module] def extract(input: I): R

  private[module] def format(rawData: R): Try[F]
}

trait SSMModule extends Module[Input, SSMMessage]

object SSMModule {
  def matchOneGroupLine(line: String, regex: Regex): Boolean = line match {
    case regex(_) =>
      true
    case _ =>
      false
  }

  def matchTwoGroupLine(line: String, regex: Regex): Boolean = line match {
    case regex(_, _) =>
      true
    case _ =>
      false
  }

  def matchThreeGroupLine(line: String, regex: Regex): Boolean = line match {
    case regex(_, _, _) =>
      true
    case _ =>
      false
  }

  def toMonth(str: String): Option[Month] = str match {
    case "JAN" =>
      Some(JANUARY)
    case "FEB" =>
      Some(FEBRUARY)
    case "MAR" =>
      Some(MARCH)
    case "APR" =>
      Some(APRIL)
    case "MAY" =>
      Some(MAY)
    case "JUN" =>
      Some(JUNE)
    case "JUL" =>
      Some(JULY)
    case "AUG" =>
      Some(AUGUST)
    case "SEP" =>
      Some(SEPTEMBER)
    case "OCT" =>
      Some(OCTOBER)
    case "NOV" =>
      Some(NOVEMBER)
    case "DEC" =>
      Some(DECEMBER)
    case _ =>
      None
  }
}

abstract class NoExtractModule extends SSMModule {
  type R = Unit
  type F = Unit

  override def process(input: Input, state: SSMMessage): SSMMessage =
    state

  override private[module] def extract(input: Input): Unit =
    ()

  override private[module] def format(rawData: Unit): Try[Unit] =
    Success(rawData)
}

import org.ssm.parser.module.SSMModule._

object ToAddressModule extends NoExtractModule {
  def canProcess(input: Input): Boolean =
    matchTwoGroupLine(input._2, "(^Q)(.*$)".r)
}

object FromAddressModule extends NoExtractModule {
  def canProcess(input: Input): Boolean =
    matchTwoGroupLine(input._2, "(^[.])(.*$)".r)
}

object IdentifierModule extends NoExtractModule {
  def canProcess(input: Input): Boolean =
    matchOneGroupLine(input._2, "(^SSM$)".r)
}

object TimeModeModule extends NoExtractModule {
  def canProcess(input: Input): Boolean =
    matchOneGroupLine(input._2, "(^UTC|LT$)".r)
}

object OtherInformationModule extends NoExtractModule {
  def canProcess(input: Input): Boolean =
    matchOneGroupLine(input._2, "(.+$)".r)
}

object SubMessageModule extends NoExtractModule {
  def canProcess(input: Input): Boolean =
    matchOneGroupLine(input._2, "(^//$)".r)
}
