package input

import model.RoverMission

import scala.util.Try

/*
File format example
4 8
(2, 3, E) LFRFF
(0, 2, N) FFLFRFF
 */

class FileInputParser(filePath: String) extends InputParser {
  def parse(): Try[List[RoverMission]] = {
    for {
      fileLines <- Try(
        scala.io.Source.fromFile(filePath).getLines.toList.filter(_.nonEmpty)
      )
      (gridString, missionStrings) <- parseLines(fileLines)
      grid <- parseGrid(gridString)
      missionList <- parseMissions(missionStrings, grid)
    } yield missionList
  }
}
