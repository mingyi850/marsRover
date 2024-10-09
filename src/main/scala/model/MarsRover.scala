package model

import model.Command.{MoveForward, RotateLeft, RotateRight}


sealed abstract class MarsRover(position: Coordinate, orientation: Orientation) {
  def execute(command: Command): MarsRover
}

case class ActiveMarsRover(position: Coordinate, orientation: Orientation)(implicit val grid: Grid) extends MarsRover(position, orientation) {
  def execute(command: Command): MarsRover = command match {
    case RotateLeft => MarsRover(position, Orientation.rotateLeft(orientation))
    case RotateRight => MarsRover(position, Orientation.rotateRight(orientation))
    case MoveForward => MarsRover(position.forward(orientation), orientation)
  }
}

case class LostMarsRover(position: Coordinate, orientation: Orientation) extends MarsRover(position, orientation) {
  def execute(command: Command): MarsRover = LostMarsRover(position, orientation)
}


object MarsRover {
  def apply(position: Coordinate, orientation: Orientation)(implicit grid: Grid): MarsRover = {
    position.validateOnGrid(grid) match {
      case None => LostMarsRover(position, orientation)
      case Some(coordinate) => ActiveMarsRover(coordinate, orientation)
    }
  }
}

