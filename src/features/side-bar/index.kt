package GUISamples.features.`side-bar`

import GUISamples.features.nodes.*
import GUISamples.features.nodes.`@core`.CoreNode
import GUISamples.features.nodes.model.graphs
import GUISamples.features.nodes.model.mainRoot
import GUISamples.features.nodes.utils.createRandomId
import com.google.gson.Gson
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import javafx.stage.Stage
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.nio.file.Files


val sideBarWidth = 200.0

fun SideBar(): VBox {
    val container = VBox()
    container.minWidth = sideBarWidth
    container.maxWidth = sideBarWidth
    fun createBtn (title: String, fn: ((id: String) -> CoreNode)): Button {
        val btn = Button(title)
        btn.minWidth = sideBarWidth
        btn.onAction = EventHandler { _ ->
           val node = fn(createRandomId(7))
           mainRoot.children.add(node.root)
        }
        container.children.add(btn)
        return btn
    }
    createBtn("BlurNode") { id -> BlurNode(id) }
    createBtn("FloatNode") { id ->  FloatNode(id) }
    createBtn("IntNode") { id ->  IntNode(id) }
    createBtn("TransformRotateNode") { id ->  TransformRotateNode(id) }
    createBtn("InvertNode") { id ->  InvertNode(id) }
    createBtn("BrightnessNode") { id ->  BrightnessNode(id) }
    createBtn("GrayNode"){ id ->  GrayNode(id) }

    val btnSaveScene = Button("Сохранить сцену")
    btnSaveScene.onAction = EventHandler { _ ->
        saveScene()
    }
    container.children.add(btnSaveScene)
    btnSaveScene.style = "fx-margin-top: 30px;"

    val btnOpenScene = Button("Открыть сцену")
    btnOpenScene.onAction = EventHandler { _ ->
        openScene()
    }
    container.children.add(btnOpenScene)
    btnOpenScene.style = "fx-margin-top: 30px;"
    return container
}


fun saveScene() {
    val format = mapOf(
        "nodes" to graphs.keys.map { x ->
            mapOf(
                "nodeType" to x.nodeType,
                "id" to x.id
            )
        },
        "subscribes" to graphs.map { x ->
            mapOf(
                "id" to x.key.id,
                "targets" to x.value.map { x ->
                    mapOf(
                        "nodeType" to x.key.nodeType,
                        "linkName" to x.value,
                        "id" to x.key.id
                    )
                }
            )
        }
    )
    val gson = Gson()
    val json = gson.toJson(format) as String

    if(json != null) {
        val window = Stage()
        val fileChooser = FileChooser()
        val extFilter = FileChooser.ExtensionFilter(
            "image files (*.json)", "*.json"
        )
        fileChooser.extensionFilters.add(extFilter)
        var file = fileChooser.showSaveDialog(window)
        if (file != null) {
            val fileName = file.name
            if (!fileName.toUpperCase().endsWith(".JSON")) {
                file = File(file.absolutePath + ".json")
            }
            Files.write(file.toPath(), json.toByteArray());
        }
    }
}

fun openScene() {
    try {
        val fileChooser = FileChooser()
        val extFilter = FileChooser.ExtensionFilter("PNG files (*.png)", "*.json")
        fileChooser.extensionFilters.addAll(extFilter)
        val file = fileChooser.showOpenDialog(null)
        val encoded = Files.readAllBytes(file.toPath());
        val content =  String(encoded, Charset.defaultCharset())
        val gson = Gson()
        val json = gson.fromJson(content, Map::class.java)
        val nodes = json.get("nodes")
        if(nodes != null) {
            println(nodes)
        }
    } catch (e: IOException) {
    }
}