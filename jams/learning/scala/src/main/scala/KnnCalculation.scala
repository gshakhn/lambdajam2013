import java.util.UUID
import scala.math._

object KnnCalculation {
  private def euclideanDistance(x: Entry, y: Entry) = {
    val a = x.pixels.zip(y.pixels)
    a.par.map { case (f, l) => pow(f - l, 2) }.sum
  }

  private def nearestNeighbors(entry: Entry, sampleData: Seq[Entry], k: Int) : Seq[Double] = {
    val sampleNumbers = sampleData.par.map {
      _.number
    }
    val sampleDistances = sampleData.par.map {
      euclideanDistance(entry, _)
    }
    sampleNumbers.zip(sampleDistances).toList.sortBy{case (number, distance) => distance}.take(k).map {case (number, _) => number}
  }

  private def commonestNeighbor(neighbors: Seq[Double]) : Double = {
    neighbors.groupBy{n => n}.toList.map{ case (n, nList) => (n, nList.size)}.maxBy{case(n, occurrences) => occurrences}._1
  }

  def nearestNeighbor(entry: Entry, sampleData: Seq[Entry]) : Double = {
    val result = commonestNeighbor(nearestNeighbors(entry, sampleData, 1))
    val success: Boolean = result == entry.number
    if(!success) {
      println("processing " + entry.number + " and we got " + result + " "
        + entry.number + " " + success)
      println("actual: " + euclideanDistance(entry, sampleData.find(_.number == entry.number).get))
      println("predicted: " + euclideanDistance(entry, sampleData.find(_.number == result).get))
      ImageHelper.writeImage(entry, UUID.randomUUID().toString.substring(0,5)
        + "fail - " + result)
    }
    result
  }
}

case class Entry(number: Double, pixels: Seq[Double])