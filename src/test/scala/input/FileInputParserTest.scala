package input

import model.Command.{MoveForward, RotateLeft, RotateRight}
import model.Orientation.{East, North}
import model._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.{Success, Try}

class FileInputParserTest extends AnyWordSpec with Matchers {

  "FileInputParser" should {

    "parse" should {
      "Read instructions from specified file correctly" in {
        val path = getClass.getResource("/validInstructions.txt").getPath
        val inputParser = new FileInputParser(path)
        val expected = List(
          RoverMission(ActiveMarsRover(Coordinate(2, 3), East)(Grid(4, 8)), List(RotateLeft, MoveForward, RotateRight, MoveForward, MoveForward)),
          RoverMission(ActiveMarsRover(Coordinate(0, 2), North)(Grid(4, 8)), List(MoveForward, MoveForward, RotateLeft, MoveForward, RotateRight, MoveForward, MoveForward))
        )
        inputParser.parse() shouldEqual Success(expected)
      }

      "Return Failure with MissionValidationError if file has invalid format" in {
        val path = getClass.getResource("/invalidInstructions.txt").getPath
        val inputParser = new FileInputParser(path)
        inputParser.parse().isFailure shouldBe true
      }

      "Return Failure with FileNotFOund exception if file does not exist" in {
        val path = "invalidPath"
        val inputParser = new FileInputParser(path)
        inputParser.parse().isFailure shouldBe true
      }
    }

  }
}
