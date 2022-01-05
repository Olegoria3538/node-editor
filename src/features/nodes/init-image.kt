package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.CreateImageView
import GUISamples.features.nodes.`@core`.CoreNode
import GUISamples.features.nodes.`@core`.types
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.layout.GridPane
import javafx.embed.swing.SwingFXUtils
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
    var img = CreateImageView()
    val node = CoreNode(types.img);
    val btn = Button("Выбрать изображение")
    node.centerBox.children.add(btn)
    btn.onAction = EventHandler {
        val image = openImage()
        if(image != null) {
            if(img.imageView != null)
                node.centerBox.children.remove(img.imageView)
            img.setImageView(image)
            node.centerBox.children.add(0, img.imageView);
            node.updateOutValue(image)
        } else {
            node.updateOutValue(null)
        }
    }
    node.footer.children.remove(node.delBtn)
    return node.root
}