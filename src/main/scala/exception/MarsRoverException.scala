package exception

object MarsRoverException {
  def invalidCommandString(command: String) = s"Invalid command: $command is not a valid command"
  def invalidOperationString(orientation: String) = s"Invalid orientation: $orientation is not a valid orientation"

  case class CommandValidationError(command: String) extends Exception(invalidCommandString(command))
  case class OrientationValidationError(orientation: String) extends Exception(invalidOperationString(orientation))
}

