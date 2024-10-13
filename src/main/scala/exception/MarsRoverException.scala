package exception

object MarsRoverException {
  def invalidCommandString(command: String) =
    s"Invalid command: $command is not a valid command"
  def invalidOperationString(orientation: String) =
    s"Invalid orientation: $orientation is not a valid orientation"
  def invalidMissionString(mission: String) =
    s"Invalid mission: $mission is not a valid mission"

  def invalidCoordinateString(x: String, y: String) =
    s"Invalid coordinate: $x, $y is not a valid coordinate"

  case class CommandValidationError(command: String)
      extends Exception(invalidCommandString(command))
  case class OrientationValidationError(orientation: String)
      extends Exception(invalidOperationString(orientation))
  case class GridValidationError(message: String) extends Exception(message)

  case class MissionValidationError(mission: String)
      extends Exception(invalidMissionString(mission))
  case class InputStructureValidationError()
      extends Exception("Invalid input structure")
}
