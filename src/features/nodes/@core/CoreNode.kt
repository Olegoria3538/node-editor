package GUISamples.features.nodes.`@core`

import GUISamples.features.nodes.model.*
import GUISamples.features.nodes.utils.createRandomId
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Point2D
import javafx.scene.control.Button
import javafx.scene.control.Label
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

val nodesTypes = _nodeTypes()
val nodeTypesToOut = mapOf<String, String?>(
    nodesTypes.blur to typesOut.img,
    nodesTypes.brightness  to typesOut.img,
    nodesTypes.float to typesOut.float,
    nodesTypes.gray  to typesOut.img,
    nodesTypes.initImage to typesOut.img,
    nodesTypes.int to typesOut.int,
    nodesTypes.invert to typesOut.img,
    nodesTypes.saveImage to null,
    nodesTypes.transformRotate to typesOut.img
)

open class CoreNode (val nodeType: String, val id: String, val title: String) {
    val root = GridPane()
    val idDataFormat = DataFormat(createRandomId(15))
    val outType = nodeTypesToOut[nodeType]


    val delBtn = Button("Удалить")

    val header = HBox()
    val leftBox = VBox()
    val centerBox = VBox()
    val rightBox = VBox()
    val footer = HBox()

    var outValue: Any? = null
    val out = Button()

    val inputMetrics = mutableMapOf<String, InputMetric>()
    val fields = mutableMapOf<String, CreateInputCore<Any>>()


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


    fun remove() {
        val parent = root.parent as AnchorPane
        parent.children.remove(root)
        removeNode(this)
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

        fun mContextDragOver(x: Double, y: Double): EventHandler<DragEvent> {
            return EventHandler<DragEvent> { event ->
                val p = Point2D(event.sceneX, event.sceneY)
                val localCoords = root.parent.sceneToLocal(p)
                root.relocate(
                    (localCoords.x - x),
                    (localCoords.y - y)
                )
                upgradeLinesPosition(this)
                event.consume()
            }
        }

        root.setOnDragDetected { event ->
            val b = event.source as GridPane
            b.parent.onDragOver = mContextDragOver(event.x, event.y)
            val content = ClipboardContent()
            content[idDataFormat] = 1
            b.startDragAndDrop(*TransferMode.ANY).setContent(content)
            event.consume()
        }

        footer.children.add(delBtn);

        delBtn.onAction = EventHandler { _ ->
            remove()
        }

        if(outType != null) {
            val outColor = typeToColor(outType)
            out.setBackground(Background(BackgroundFill(outColor, CornerRadii.EMPTY, Insets.EMPTY)))
            out.onAction = EventHandler {
                selectNode.setSelectNode(this)
            }
            rightBox.children.add(out);
        }
        header.children.add(Label(title))

        addNode(this)
    }
}


