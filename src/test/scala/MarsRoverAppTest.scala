import model.Command.{MoveForward, RotateLeft, RotateRight}
import model.Orientation.{East, North}
import model._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Success

class MarsRoverAppTest extends AnyWordSpec with Matchers {

  "MarsRoverApp" should {

    "run" should {
      "Read instructions from specified file correctly and return correctly formatted results" in {
        val path = getClass.getResource("/validInstructions.txt").getPath
        val app = new MarsRoverApp(Some(path))
        val expected = List(
          "(4, 4, E)",
          "(0, 4, W) LOST"
        )
        app.run() shouldEqual Success(expected)
      }

      "Return Failure with MissionValidationError if file has invalid format" in {
        val path = getClass.getResource("/invalidInstructions.txt").getPath
        val app = new MarsRoverApp(Some(path))
        app.run().isFailure shouldBe true
      }

      "Read instructions from terminal and return correctly formatted results" in {
        val app = new MarsRoverApp(None)
        val expected = List(
          "(4, 4, E)",
          "(0, 4, W) LOST"
        )
        val terminalInput =
          getClass.getResourceAsStream("/validInstructions.txt")
        Console.withIn(terminalInput) {
          app.run() shouldEqual Success(expected)
        }
      }

      "Read instructions from terminal and return failure on broken input" in {
        val app = new MarsRoverApp(None)
        val terminalInput =
          getClass.getResourceAsStream("/invalidInstructions.txt")
        Console.withIn(terminalInput) {
          app.run().isFailure shouldBe true
        }
      }
    }

  }
}
