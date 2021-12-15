package GUISamples.features.nodes.`@core`

import GUISamples.features.nodes.utils.copyImage
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.image.ImageView
import javafx.scene.image.WritableImage
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.stage.Stage
import java.awt.Toolkit


val size = Toolkit.getDefaultToolkit().screenSize


class CreateImageView {
    var imageView: ImageView? = null

    fun setImageView(writableImage: WritableImage) {
        imageView = ImageView(writableImage)
        imageView!!.setFitHeight(200.0)
        imageView!!.setFitWidth(200.0)
        imageView!!.setOnMouseClicked(EventHandler<MouseEvent> { click ->
            if (click.clickCount == 2) {
                val copy = copyImage(writableImage)
                val imgV = ImageView(copy)
                val window = Stage()
                window.title = "FUUUUL"
                val root = Pane()
                val scene = Scene(root, size.width.toDouble(), size.height.toDouble())
                window.setScene(scene)
                window.show()
                root.children.add(imgV)
            }
        })
    }
}