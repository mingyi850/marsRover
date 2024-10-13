import input.InputParser

import scala.util.{Failure, Success}

object Main {
  def main(args: Array[String]): Unit = {
    println("Starting Mars Rover!")
    val filePath: Option[String] = args.toList.headOption
    val app = new MarsRoverApp(filePath)
    app.run() match {
      case Success(results) => results.foreach(println)
      case Failure(e)       => throw e
    }
  }
}
