package GUISamples.features.nodes.`@core`

import GUISamples.features.nodes.model.selectNode
import GUISamples.features.nodes.utils.createRandomId
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Point2D
import javafx.scene.control.Button
import javafx.scene.input.*
import javafx.scene.layout.*
import javafx.scene.paint.Color

class _types {
   val img = "img"
    val int = "int"
    val float = "float"
}
val types = _types()

open class CoreNode (val outType: String) {
    val root = GridPane()
    val id = DataFormat(createRandomId(15))

    private val delBtn = Button("Удалить")

    val header = HBox()
    val leftBox = VBox()
    val centerBox = VBox()
    val rightBox = VBox()
    val footer = HBox()

    var outValue: Any? = null
    val out = Button()

    private val wathchers = mutableSetOf<((x: Any) -> Unit)>()
    fun addWatcher(x: ((x: Any) -> Unit)) {
        wathchers.add ( x )
    }

    fun updateOutValue(x: Any?) {
        outValue = x
        println(wathchers)
        wathchers.forEach {fn ->
            fn(outValue as Any)
        }
    }

    fun typeToColor(type: String): Color {
        return when (type) {
            types.float -> Color.YELLOW
            types.int -> Color.BLUE
            types.img -> Color.CHOCOLATE
            else -> Color.RED
        }
    }

    fun addInputMetrics(name: String, type: String, fn: (x: Any?) -> Unit) {
        val color = typeToColor(type)
        val btn = Button(name)
        btn.setBackground(Background(BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)))
        btn.onAction = EventHandler {
            if (selectNode.node != null && selectNode.node != this && selectNode.node!!.outType == type) {
                selectNode.node!!.addWatcher(fn)
                selectNode.node!!.out.setStyle("");
                fn(selectNode.node!!.outValue)
                selectNode.removeSelectNode();

            }
        }
        leftBox.children.add(btn);

    }

    init {
        root.setHgap(10.0);
        root.setVgap(10.0);
        root.setGridLinesVisible(true)
        root.padding = Insets(40.0)
        root.setBackground(Background(BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)))

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
            event.consume()
        }

        root.setOnDragDetected { event ->
            val b = event.source as GridPane
            b.parent.onDragOver = mContextDragOver
            val content = ClipboardContent()
            content[id] = 1
            b.startDragAndDrop(*TransferMode.ANY).setContent(content)
            event.consume()
        }

        footer.children.add(delBtn);

        delBtn.onAction = EventHandler { _ ->
            val parent = root.parent as AnchorPane
            parent.children.remove(root)
        }

        val outColor = typeToColor(outType)
        out.setBackground(Background(BackgroundFill(outColor, CornerRadii.EMPTY, Insets.EMPTY)))
        out.onAction = EventHandler {
            selectNode.setSelectNode(this)
            out.setStyle("-fx-border-color: #f48225; -fx-border-width: 3px;");
        }
        rightBox.children.add(out);
    }
}


