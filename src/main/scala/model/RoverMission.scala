package model

import scala.annotation.tailrec

@tailrec
case class RoverMission(rover: MarsRover, commandList: List[Command]) {
  def executeMission: MarsRover = {
    rover match {
      case rover : LostMarsRover => rover
      case rover: ActiveMarsRover => commandList match {
        case Nil => rover
        case head :: tail => RoverMission(rover.execute(head), tail).executeMission
      }
    }
  }
}