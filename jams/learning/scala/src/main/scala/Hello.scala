import main.parsing.CsvReader

object Main {
  def main(args: Array[String]) = {
    println("Hi!")
    val entries = new CsvReader().readFile("../digitssample.csv")
  }
}
