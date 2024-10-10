package input

import model.Command.{MoveForward, RotateLeft, RotateRight}
import model.Orientation.{East, North}
import model._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Success

class TerminalInputParserTest extends AnyWordSpec with Matchers {

  "TerminalInputParser" should {

    "parse" should {
      "Read instructions from terminal correctly" in {
        val terminalInput = getClass.getResourceAsStream("/validInstructions.txt")
        val inputParser = new TerminalInputParser
        Console.withIn(terminalInput) {
          val expected = List(
            RoverMission(ActiveMarsRover(Coordinate(2, 3), East)(Grid(4, 8)), List(RotateLeft, MoveForward, RotateRight, MoveForward, MoveForward)),
            RoverMission(ActiveMarsRover(Coordinate(0, 2), North)(Grid(4, 8)), List(MoveForward, MoveForward, RotateLeft, MoveForward, RotateRight, MoveForward, MoveForward))
          )
          inputParser.parse() shouldEqual Success(expected)
        }
      }

      "Return Failure with MissionValidationError if input has invalid format" in {
        val terminalInput = getClass.getResourceAsStream("/invalidInstructions.txt")
        val inputParser = new TerminalInputParser
        Console.withIn(terminalInput) {
          inputParser.parse().isFailure shouldBe true
        }
      }
    }

  }
}
