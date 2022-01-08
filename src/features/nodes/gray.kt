package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.*
import GUISamples.features.nodes.utils.matToImage
import GUISamples.features.nodes.utils.imageToMat
import javafx.scene.image.WritableImage
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc


fun GrayNode(id: String): CoreNode {
    val node = CreateNode(nodesTypes.gray, id);
    val img = CreateImageView()
    var image: WritableImage? = null

    fun shot() {
        if(img.imageView != null)
            node.centerBox.children.remove(img.imageView)
        if(image != null) {
            val src = imageToMat(image as WritableImage)!!
            val dst = Mat()
            Imgproc.cvtColor(src, dst, Imgproc.COLOR_RGB2GRAY);
            val writableImage = matToImage(dst)!!
            img.setImageView(writableImage)
            node.centerBox.children.add(0, img.imageView)
            node.updateOutValue(writableImage)
        } else {
            node.updateOutValue(null)
        }
    }

    val inImage = InputMetric("image", typesOut.img, "image", fn = { img ->
        image = img as WritableImage?
        shot()
    })

    node.addInputMetrics(inImage)
    return node
}