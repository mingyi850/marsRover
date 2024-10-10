package input

import model.Command.{MoveForward, RotateLeft, RotateRight}
import model.{Coordinate, Grid, MarsRover, Orientation, RoverMission}
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{times, verify, when}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar.mock

import scala.util.{Success, Try}

class InputParserTest extends AnyWordSpec with Matchers {

  case class TestInputParser() extends InputParser {
    override def parse(): Try[List[RoverMission]] = Success(List.empty[RoverMission])
  }

  "InputParser" should {

    "InputParser.parseLines" should {
      "Split input into head and tail" in {
        val inputParser = TestInputParser()
        inputParser.parseLines(List("abc", "def", "cde")).shouldEqual(Success("abc", List("def", "cde")))
      }

      "Return Failure if input is empty" in {
        val inputParser = TestInputParser()
        inputParser.parseLines(List.empty[String]).isFailure.shouldEqual(true)
      }

    }

    "InputParser.parseGrid" should {
      "Return Grid if input is valid with 2 coordinates" in {
        val inputParser = TestInputParser()
        inputParser.parseGrid("4 8").shouldEqual(Success(Grid(4, 8)))
        inputParser.parseGrid("14 33348").shouldEqual(Success(Grid(14, 33348)))
      }

      "Return Failure if input is invalid" in {
        val inputParser = TestInputParser()
        inputParser.parseGrid("4 8 9").isFailure.shouldEqual(true)
        inputParser.parseGrid("4").isFailure.shouldEqual(true)
        inputParser.parseGrid("a d").isFailure.shouldEqual(true)
      }
    }

    "InputParser.parseMissions" should {
      "Return List of RoverMissions if input is valid" in {
        val inputParser = TestInputParser()
        inputParser.parseMissions(List("(2, 3, E) LFRFF", "(0, 2, N) FFLFRFF"), Grid(4, 8)).shouldEqual(Success(List(RoverMission(MarsRover(Coordinate(2, 3), Orientation.East)(Grid(4, 8)), List(RotateLeft, MoveForward, RotateRight, MoveForward, MoveForward)), RoverMission(MarsRover(Coordinate(0, 2), Orientation.North)(Grid(4, 8)), List(MoveForward, MoveForward, RotateLeft, MoveForward, RotateRight, MoveForward, MoveForward)))))
      }

      "Return Failure if any input is invalid" in {
        val inputParser = TestInputParser()
        inputParser.parseMissions(List("(2, 3, E) LFRFF", "(0, 2, N) FFLFRFFG"), Grid(4, 8)).isFailure.shouldEqual(true)
        inputParser.parseMissions(List("(2, 3, A) LFRFF", "(0, 2, N) FFLFRFF"), Grid(4, 8)).isFailure.shouldEqual(true)
      }

    }
    "InputParser.parseMissionString" should {
      "Return RoverMission if input is valid" in {
        val inputParser = TestInputParser()
        inputParser.parseMissionString("(2, 3, E) LFRFF", Grid(4, 8)).shouldEqual(Success(RoverMission(MarsRover(Coordinate(2, 3), Orientation.East)(Grid(4, 8)), List(RotateLeft, MoveForward, RotateRight, MoveForward, MoveForward))))
      }

      "Return Failure if orientation is invalid" in {
        val inputParser = TestInputParser()
        inputParser.parseMissionString("(2, 3, D) LFRFF", Grid(4, 8)).isFailure.shouldEqual(true)
        inputParser.parseMissionString("(2, 3, 3) LFRFF", Grid(4, 8)).isFailure.shouldEqual(true)
      }

      "Return Failure if coordinates are malformed" in {
        val inputParser = TestInputParser()
        inputParser.parseMissionString("(2, 3, E LFRFF", Grid(4, 8)).isFailure.shouldEqual(true)
        inputParser.parseMissionString("a, 3, E LFRFF", Grid(4, 8)).isFailure.shouldEqual(true)
      }

      "Return Failure if commands are invalid" in {
        val inputParser = TestInputParser()
        inputParser.parseMissionString("(2, 3, E) LFRFFG", Grid(4, 8)).isFailure.shouldEqual(true)
        inputParser.parseMissionString("(2, 3, E) LFRFF  ", Grid(4, 8)).isFailure.shouldEqual(true)
        inputParser.parseMissionString("(2, 3, E) LFRFF  F", Grid(4, 8)).isFailure.shouldEqual(true)
      }
    }
  }
}
