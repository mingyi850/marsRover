package model

import model.Command.{MoveForward, RotateLeft, RotateRight}
import model.Orientation.{East, North, South, West}
import org.mockito.Mockito.{mock, when}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class MarsRoverTest extends AnyWordSpec with Matchers {

  "MarsRover" should {
    "Return ActiveMarsRover if coordinate is on Grid" in {
      val position: Coordinate = mock(classOf[Coordinate])
      implicit val grid: Grid = mock(classOf[Grid])
      when(position.validateOnGrid(grid)).thenReturn(Some(Coordinate(2, 3)))
      MarsRover(position, East).shouldEqual(ActiveMarsRover(Coordinate(2, 3), East))
    }

    "Return LostMarsRover if coordinate is not on Grid" in {
      val position: Coordinate = mock(classOf[Coordinate])
      implicit val grid: Grid = mock(classOf[Grid])
      when(position.validateOnGrid(grid)).thenReturn(None)
      MarsRover(position, East).shouldEqual(LostMarsRover(position, East))
    }

  }

  "ActiveMarsRover" should {
    "Return correct new state when performing rotation" in {
      implicit val grid: Grid = Grid(10, 10)
      val position = Coordinate(2, 3)
      ActiveMarsRover(position, East).execute(RotateLeft).shouldEqual(ActiveMarsRover(position, North))
      ActiveMarsRover(position, South).execute(RotateRight).shouldEqual(ActiveMarsRover(position, West))
    }
    "Return correct new position when movingForward" in {
      implicit val grid: Grid = Grid(10, 10)
      val position = Coordinate(2, 3)
      ActiveMarsRover(position, East).execute(MoveForward).shouldEqual(ActiveMarsRover(Coordinate(3, 3), East))
      ActiveMarsRover(position, South).execute(MoveForward).shouldEqual(ActiveMarsRover(Coordinate(2, 2), South))
    }
    "Become LostMarsRover if leaving grid boundaries" in {
      implicit val grid: Grid = Grid(9, 9)
      ActiveMarsRover(Coordinate(9, 9), North).execute(MoveForward).shouldEqual(LostMarsRover(Coordinate(9, 9), North))
      ActiveMarsRover(Coordinate(0, 1), West).execute(MoveForward).shouldEqual(LostMarsRover(Coordinate(0, 1), West))
    }
  }

  "LostMarsRover" should {
    "Always stay put" in {
      val position = Coordinate(3, 20)
      val orientation = South
      LostMarsRover(position, orientation).execute(RotateLeft).shouldEqual(LostMarsRover(position, orientation))
      LostMarsRover(position, orientation).execute(RotateRight).shouldEqual(LostMarsRover(position, orientation))
      LostMarsRover(position, orientation).execute(MoveForward).shouldEqual(LostMarsRover(position, orientation))
    }
  }
}
