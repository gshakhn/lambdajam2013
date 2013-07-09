import collection.JavaConversions._

class CsvReader {
  def readFile(filename: String): Seq[Entry] = {
    scala.io.Source.fromFile(filename).getLines().drop(1).map {
      line => {
        val values = line.split(",").map(
          v => v.toInt
        )
        Entry(values.head, values.tail)
      }
    }.toSeq
  }
}
