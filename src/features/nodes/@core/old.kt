package GUISamples.features.nodes.`@core`

//
//import GUISamples.features.nodes.model.addNode
//import GUISamples.features.nodes.model.removeNode
//import GUISamples.features.nodes.model.shakeTree
//import GUISamples.features.nodes.utils.createRandomId
//import javafx.event.EventHandler
//import javafx.geometry.Point2D
//import javafx.scene.control.Button
//import javafx.scene.image.ImageView
//import javafx.scene.image.WritableImage
//import javafx.scene.input.ClipboardContent
//import javafx.scene.input.DataFormat
//import javafx.scene.input.DragEvent
//import javafx.scene.input.TransferMode
//import javafx.scene.layout.*
//
//
//class CoreNode (
//    inputs: List<CreateInputCore<Any>>?,
//    val outMap: ((inputsValue:  MutableMap<String, Any>?, image: WritableImage?, inputParams: Any?) -> Any)?,
//) {
//    val root = GridPane()
//
//    val id = DataFormat(createRandomId(15))
//    val valuesInputs = mutableMapOf<String, Any>()
//
//    var image: WritableImage? = null
//    var imageView: ImageView? = null
//
//    var inputValue: Any? = null
//    var outValue: Any?  = null
//
//    private val delBtn = Button("Удалить")
//
//    private val column1 = ColumnConstraints()
//    private val row1 = RowConstraints()
//    private val row2 = RowConstraints()
//    val customContentContainer = VBox()
//
//    fun updateSubscribe() {
//        if (outMap != null) {
//            outValue = outMap?.let { it(
//                valuesInputs ?: null,
//                image ?: null,
//                inputValue ?: null
//            ) }
//        }
//    }
//
//    fun updateImage (img: WritableImage?) {
//        image = img
//        if(img != null) {
//            if(imageView != null)
//                root.children.remove(imageView)
//            imageView = ImageView(img)
//            imageView!!.setFitHeight(100.0)
//            imageView!!.setFitWidth(100.0)
//            root.add(imageView, 0, 0);
//        }
//        updateSubscribe()
//    }
//
//    fun updateInputParams(x: Any, img: WritableImage?) {
//        inputValue = x
//        if(img != null)
//            updateImage(img)
//        updateSubscribe()
//    }
//
//
//    fun setValueInput(name: String, value: Any){
//        valuesInputs[name] = value
//        updateSubscribe()
//    }
//
//    init {
//        column1.minWidth = 100.0
//        root.columnConstraints.add(column1)
//
//        row1.minHeight = 100.0
//        root.rowConstraints.add(row1)
//
//        root.rowConstraints.add(row2)
//
//        root.setGridLinesVisible(true)
//        root.add(customContentContainer, 0, 1);
//
//        val mContextDragOver = EventHandler<DragEvent> { event ->
//            val p = Point2D(event.sceneX, event.sceneY)
//            val localCoords = root.parent.sceneToLocal(p)
//            root.relocate(
//                (localCoords.x),
//                (localCoords.y)
//            )
//            event.consume()
//        }
//
//        val mContextDragDropped = EventHandler<DragEvent> { event ->
//            root.parent.onDragOver = null
//            root.parent.onDragDropped = null
//
//            val p = Point2D(event.sceneX, event.sceneY)
//            val localCoords = root.parent.sceneToLocal(p)
//            root.relocate(
//                (localCoords.x),
//                (localCoords.y)
//            )
//
//            event.isDropCompleted = true
//            event.consume()
//        }
//
//        root.setOnDragDetected { event ->
//            val b = event.source as GridPane
//            b.parent.onDragOver = null
//            b.parent.onDragDropped = null
//
//            b.parent.onDragOver = mContextDragOver
//            b.parent.onDragDropped = mContextDragDropped
//
//            val content = ClipboardContent()
//            content[id] = 1
//            b.startDragAndDrop(*TransferMode.ANY).setContent(content)
//            event.consume()
//        }
//
//        if (inputs != null) {
//            inputs.forEach { field ->
//                customContentContainer.children.add(field.input)
//                field.addWatcher { x ->
//                    setValueInput(field.name, x)
//                }
//            }
//        }
//
//        customContentContainer.children.add(delBtn)
//
//        delBtn.onAction = EventHandler { _ ->
//            removeNode(this)
//            val parent = root.parent as AnchorPane
//            parent.children.remove(root)
//        }
//
//        addNode(this)
//    }
//}