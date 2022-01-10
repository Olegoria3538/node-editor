package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.*
import GUISamples.features.nodes.utils.copyImage
import GUISamples.features.nodes.utils.imageToMat
import GUISamples.features.nodes.utils.matToImage
import javafx.scene.image.WritableImage
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc

fun AddTextNode(id: String): CoreNode {
    val node = CreateNode(nodesTypes.addText, id)
    val img = CreateImageView()

    var image: WritableImage? = null
    var xValue = 0.0
    var yValue = 0.0
    var textValue = ""

    fun shot() {
        if(img.imageView != null)
            node.centerBox.children.remove(img.imageView)
        if(image != null) {
            if(textValue == ""){
                val imgCopy = copyImage(image!!)!!
                img.setImageView(imgCopy)
                node.centerBox.children.add(0, img.imageView)
                node.updateOutValue(imgCopy)
            } else {
                val src = imageToMat(image as WritableImage)
                val position = Point(xValue, yValue)
                val color = Scalar(0.0, 0.0, 255.0)
                val font = Imgproc.FONT_HERSHEY_SIMPLEX
                val scale = 1
                val thickness = 3
                Imgproc.putText(src, textValue, position, font, scale.toDouble(), color, thickness)
                val writableImage = matToImage(src)!!
                img.setImageView(writableImage)
                node.centerBox.children.add(0, img.imageView)
                node.updateOutValue(writableImage)
            }
        } else {
            node.updateOutValue(null)
        }
    }


    val xMetric = InputMetric("x", typesOut.int, "x", fn = { x ->
        xValue = x?.toString()?.toDouble() ?: 0.0
        shot()
    })

    val yMetric = InputMetric("y", typesOut.int, "y", fn = { x ->
        yValue = x?.toString()?.toDouble() ?: 0.0
        shot()
    })

    val textMetric = InputMetric("text", typesOut.string, "text", fn = { x ->
        textValue = x?.toString() ?: ""
        shot()
    })

    val inImage = InputMetric("image", typesOut.img, "image", fn = { img ->
        image = img as WritableImage?
        shot()
    })

    node.addInputMetrics(textMetric)
    node.addInputMetrics(xMetric)
    node.addInputMetrics(yMetric)
    node.addInputMetrics(inImage)

    return node
}