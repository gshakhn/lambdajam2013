import dispatch._
import Defaults._
import java.io.{FileOutputStream, PrintWriter}
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonDSL._

object Main {
  def main(args: Array[String]) = {
    val cellSize = 15
    val maze: List[List[Int]] = MazeGenerator.generateMaze(cellSize, cellSize)
    println(maze)

    val json = mazeToJson(maze)

    renderMazeAndSavePng(json, cellSize)
    //Thank you, dispatch library!!
    System.exit(0)
  }


  def renderMazeAndSavePng(json: String, cellSize: Int) {
    val request = url("http://mazesjam.apphb.com/mazes/render") << Map("maze" -> json, "cellSize" -> cellSize.toString)
    request.setMethod("POST")
    val response = Http(request OK as.Bytes)
    val actualResponse = response()

    val outputFile: String = "maze.png"
    val outputStream = new FileOutputStream(outputFile)
    outputStream.write(actualResponse)
    outputStream.close()
  }

  def mazeToJson(maze: List[List[Int]]): String = {
    "[" +
      maze.zipWithIndex.foldLeft("") {
        case (acc, rowWithIndex) => {
          val optionalComma = rowWithIndex._2 match {
            case x if x != maze.size - 1 => ","
            case _ => ""
          }
          acc + compact(render(rowWithIndex._1)) + optionalComma
        }
      } + "]"
  }
}
