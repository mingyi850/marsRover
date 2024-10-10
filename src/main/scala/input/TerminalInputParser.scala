package input

import model.{Grid, RoverMission}

import scala.util.{Failure, Success, Try}

class TerminalInputParser extends InputParser {
  def parse(): Try[List[RoverMission]] = {
    for {
      grid <- parseGridFromTerminal()
      missionList <- parseMissionsFromTerminal(List.empty[RoverMission], grid)
    }
    yield missionList
  }

  def parseGridFromTerminal(): Try[Grid] = {
    println("Enter the grid size as x y coordinates: format x y")
    val input = scala.io.StdIn.readLine()
    parseGrid(input)
  }

  def parseMissionsFromTerminal(accum: List[RoverMission], grid: Grid): Try[List[RoverMission]] = {
    println("Enter the next mission or hit enter to finish: format: (x y direction) instructions")
    val input = scala.io.StdIn.readLine()
    if (input == "") {
      Success(accum)
    } else {
      parseMissionString(input, grid) match {
        case Success(mission) => parseMissionsFromTerminal(accum :+ mission, grid)
        case Failure(e) => Failure(e)
      }
    }
  }
}
