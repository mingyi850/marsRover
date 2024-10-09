package model

import exception.MarsRoverException.OrientationValidationError

import scala.util.{Failure, Success, Try}

sealed trait Orientation

object Orientation {
  case object North extends Orientation
  case object South extends Orientation
  case object East extends Orientation
  case object West extends Orientation

  def fromString(str: String): Try[Orientation] = str match {
    case "N" => Success(North)
    case "S" => Success(South)
    case "E" => Success(East)
    case "W" => Success(West)
    case _ => Failure(OrientationValidationError(str))
  }

  def rotateRight(original: Orientation): Orientation = original match {
    case North => East
    case East => South
    case South => West
    case West => North
  }

  def rotateLeft(original: Orientation): Orientation = original match {
    case North => West
    case West => South
    case South => East
    case East => North
  }

  def toString(orientation: Orientation): String = orientation match {
    case North => "N"
    case South => "S"
    case East => "E"
    case West => "W"
  }
}