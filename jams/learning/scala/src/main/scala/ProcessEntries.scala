
object ProcessEntries {
  def processEntries(entries: Seq[Entry]): Seq[Entry] = {
    entries.groupBy(_.number).map(
      t => {
        Entry(t._1, averageOfArrays(t._2))
      }
    ).toSeq
  }

  def averageOfArrays(entries: Seq[Entry]): Seq[Double] = {
    println(entries.size + " instances for " + entries(0).number)
    val average = entries.map(
      e => e.pixels
    ).reduce (
      (a, b) => sumSeqs(a, b)
    ).map(p => p / entries.size)
    average
  }

  def normalize(pixels: Seq[Double]): Seq[Double] = {
    val sum = pixels.sum
    pixels.map(_ / sum)
  }

  def sumSeqs(a: Seq[Double], b: Seq[Double]): Seq[Double] = {
    val reduced = a.zip(b).toList.map(
      a => a._1 + a._2
    )
    reduced
  }

  def testArraysAveraging() {
    println(ProcessEntries.averageOfArrays(Seq[Entry](
      Entry(1, Seq[Double](1,0,2)),
      Entry(1, Seq[Double](2,0,2)),
      Entry(1, Seq[Double](3,2,3))
    )))
  }
}
