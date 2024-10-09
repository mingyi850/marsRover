package model

import exception.MarsRoverException.OrientationValidationError
import model.Command.{MoveForward, RotateLeft, RotateRight}
import model.Orientation.{East, North, South, West}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.{Failure, Success}

class OrientationTest extends AnyWordSpec with Matchers {

  "Orientation" should {

    "Orientation.fromString" should {
      "Parse valid strings into Successful Orientation" in {
        Orientation.fromString("N").shouldEqual(Success(North))
        Orientation.fromString("S").shouldEqual(Success(South))
        Orientation.fromString("E").shouldEqual(Success(East))
        Orientation.fromString("W").shouldEqual(Success(West))
      }

      "Parse any invalid strings into Failures with Validation Exceptions" in {
        Orientation.fromString("P").shouldEqual(Failure(OrientationValidationError("P")))
        Orientation.fromString("a").shouldEqual(Failure(OrientationValidationError("a")))
        Orientation.fromString("1").shouldEqual(Failure(OrientationValidationError("1")))
      }
    }

    "Orientation.toString" should {
      "Return the correct string from a orientation" in {
        Orientation.toString(North).shouldEqual("N")
        Orientation.toString(South).shouldEqual("S")
        Orientation.toString(East).shouldEqual("E")
        Orientation.toString(West).shouldEqual("W")
      }
    }

    "Orientation.rotateLeft" should {
      "Return the next direction after a 90 degree left turn" in {
        Orientation.rotateLeft(East).shouldEqual(North)
        Orientation.rotateLeft(North).shouldEqual(West)
        Orientation.rotateLeft(West).shouldEqual(South)
        Orientation.rotateLeft(South).shouldEqual(East)
      }
    }

    "Orientation.rotateRight" should {
      "Return the next direction after a 90 degree right turn" in {
        Orientation.rotateRight(East).shouldEqual(South)
        Orientation.rotateRight(South).shouldEqual(West)
        Orientation.rotateRight(West).shouldEqual(North)
        Orientation.rotateRight(North).shouldEqual(East)
      }
    }

  }
}
