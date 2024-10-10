package model

import exception.MarsRoverException.GridValidationError

import scala.util.{Failure, Success, Try}

case class Grid(width: Int, height: Int)

object Grid {
  def create(width: Int, height: Int): Try[Grid] = {
    if (width < 0 || height < 0) {
      Failure(GridValidationError("Grid dimensions must be positive"))
    }
    Success[Grid](Grid(width, height))
  }
}
