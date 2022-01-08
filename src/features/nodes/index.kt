package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.*
import GUISamples.features.nodes.model.selectNode
import GUISamples.features.nodes.utils.copyImage
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.image.WritableImage
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.Stage
import java.awt.Toolkit


fun typeMetricToColor(type: String): Color {
    return when (type) {
        typesOut.float -> Color.YELLOW
        typesOut.int -> Color.BLUE
        typesOut.img -> Color.CHOCOLATE
        else -> Color.RED
    }
}

class _typesOutNode {
    val img = "img"
    val int = "int"
    val float = "float"
    val none = null
}
val typesOut = _typesOutNode()

class _nodeTypes {
    val blur = "blur"
    val brightness = "brightness"
    val float = "float"
    val gray = "gray"
    val initImage = "initImage"
    val int = "int"
    val invert = "invert"
    val saveImage = "saveImage"
    val transformRotate = "transformRotate"
}

val nodesTypes = _nodeTypes()
class NodesMapTemplate(val fn: (id: String) -> CoreNode, val outType: String?, val title: String)

val nodesMap = hashMapOf(
    nodesTypes.blur to NodesMapTemplate(fn = { x -> BlurNode(x)}, typesOut.img, "Blur"),
    nodesTypes.brightness to NodesMapTemplate(fn = { x -> BrightnessNode(x)}, typesOut.img, "Brightness"),
    nodesTypes.float to NodesMapTemplate(fn = { x -> FloatNode(x)}, typesOut.float, "Float"),
    nodesTypes.gray to NodesMapTemplate(fn = { x -> GrayNode(x) }, typesOut.img, "Gray"),
    nodesTypes.initImage to NodesMapTemplate(fn = { x -> InitImageNode(x) }, typesOut.img, "Init image"),
    nodesTypes.int to NodesMapTemplate(fn = { x -> IntNode(x) }, typesOut.int, "Integer"),
    nodesTypes.invert to NodesMapTemplate(fn = { x -> InvertNode(x) }, typesOut.img, "Invert"),
    nodesTypes.saveImage to NodesMapTemplate(fn = { x -> SaveImageNode(x) }, typesOut.none, "Save image"),
    nodesTypes.transformRotate to NodesMapTemplate(fn = { x -> TransformRotateNode(x) }, typesOut.img, "Transform"),
)

fun CreateNode(nodeType: String, id: String): CoreNode {
    val template = nodesMap[nodeType]!!
    val outType = template.outType
    val node = CoreNode(nodeType, outType, id)
    node.header.children.add(Label(template.title))

    if(outType != null) {
        val outColor = typeMetricToColor(outType)
        node.out.setBackground(Background(BackgroundFill(outColor, CornerRadii.EMPTY, Insets.EMPTY)))
        node.out.onAction = EventHandler {
            selectNode.setSelectNode(node)
        }
        node.rightBox.children.add(node.out);
    }
    return node
}

fun <T> CreateNodeSimplyInput(input: CreateInputCore<T>, nodeType: String, id: String): CoreNode  {
    val node = CreateNode(nodeType, id)
    node.updateOutValue(input.value as Any)
    node.fields[input.name] = input as CreateInputCore<Any>
    input.addWatcher { x ->
        node.updateOutValue(x as Any)
    }
    node.centerBox.children.add(input.input);
    return node
}

fun InputMetric(
    name: String,
    type: String,
    text: String,
    fn: ((x: Any?) -> Unit)): InputMetricCore
{
    val metric =  InputMetricCore(name, type, text, fn)
    val color = typeMetricToColor(type)
    metric.btn.setBackground(Background(BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)))
    return metric
}


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