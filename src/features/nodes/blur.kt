package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.*
import javafx.scene.image.ImageView
import javafx.scene.image.WritableImage
import javafx.scene.layout.GridPane

fun BlurNode(): CoreNode {
    val node = CoreNode(types.img);
    var imageView: ImageView? = null

    val inFloat = InputMetric("kernelSize", types.int, "kernelSize", fn = { x ->
        println(x)
    })

    val inImage = InputMetric("image", types.img, "image", fn = { image ->
        if(imageView != null)
            node.centerBox.children.remove(imageView)
        if(image != null) {
            imageView = ImageView(image as WritableImage)
            imageView!!.setFitHeight(100.0)
            imageView!!.setFitWidth(100.0)
            node.centerBox.children.add(0, imageView);
            node.updateOutValue(image)
        }
    })

    node.addInputMetrics(inFloat)
    node.addInputMetrics(inImage)

    return node
}