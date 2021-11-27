package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.CoreSimpleInput
import GUISamples.features.nodes.`@core`.CoreNode
import GUISamples.features.nodes.`@core`.types
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.layout.GridPane
import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.ImageView
import javafx.scene.image.WritableImage
import javafx.stage.FileChooser
import java.io.IOException
import javax.imageio.ImageIO

fun openImage(): WritableImage? {
    try {
        val fileChooser = FileChooser()
        val extFilterJPG = FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg")
        val extFilterPNG = FileChooser.ExtensionFilter("PNG files (*.png)", "*.png")
        fileChooser.extensionFilters.addAll(extFilterJPG, extFilterPNG)
        val file = fileChooser.showOpenDialog(null)
        val bufferedImage = ImageIO.read(file)
        return SwingFXUtils.toFXImage(bufferedImage, null)
    } catch (e: IOException) {
        return null
    }
}

fun InitImageNode(): GridPane {
    var imageView: ImageView? = null
    val node = CoreNode(types.img);
    val btn = Button("Выбрать изоюражение")
    node.centerBox.children.add(btn)
    btn.onAction = EventHandler {
        val image = openImage()
        if(image != null) {
            if(imageView != null)
                node.centerBox.children.remove(imageView)
            imageView = ImageView(image)
            imageView!!.setFitHeight(100.0)
            imageView!!.setFitWidth(100.0)
            node.centerBox.children.add(0, imageView);
            node.updateOutValue(image)
        }
    }
    return node.root
}