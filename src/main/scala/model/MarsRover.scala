package model

import model.Command.{MoveForward, RotateLeft, RotateRight}

sealed abstract class MarsRover(
    position: Coordinate,
    orientation: Orientation
) {
  def execute(command: Command): MarsRover
  def toStateString(): String
}

case class ActiveMarsRover(position: Coordinate, orientation: Orientation)(
    implicit val grid: Grid
) extends MarsRover(position, orientation) {
  def execute(command: Command): MarsRover = command match {
    case RotateLeft =>
      ActiveMarsRover(position, Orientation.rotateLeft(orientation))
    case RotateRight =>
      ActiveMarsRover(position, Orientation.rotateRight(orientation))
    case MoveForward =>
      position.forward(orientation).validateOnGrid(grid) match {
        case Some(newCoordinate) => ActiveMarsRover(newCoordinate, orientation)
        case None                => LostMarsRover(position, orientation)
      }
  }

  def toStateString(): String =
    s"(${position.x}, ${position.y}, ${Orientation.toString(orientation)})"
}

case class LostMarsRover(position: Coordinate, orientation: Orientation)
    extends MarsRover(position, orientation) {
  def execute(command: Command): MarsRover =
    LostMarsRover(position, orientation)

  def toStateString(): String =
    s"(${position.x}, ${position.y}, ${Orientation.toString(orientation)}) LOST"
}

object MarsRover {
  def apply(position: Coordinate, orientation: Orientation)(implicit
      grid: Grid
  ): MarsRover = {
    position.validateOnGrid(grid) match {
      case None             => LostMarsRover(position, orientation)
      case Some(coordinate) => ActiveMarsRover(coordinate, orientation)
    }
  }
}
