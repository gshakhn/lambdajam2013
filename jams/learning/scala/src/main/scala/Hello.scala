
object Main {
  def main(args: Array[String]) = {
    println("Hi!")
    val training = new CsvReader().readFile("../digitssample.csv")
    val testing = new CsvReader().readFile("../digitscheck.csv")
    val correct = testing.map(
      e => KnnCalculation.nearestNeighbor(e, training) == e.number
    ).toList.count(
      e => e == true
    )
    println(correct + " Correctly classified instances")
  }
}
