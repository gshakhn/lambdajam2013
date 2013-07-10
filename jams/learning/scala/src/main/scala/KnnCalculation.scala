import java.util.UUID
import scala.math._
import Ordering.Implicits._

object KnnCalculation {
  private def euclideanDistance(x: Entry, y: Entry) = {
    val a = x.pixels.zip(y.pixels)
    a.par.map { case (f, l) => (f - l) * (f - l) }.sum
  }

  def nearestNeighbors(entry: Entry, sampleData: Seq[Entry], k: Int) : Seq[Double] = {
    val sampleNumbers = sampleData.par.map {
      _.number
    }
    val sampleDistances = sampleData.par.map {
      euclideanDistance(entry, _)
    }
    val zipped = sampleNumbers.zip(sampleDistances).toList
    top(k, zipped).map {case (number, _) => number}
  }

  def top [E, F : Ordering] (n: Int, li: List [(E, F)]) : List[(E, F)] = {
    def updateSofar (sofar: List [(E, F)], el: (E, F)) : List [(E, F)] = {
      if (el._2 < sofar.head._2)
        (el :: sofar.tail).sortWith(_._2 > _._2)
      else sofar
    }

    (li.take(n).sortWith(_._2 > _._2) /: li.drop(n)) (updateSofar(_, _))
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
      ImageHelper.writeImage(entry, UUID.randomUUID().toString.substring(0,5)
        + "fail - " + result)
    }
    result
  }
}

case class Entry(number: Double, pixels: Seq[Double])