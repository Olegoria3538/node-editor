package GUISamples.features.nodes.`@core`

import GUISamples.features.nodes.model.addNode
import GUISamples.features.nodes.model.removeNode
import GUISamples.features.nodes.model.shakeTree
import GUISamples.features.nodes.utils.createRandomId
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Point2D
import javafx.scene.control.Button
import javafx.scene.input.ClipboardContent
import javafx.scene.input.DataFormat
import javafx.scene.input.DragEvent
import javafx.scene.input.TransferMode
import javafx.scene.layout.*
import javafx.scene.paint.Color


open class CoreNode (val updater: ((x: Any) -> Unit)?) {
    val root = GridPane()
    val id = DataFormat(createRandomId(15))

    private val delBtn = Button("Удалить")

    val header = HBox()
    val leftBox = VBox()
    val centerBox = VBox()
    val rightBox = VBox()
    val footer = HBox()

    var outValue: Any? = null

    fun triggerSubcribes() {
        shakeTree(this)
    }

    init {
        root.setHgap(10.0);
        root.setVgap(10.0);
        root.setGridLinesVisible(true)
        root.padding = Insets(40.0)
        root.setBackground(Background(BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)))

        root.add(header, 0, 0);
        root.add(leftBox, 0, 1);
        root.add(centerBox, 1, 1);
        root.add(rightBox, 2, 1);
        root.add(footer, 0, 0);

        val mContextDragOver = EventHandler<DragEvent> { event ->
            val p = Point2D(event.sceneX, event.sceneY)
            val localCoords = root.parent.sceneToLocal(p)
            root.relocate(
                (localCoords.x),
                (localCoords.y)
            )
            event.consume()
        }

        val mContextDragDropped = EventHandler<DragEvent> { event ->
            root.parent.onDragOver = null
            root.parent.onDragDropped = null

            val p = Point2D(event.sceneX, event.sceneY)
            val localCoords = root.parent.sceneToLocal(p)
            root.relocate(
                (localCoords.x),
                (localCoords.y)
            )

            event.isDropCompleted = true
            event.consume()
        }

        root.setOnDragDetected { event ->
            val b = event.source as GridPane
            b.parent.onDragOver = null
            b.parent.onDragDropped = null

            b.parent.onDragOver = mContextDragOver
            b.parent.onDragDropped = mContextDragDropped

            val content = ClipboardContent()
            content[id] = 1
            b.startDragAndDrop(*TransferMode.ANY).setContent(content)
            event.consume()
        }

        root.add(delBtn, 1, 1);

        delBtn.onAction = EventHandler { _ ->
            removeNode(this)
            val parent = root.parent as AnchorPane
            parent.children.remove(root)
        }

        addNode(this)
    }
}


