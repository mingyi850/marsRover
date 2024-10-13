package model

import exception.MarsRoverException.CommandValidationError
import model.Command.{MoveForward, RotateLeft, RotateRight}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.{Failure, Success}

class CommandTest extends AnyWordSpec with Matchers {

  "Command" should {

    "Command.fromString" should {
      "Parse valid strings into Success Commands" in {
        Command.fromString("L").shouldEqual(Success(RotateLeft))
        Command.fromString("R").shouldEqual(Success(RotateRight))
        Command.fromString("F").shouldEqual(Success(MoveForward))
      }

      "Parse any invalid strings into Failures with Validation Exceptions" in {
        Command
          .fromString("A")
          .shouldEqual(Failure(CommandValidationError("A")))
        Command
          .fromString("LR")
          .shouldEqual(Failure(CommandValidationError("LR")))
        Command
          .fromString("1")
          .shouldEqual(Failure(CommandValidationError("1")))
      }
    }

    "Command.toString" should {
      "Return the correct string from a command" in {
        Command.toString(RotateLeft).shouldEqual("L")
        Command.toString(RotateRight).shouldEqual("R")
        Command.toString(MoveForward).shouldEqual("F")
      }
    }

  }
}
