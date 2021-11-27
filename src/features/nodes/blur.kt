package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.CoreNode
import GUISamples.features.nodes.`@core`.types
import javafx.scene.image.ImageView
import javafx.scene.image.WritableImage
import javafx.scene.layout.GridPane

fun BlurNode(): GridPane {
    val node = CoreNode(types.img);
    var imageView: ImageView? = null
    node.addInputMetrics("kernelSize",  types.int, fn = { x ->
        println(x)
    })
    node.addInputMetrics("image",  "img", fn = { image ->
        if(imageView != null)
            node.centerBox.children.remove(imageView)
        imageView = ImageView(image as WritableImage)
        imageView!!.setFitHeight(100.0)
        imageView!!.setFitWidth(100.0)
        node.centerBox.children.add(0, imageView);
        node.updateOutValue(image)
    })
    return node.root
}