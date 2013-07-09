/**
 * Created with IntelliJ IDEA.
 * User: gshakhnazaryan
 * Date: 7/9/13
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */

import scala.math._

object KnnCalculation {
  def euclideanDistance(x: Entry, y: Entry) = {
    val a = x.pixels.zip(y.pixels)
    a.map { case (f, l) => pow(f - l, 2) }.sum
  }

  def nearestNeighbors(entry: Entry, sampleData: Seq[Entry], k: Int) : Seq[Int] = {
    val sampleNumbers: Seq[Int] = sampleData.map {
      _.number
    }
    val sampleDistances: Seq[Double] = sampleData.map {
      euclideanDistance(entry, _)
    }
    sampleNumbers.zip(sampleDistances).sortBy{case (number, distance) => distance}.take(k).map {case (number, _) => number}
  }

  def commonestNeighbor(neighbors: Seq[Int]) : Int = {
    neighbors.groupBy{n => n}.toList.map{ case (n, nList) => (n, nList.size)}.maxBy{case(n, occurrences) => occurrences}._1
  }
}

case class Entry(number: Int, pixels: Seq[Int])