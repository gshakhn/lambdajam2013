package main.parsing

import collection.JavaConversions._

case class Entry(pixels: Seq[Int], label: Int)

class CsvReader {
  def readFile(filename: String): Seq[Entry] = {
    scala.io.Source.fromFile(filename).getLines().drop(1).map {
      line => {
        val values = line.split(",").map(
          v => v.toInt
        )
        Entry(values.tail, values.head)
      }
    }.toSeq
  }

}
