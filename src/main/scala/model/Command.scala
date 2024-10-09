package model

import exception.MarsRoverException.CommandValidationError

import scala.util.{Failure, Success, Try}

sealed trait Command

object Command {
  case object RotateLeft extends Command
  case object RotateRight extends Command
  case object MoveForward extends Command

  def fromString(str: String): Try[Command] = str match {
    case "L" => Success(RotateLeft)
    case "R" => Success(RotateRight)
    case "F" => Success(MoveForward)
    case _ => Failure(CommandValidationError(str))
  }

  def toString(command: Command): String = command match {
    case RotateLeft => "L"
    case RotateRight => "R"
    case MoveForward => "F"
  }
}


