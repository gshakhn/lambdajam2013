import java.awt.image.{WritableRaster, BufferedImage}
import java.io.{FileOutputStream, ByteArrayInputStream}
import javax.imageio.ImageIO

object Main {
  def main(args: Array[String]) = {
    println("Hi!")
    val training = new CsvReader().readFile("../digitssample.csv")
    val testing = new CsvReader().readFile("../digitscheck.csv")
    val processedTraining = ProcessEntries.processEntries(training)

    processedTraining.foreach(
      e => ImageHelper.writeImage(e, "processed")
    )
    //println("Processed: " + processedTraining)

    val correct = testing.map(
      e => {
        val candidates = KnnCalculation.nearestNeighbors(e, processedTraining, 5)
        KnnCalculation.nearestNeighbor(e, training.filter(
          e => candidates.contains(e.number)
        )) == e.number
        }
      ).toList.count(
      e => e == true
    )
    println(correct + " Correctly classified instances")
  }
}
