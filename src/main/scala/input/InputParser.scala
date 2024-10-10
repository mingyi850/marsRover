package input

import exception.MarsRoverException.{GridValidationError, InputStructureValidationError, MissionValidationError}
import model._

import scala.util.{Failure, Success, Try}


abstract class InputParser {

  def parse(): Try[List[RoverMission]]

  def parseLines(lines: List[String]): Try[(String, List[String])] = {
    lines match {
      case Nil => Failure(InputStructureValidationError())
      case head :: tail => Success((head, tail))
    }
  }

  def parseGrid(str: String): Try[Grid] = {
    val regex = """(\d+) (\d+)""".r
    regex.unapplySeq(str) match {
      case Some(Seq(width, height)) => {
        (width.toIntOption, height.toIntOption) match {
          case (Some(w), Some(h)) => Grid.create(w, h)
          case _ => Failure(GridValidationError(f"Invalid input: $width $height. Grid dimensions should be integers"))
        }
      }
      case _ => Failure(GridValidationError(f"Invalid input: $str. Grid dimensions should be integers"))
    }
  }

  def parseMissions(lines: List[String], grid: Grid): Try[List[RoverMission]] = {
    toTryList(lines.map(parseMissionString(_, grid)))
  }

  def parseMissionString(str: String, grid: Grid): Try[RoverMission] = {
    val regex = """\((\d+), (\d+), ([NSEW])\) ([LFR]+)""".r
    regex.unapplySeq(str) match {
      case Some(Seq(xStr, yStr, orientationStr, commandStr)) => {
        for {
          coordinate <- parseCoordinate(xStr, yStr)
          orientation <- Orientation.fromString(orientationStr)
          commands <- parseCommandList(commandStr)
        } yield {
          RoverMission(MarsRover(coordinate, orientation)(grid), commands)
        }
      }
      case _ => Failure(MissionValidationError(str))
    }
  }

  private def parseCoordinate(x: String, y: String): Try[Coordinate] = {
    (x.toIntOption, y.toIntOption) match {
      case (Some(x), Some(y)) => Success(Coordinate(x, y))
      case _ => Failure(MissionValidationError(s"($x, $y)"))
    }
  }

  private def parseCommandList(str: String): Try[List[Command]] = {
    toTryList(
      str
      .toList
      .map{ x => Command.fromString(x.toString) }
    )

  }

  private def toTryList[A](list: List[Try[A]]): Try[List[A]] = {
    list.foldRight(Try(List.empty[A])) {
      case (Success(x), Success(xs)) => Success(x :: xs)
      case (Failure(e), _) => Failure(e)
      case (_, Failure(e)) => Failure(e)
    }
  }

}

object InputParser {
  def apply(filePath: Option[String]): InputParser = filePath match {
    case Some(path) => new FileInputParser(path)
    case None => new TerminalInputParser()
  }
}
