package model

import model.Command.{MoveForward, RotateLeft, RotateRight}
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{times, verify, when}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar.mock

import scala.util.{Failure, Success}

class RoverMissionTest extends AnyWordSpec with Matchers {

  "RoverMission" should {

    "RoverMission.executeMission" should {
      "Complete all commands if rover stays active" in {
        val rover = mock[ActiveMarsRover]
        when(rover.execute(any[Command])).thenReturn(rover)
        RoverMission(
          rover,
          List(RotateLeft, RotateRight, MoveForward)
        ).executeMission.shouldEqual(rover)
        verify(rover, times(3)).execute(any[Command])
      }

      "Execute commands and halt if rover becomes Lost" in {
        val rover = mock[ActiveMarsRover]
        val lostRover = mock[LostMarsRover]
        when(rover.execute(any[Command])).thenReturn(rover)
        when(rover.execute(RotateRight)).thenReturn(lostRover)
        RoverMission(
          rover,
          List(RotateLeft, RotateRight, MoveForward)
        ).executeMission.shouldEqual(lostRover)
        verify(rover, times(2)).execute(any[Command])
        verify(lostRover, times(0)).execute(any[Command])
      }
    }
  }
}
