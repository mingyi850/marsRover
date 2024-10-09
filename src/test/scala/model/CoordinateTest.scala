package model

import exception.MarsRoverException.CommandValidationError
import model.Command.{MoveForward, RotateLeft, RotateRight}
import model.Orientation.{East, North, South, West}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.{Failure, Success}

class CoordinateTest extends AnyWordSpec with Matchers {

  "Coordinate" should {
    "Return correct new coordinates when moving forward" in {
      Coordinate(1, 2).forward(East).shouldEqual(Coordinate(2,2))
      Coordinate(3, 10).forward(West).shouldEqual(Coordinate(2, 10))
      Coordinate(-20, 33).forward(North).shouldEqual(Coordinate(-20, 34))
      Coordinate(-222, -323).forward(South).shouldEqual(Coordinate(-222, -324))
    }

    "Return some coordinate if coordinate is on grid" in {
      val grid1 = Grid(1, 1)
      Coordinate(0, 0).validateOnGrid(grid1).shouldEqual(Some(Coordinate(0, 0)))
      Coordinate(1,0).validateOnGrid(grid1).shouldEqual(None)
      Coordinate(1,1).validateOnGrid(grid1).shouldEqual(None)
      Coordinate(0,1).validateOnGrid(grid1).shouldEqual(None)

      val grid2 = Grid(3, 10)
      Coordinate(2, 9).validateOnGrid(grid2).shouldEqual(Some(Coordinate(2, 9)))
      Coordinate(1, 5).validateOnGrid(grid2).shouldEqual(Some(Coordinate(1, 5)))
      Coordinate(5, 5).validateOnGrid(grid2).shouldEqual(None)
      Coordinate(4, 10).validateOnGrid(grid2).shouldEqual(None)
    }


  }
}
