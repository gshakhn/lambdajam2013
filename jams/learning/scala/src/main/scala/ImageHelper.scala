import java.awt.image.{WritableRaster, BufferedImage}
import java.io.FileOutputStream
import javax.imageio.ImageIO

object ImageHelper {
  def writeImage(entry: Entry, filename: String) {
    val image = new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_INDEXED)
    val bytes = entry.pixels.grouped(28).map(_.toArray)
    val raster: WritableRaster = image.getRaster
    bytes.zipWithIndex.map(
      row => row._1.zipWithIndex.map(
        pixel => {
          raster.setPixel(pixel._2, row._2, Array[Int](pixel._1.toInt))
        }
      ).toList
    ).toList
    image.setData(raster)
    val stream: FileOutputStream = new FileOutputStream(filename + " - " + entry.number + ".gif")
    ImageIO.write(image, "gif", stream)
    stream.close()
  }
}
