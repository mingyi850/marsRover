package model

import model.Orientation.{North, South, East, West}

case class Coordinate(x: Int, y: Int) {
  def forward(orientation: Orientation): Coordinate = orientation match {
    case North => Coordinate(x, y + 1)
    case South => Coordinate(x, y - 1)
    case East => Coordinate(x + 1, y)
    case West => Coordinate(x - 1, y)
  }

  def validateOnGrid(grid: Grid): Option[Coordinate] = {
    if (x >= 0 && x <= grid.width && y >= 0 && y <= grid.height) {
      Some(Coordinate(x, y))
    } else None
  }
}
