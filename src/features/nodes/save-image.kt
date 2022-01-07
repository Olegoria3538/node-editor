package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.*
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.layout.GridPane
import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.WritableImage
import javafx.stage.FileChooser
import javafx.stage.Stage
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.imageio.ImageIO

private fun writableImageSave(img: WritableImage, file: File) {
    DataOutputStream(FileOutputStream("super-save.txt")).use { dos -> dos.writeUTF(file.path) }
    ImageIO.write(
        SwingFXUtils.fromFXImage(img, null),
        "png", file
    )
}

fun saveImage(image: WritableImage) {
    val window = Stage()
    val fileChooser = FileChooser()
    val extFilter = FileChooser.ExtensionFilter(
        "image files (*.png)", "*.png"
    )
    fileChooser.extensionFilters.add(extFilter)
    var file = fileChooser.showSaveDialog(window)
    if (file != null) {
        val fileName = file.name
        if (!fileName.toUpperCase().endsWith(".PNG")) {
            file = File(file.absolutePath + ".png")
        }
        writableImageSave(image, file)
    }
}


fun SaveImageNode(id: String): GridPane {
    val node = CoreNode(typesNode.saveImage, id);
    val btn = Button("Сохранить изображение")
    var image: WritableImage? = null
    val imgView = CreateImageView()

    node.centerBox.children.add(btn)
    btn.onAction = EventHandler {
        image?.let { saveImage(it) }
    }
    val inImage = InputMetric("image", typesOut.img, "image", fn = { img ->
        image = img as WritableImage?
        if(img != null) {
            imgView.setImageView(img)
            node.centerBox.children.add(0, imgView.imageView)
        } else {
            if(imgView.imageView != null)
                node.centerBox.children.remove(imgView.imageView)
        }
    })
    node.addInputMetrics(inImage)
    node.footer.children.remove(node.delBtn)
    return node.root
}