package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.*
import javafx.event.EventHandler
import javafx.scene.control.Button
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

fun InitImageNode(id: String): CoreNode {
    var img = CreateImageView()
    val node = CoreNode(nodesTypes.initImage, id, "InitImage");
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
    return node
}