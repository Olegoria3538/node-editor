package GUISamples.features.nodes.utils

import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.PixelReader
import javafx.scene.image.WritableImage
import javafx.scene.image.WritablePixelFormat
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.imgcodecs.Imgcodecs
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.nio.ByteBuffer
import javax.imageio.ImageIO


fun imageToMat(image: WritableImage): Mat? {
    val width = image.getWidth().toInt()
    val height = image.getHeight().toInt()
    val buffer = ByteArray(width * height * 4)
    val reader: PixelReader = image.getPixelReader()
    val format: WritablePixelFormat<ByteBuffer> = WritablePixelFormat.getByteBgraInstance()
    reader.getPixels(0, 0, width, height, format, buffer, 0, width * 4)
    val mat = Mat(height, width, CvType.CV_8UC4)
    mat.put(0, 0, buffer)
    return mat
}

fun Mat2WritableImage(mat: Mat?): WritableImage? {
    val matOfByte = MatOfByte()
    Imgcodecs.imencode(".jpg", mat, matOfByte)
    val byteArray = matOfByte.toArray()
    val `in`: InputStream = ByteArrayInputStream(byteArray)
    val bufImage: BufferedImage = ImageIO.read(`in`)
    return SwingFXUtils.toFXImage(bufImage, null)
}