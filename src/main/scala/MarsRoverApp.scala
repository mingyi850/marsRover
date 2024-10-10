import input.InputParser

import scala.util.Try

class MarsRoverApp(filePath: Option[String]) {
  val parser = InputParser(filePath)

  def run(): Try[List[String]] = {
    parser.parse()
      .map(
        missionList => missionList
          .map(_.executeMission.toStateString())
      )
  }

}
