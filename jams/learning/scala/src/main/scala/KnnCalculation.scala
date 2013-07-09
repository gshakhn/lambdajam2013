/**
 * Created with IntelliJ IDEA.
 * User: gshakhnazaryan
 * Date: 7/9/13
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */

import scala.math._

object KnnCalculation {
  private def euclideanDistance(x: Entry, y: Entry) = {
    val a = x.pixels.zip(y.pixels)
    a.par.map { case (f, l) => pow(f - l, 2) }.sum
  }

  private def nearestNeighbors(entry: Entry, sampleData: Seq[Entry], k: Int) : Seq[Int] = {
    val sampleNumbers = sampleData.par.map {
      _.number
    }
    val sampleDistances = sampleData.par.map {
      euclideanDistance(entry, _)
    }
    sampleNumbers.zip(sampleDistances).toList.sortBy{case (number, distance) => distance}.take(k).map {case (number, _) => number}
  }

  private def commonestNeighbor(neighbors: Seq[Int]) : Int = {
    neighbors.groupBy{n => n}.toList.map{ case (n, nList) => (n, nList.size)}.maxBy{case(n, occurrences) => occurrences}._1
  }

  def nearestNeighbor(entry: Entry, sampleData: Seq[Entry]) : Int = {
    val result = commonestNeighbor(nearestNeighbors(entry, sampleData, 5))
    println("processing " + entry.number + " and we got " + (result == entry.number))
    result
  }
}

case class Entry(number: Int, pixels: Seq[Int])