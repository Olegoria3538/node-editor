package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.CoreNode
import GUISamples.features.nodes.`@core`.InputMetric
import GUISamples.features.nodes.`@core`.types
import GUISamples.features.nodes.utils.Mat2WritableImage
import GUISamples.features.nodes.utils.imageToMat
import javafx.scene.image.ImageView
import javafx.scene.image.WritableImage
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc


fun BlurNode(): CoreNode {
    val node = CoreNode(types.img);
    var imageView: ImageView? = null
    var image: WritableImage? = null
    var inputValue = 0.0

    fun blur() {
        if(imageView != null)
            node.centerBox.children.remove(imageView)
        if(image != null) {
            val src = imageToMat(image as WritableImage)

            val dst = Mat(src!!.rows(), src!!.cols(), src!!.type())
            val v = if (inputValue.toInt() % 2 == 0) {
                inputValue + 1
            } else {
                inputValue
            }

            val size = Size(v, v)
            Imgproc.GaussianBlur(src, dst, size, 0.0)

            val writableImage = Mat2WritableImage(dst)

            imageView = ImageView(writableImage)
            imageView!!.setFitHeight(200.0)
            imageView!!.setFitWidth(200.0)
            node.centerBox.children.add(0, imageView)

            node.updateOutValue(writableImage)
        }
    }


    val inFloat = InputMetric("kernelSize", types.int, "kernelSize", fn = { x ->
        inputValue = x.toString().toDouble()
        blur()
    })

    val inImage = InputMetric("image", types.img, "image", fn = { img ->
        image = img as WritableImage?
        blur()
    })

    node.addInputMetrics(inFloat)
    node.addInputMetrics(inImage)

    return node
}