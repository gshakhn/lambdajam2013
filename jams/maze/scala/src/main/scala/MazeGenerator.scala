import util.Random

object MazeGenerator {
  val defaultCell = 15
  val NORTH = 1
  val SOUTH = 2
  val EAST = 4
  val WEST = 8

  object Orientation extends Enumeration {
    type Orientation = Value
    val Vertical, Horizontal = Value
  }

  import Orientation._

  def generateMazeInternal(maze: List[List[Int]]): List[List[Int]] = {
    val height = maze.size
    val width = maze(0).size
    (height, width) match {
      case (x,y) if x < 2 && y < 2 => maze
      case _ => {
        lazy val horizontalOrVertical = Random.nextInt(height + width)
        val orientation =
          if(height == 1) Vertical
          else if(width == 1) Horizontal
          else {
            horizontalOrVertical match {
              case x if x < height => Horizontal
              case _ => Vertical
            }
          }
        orientation match {
          case Horizontal => {
            println("Splitting Horizontally: " + maze)
            val (topHalf, bottomHalf) = splitHorizontally(height, width, maze)
            topHalf ++ bottomHalf
          }
          case Vertical => {
            println("Splitting Vertically: " + maze)
            val y = Random.nextInt(width - 1) + 1
            println("column " + y)
            val (westHalf, eastHalf) = splitVertically(height, width, maze)
            westHalf.zip(eastHalf).map{
              case (l1, l2) => l1 ++ l2
            }
          }
        }
      }
    }
  }

  def splitHorizontally(height: Int, width: Int, maze: List[List[Int]]): (List[List[Int]], List[List[Int]]) = {
    val x = Random.nextInt(height - 1) + 1
    println("row " + x)
    val opening = Random.nextInt(width)
    val updatedMaze = maze.zipWithIndex.map {
      case (row, rowIndex) =>
        rowIndex match {
          case r if r == x - 1 => drawHorizontalWall(row, opening, SOUTH)
          case r if r == x => drawHorizontalWall(row, opening, NORTH)
          case _ => row
        }
    }
    val topHalf = generateMazeInternal(updatedMaze.slice(0, x))
    val bottomHalf = generateMazeInternal(updatedMaze.slice(x, height))
    (topHalf, bottomHalf)
  }

  def drawVerticalWall(row: List[Int], column: Int) = {
    row.zipWithIndex.map {
      case (n, columnIndex) => {
        columnIndex match {
          case c if c == column - 1 => n - EAST
          case c if c == column => n - WEST
          case _ => n
        }
      }
    }
  }

  def splitVertically(height: Int, width: Int, maze: List[List[Int]]): (List[List[Int]], List[List[Int]]) = {
    val x = Random.nextInt(width - 1) + 1
    val opening = Random.nextInt(height)
    val updatedMaze = maze.zipWithIndex.map {
      case (row, rowIndex) =>
        rowIndex match {
          case r if r != opening => drawVerticalWall(row, x)
          case _ => row
        }
    }
    val westHalf = generateMazeInternal( updatedMaze.map {
      _.slice(0, x)
    })
    val eastHalf = generateMazeInternal( updatedMaze.map {
      _.slice(x, width)
    })
    (westHalf, eastHalf)
  }


  def drawHorizontalWall(row: List[Int], opening: Int, size: Int): List[Int] = {
    row.zipWithIndex.map {
      case (n, columnIndex) => {
        if (columnIndex == opening) n else n - size
      }
    }
  }

  def generateMaze(height: Int, width: Int): List[List[Int]] = {
    println("")
    generateMazeInternal(List.fill(height, width)(defaultCell))
  }
}
