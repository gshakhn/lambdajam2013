
object Main {
  def main(args: Array[String]) = {
    println("Hi!")
    val training = new CsvReader().readFile("../digitssample.csv")
    val testing = new CsvReader().readFile("../digitscheck.csv")
  }
}
