package model

import model.Command.{MoveForward, RotateLeft, RotateRight}
import model.Orientation.{East, North, South, West, rotateLeft, rotateRight}
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar.mock

class MarsRoverTest extends AnyWordSpec with Matchers {

  "MarsRover" should {
    "Return ActiveMarsRover if coordinate is on Grid" in {
      val position: Coordinate = mock[Coordinate]
      implicit val grid: Grid = mock[Grid]
      when(position.validateOnGrid(grid)).thenReturn(Some(Coordinate(2, 3)))
      MarsRover(position, East).shouldEqual(
        ActiveMarsRover(Coordinate(2, 3), East)
      )
    }

    "Return LostMarsRover if coordinate is not on Grid" in {
      val position: Coordinate = mock[Coordinate]
      implicit val grid: Grid = mock[Grid]
      when(position.validateOnGrid(grid)).thenReturn(None)
      MarsRover(position, East).shouldEqual(LostMarsRover(position, East))
    }

  }

  "ActiveMarsRover" should {
    "Return correct new state when performing rotation" in {
      implicit val grid: Grid = Grid(10, 10)
      val position = mock[Coordinate]
      ActiveMarsRover(position, East)
        .execute(RotateLeft)
        .shouldEqual(ActiveMarsRover(position, Orientation.rotateLeft(East)))
      ActiveMarsRover(position, West)
        .execute(RotateRight)
        .shouldEqual(ActiveMarsRover(position, Orientation.rotateRight(West)))
    }
    "Return correct new position when movingForward" in {
      implicit val grid: Grid = Grid(10, 10)
      val position = mock[Coordinate]
      val goodCoordinate = mock[Coordinate]
      when(position.forward(East)).thenReturn(goodCoordinate)
      when(goodCoordinate.validateOnGrid(grid)).thenReturn(Some(goodCoordinate))
      ActiveMarsRover(position, East)
        .execute(MoveForward)
        .shouldEqual(ActiveMarsRover(goodCoordinate, East))
    }
    "Become LostMarsRover with original position if leaving grid boundaries" in {
      implicit val grid: Grid = Grid(9, 9)
      val position = mock[Coordinate]
      val lostCoordinate = mock[Coordinate]
      val goodCoordinate = mock[Coordinate]
      when(lostCoordinate.validateOnGrid(grid)).thenReturn(None)
      when(goodCoordinate.validateOnGrid(grid)).thenReturn(Some(goodCoordinate))

      when(position.forward(North)).thenReturn(lostCoordinate)
      ActiveMarsRover(position, North)
        .execute(MoveForward)
        .shouldEqual(LostMarsRover(position, North))
    }
  }

  "LostMarsRover" should {
    "Always stay put" in {
      val position = Coordinate(3, 20)
      val orientation = South
      LostMarsRover(position, orientation)
        .execute(RotateLeft)
        .shouldEqual(LostMarsRover(position, orientation))
      LostMarsRover(position, orientation)
        .execute(RotateRight)
        .shouldEqual(LostMarsRover(position, orientation))
      LostMarsRover(position, orientation)
        .execute(MoveForward)
        .shouldEqual(LostMarsRover(position, orientation))
    }
  }

  "To State String" should {
    "Return the correct state string" in {
      val position = Coordinate(3, 20)
      val orientation = South
      implicit val grid = Grid(10, 10)
      LostMarsRover(position, orientation)
        .toStateString()
        .shouldEqual("(3, 20, S) LOST")
      ActiveMarsRover(position, orientation)
        .toStateString()
        .shouldEqual("(3, 20, S)")
    }
  }
}
