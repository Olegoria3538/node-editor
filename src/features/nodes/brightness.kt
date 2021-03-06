package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.*
import GUISamples.features.nodes.utils.matToImage
import GUISamples.features.nodes.utils.imageToMat
import javafx.scene.image.WritableImage
import org.opencv.core.Mat


fun BrightnessNode(id: String): CoreNode {
    val node = CreateNode(nodesTypes.brightness, id);
    val img = CreateImageView()

    var image: WritableImage? = null
    var bright = 0.0

    fun shot() {
        if(img.imageView != null)
            node.centerBox.children.remove(img.imageView)
        if(image != null) {
            val src = imageToMat(image as WritableImage)!!
            val dst = Mat()
            src.convertTo(dst, -1, bright);
            val writableImage = matToImage(dst)!!
            img.setImageView(writableImage)
            node.centerBox.children.add(0, img.imageView)
            node.updateOutValue(writableImage)
        } else {
            node.updateOutValue(null)
        }
    }


    val inFloat = InputMetric("bright", typesOut.float, "bright", fn = { x ->
        bright = x?.toString()?.toDouble() ?: 0.0
        shot()
    })

    val inImage = InputMetric("image", typesOut.img, "image", fn = { img ->
        image = img as WritableImage?
        shot()
    })

    node.addInputMetrics(inFloat)
    node.addInputMetrics(inImage)

    return node
}