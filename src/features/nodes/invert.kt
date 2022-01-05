package GUISamples.features.nodes
import GUISamples.features.nodes.`@core`.CoreNode
import GUISamples.features.nodes.`@core`.CreateImageView
import GUISamples.features.nodes.`@core`.InputMetric
import GUISamples.features.nodes.`@core`.types
import GUISamples.features.nodes.utils.matToImage
import GUISamples.features.nodes.utils.imageToMat
import javafx.scene.image.ImageView
import javafx.scene.image.WritableImage
import org.opencv.core.*


fun InvertNode(): CoreNode {
    val node = CoreNode(types.img);
    val img = CreateImageView()
    var image: WritableImage? = null

    fun shot() {
        if(img.imageView != null)
            node.centerBox.children.remove(img.imageView)
        if(image != null) {
            val src = imageToMat(image as WritableImage)!!
            val dst = Mat(src.rows(), src.cols(), src.type())
            Core.bitwise_not(src, dst)
            val writableImage = matToImage(dst)!!
            img.setImageView(writableImage)
            node.centerBox.children.add(0, img.imageView)
            node.updateOutValue(writableImage)
        } else {
            node.updateOutValue(null)
        }
    }

    val inImage = InputMetric("image", types.img, "image", fn = { img ->
        image = img as WritableImage?
        shot()
    })

    node.addInputMetrics(inImage)

    return node
}
