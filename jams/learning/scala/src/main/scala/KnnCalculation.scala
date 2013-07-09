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
}

case class Entry(number: Int, pixels: Seq[Int])