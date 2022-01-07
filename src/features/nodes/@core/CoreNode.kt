package GUISamples.features.nodes.`@core`

import GUISamples.features.nodes.model.*
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Point2D
import javafx.scene.control.Button
import javafx.scene.input.*
import javafx.scene.layout.*
import javafx.scene.paint.Color


class _typesOutNode {
   val img = "img"
    val int = "int"
    val float = "float"
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

val typesNode = _nodeTypes()
val nodeTypes = mapOf<String, String?>(
    typesNode.blur to typesOut.img,
    typesNode.brightness  to typesOut.img,
    typesNode.float to typesOut.float,
    typesNode.gray  to typesOut.img,
    typesNode.initImage to typesOut.img,
    typesNode.int to typesOut.int,
    typesNode.invert to typesOut.img,
    typesNode.saveImage to null,
    typesNode.transformRotate to typesOut.img,
)

open class CoreNode (val nodeType: String, val id: String) {
    val root = GridPane()
    val idDataFormat = DataFormat(id)
    val outType = nodeTypes[nodeType]

    val delBtn = Button("Удалить")

    val header = HBox()
    val leftBox = VBox()
    val centerBox = VBox()
    val rightBox = VBox()
    val footer = HBox()

    var outValue: Any? = null
    val out = Button()

    val inputMetrics = mutableMapOf<String, InputMetric>()

    fun updateOutValue(x: Any?) {
        outValue = x
        shakeTree(this)
    }

    fun typeToColor(type: String): Color {
        return when (type) {
            typesOut.float -> Color.YELLOW
            typesOut.int -> Color.BLUE
            typesOut.img -> Color.CHOCOLATE
            else -> Color.RED
        }
    }

    fun addInputMetrics(metric: InputMetric) {
        inputMetrics.put(metric.name, metric)
        val color = typeToColor(metric.type)
        val btn = metric.btn
        btn.setBackground(Background(BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)))
        btn.onAction = EventHandler {
            if (isMakeSubscribe(selectNode.node, this, metric)) {
                addSubscribe(selectNode.node!!, this, metric)
                metric.fn(selectNode.node!!.outValue)
                selectNode.removeSelectNode()
            }
        }
        btn.setOnMouseClicked(EventHandler<MouseEvent> { mouseEvent ->
            if (mouseEvent.button.equals(MouseButton.PRIMARY)) {
                if (mouseEvent.clickCount === 2) {
                    removeSubscribe(this, metric)
                }
            }
        })
        leftBox.children.add(btn);
    }

    init {
        root.setHgap(20.0)
        root.setVgap(20.0)
        root.padding = Insets(20.0)
        root.setBackground(Background(BackgroundFill(Color.web("rgba(0,0,0,0.3)"), CornerRadii.EMPTY, Insets.EMPTY)))

        root.add(header, 1, 0);
        root.add(leftBox, 0, 1);
        root.add(centerBox, 1, 1);
        root.add(rightBox, 2, 1);
        root.add(footer, 1, 2);

        val mContextDragOver = EventHandler<DragEvent> { event ->
            val p = Point2D(event.sceneX, event.sceneY)
            val localCoords = root.parent.sceneToLocal(p)
            root.relocate(
                (localCoords.x),
                (localCoords.y)
            )
            upgradeLinesPosition(this)
            event.consume()
        }

        root.setOnDragDetected { event ->
            val b = event.source as GridPane
            b.parent.onDragOver = mContextDragOver
            val content = ClipboardContent()
            content[idDataFormat] = 1
            b.startDragAndDrop(*TransferMode.ANY).setContent(content)
            event.consume()
        }

        footer.children.add(delBtn);

        delBtn.onAction = EventHandler { _ ->
            val parent = root.parent as AnchorPane
            parent.children.remove(root)
            removeNode(this)
        }

        if(outType != null) {
            val outColor = typeToColor(outType)
            out.setBackground(Background(BackgroundFill(outColor, CornerRadii.EMPTY, Insets.EMPTY)))
            out.onAction = EventHandler {
                selectNode.setSelectNode(this)
            }
            rightBox.children.add(out);
        }

        addNode(this)
    }
}


