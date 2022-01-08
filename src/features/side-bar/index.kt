package GUISamples.features.`side-bar`

import GUISamples.features.nodes.*
import GUISamples.features.nodes.`@core`.CoreNode
import GUISamples.features.nodes.model.*
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
    val container = VBox(5.0)
    container.minWidth = sideBarWidth
    container.maxWidth = sideBarWidth

    fun createSpacer(height: Double) {
        val spacer = VBox(5.0)
        spacer.minHeight = height
        spacer.maxHeight = height
        container.children.add(spacer)
    }

    val nodesGuard = HashMap(nodesMap)
    nodesGuard.remove(nodesTypes.initImage)
    nodesGuard.remove(nodesTypes.saveImage)
    nodesGuard.values.forEach { x ->
        val btn = Button(x.title)
        btn.minWidth = sideBarWidth
        btn.onAction = EventHandler { _ ->
            val node = x.fn(createRandomId(7))
            mainRoot.children.add(node.root)
        }
        container.children.add(btn)
    }


    createSpacer(100.0)

    val btnSaveScene = Button("Сохранить сцену")
    btnSaveScene.minWidth = sideBarWidth
    btnSaveScene.onAction = EventHandler { _ ->
        saveScene()
    }
    container.children.add(btnSaveScene)

    val btnOpenScene = Button("Открыть сцену")
    btnOpenScene.minWidth = sideBarWidth
    btnOpenScene.onAction = EventHandler { _ ->
        openScene()
    }
    container.children.add(btnOpenScene)
    return container
}


fun saveScene() {
    println(graphs.keys.toList()[0].inputMetrics)
    val format = mapOf(
        "nodes" to graphs.keys.map { x ->
            mapOf(
                "nodeType" to x.nodeType,
                "id" to x.id,
                "positionX" to x.root.localToParentTransform.tx.toString(),
                "positionY" to x.root.localToParentTransform.ty.toString(),
                "fields" to x.fields.map { x ->
                    mapOf(x.key to x.value.value)
                }
            )
        },
        "subscribes" to graphs.map { x ->
            mapOf(
                "id" to x.key.id,
                "targets" to x.value.map { x ->
                    mapOf(
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
        val nodes = json.get("nodes") as List<Map<String, Any>>?
        if(nodes != null) {
            removeAllScene()
            val nodesHash = mutableMapOf<String, CoreNode>()
            //добавляем ноды на сцену
            nodes?.forEach { x ->
                val id = x.get("id") as String
                val nodeType = x.get("nodeType") as String?
                val positionX = x.get("positionX") as String?
                val positionY = x.get("positionY") as String?
                val node = nodesMap[nodeType]?.fn?.let { it(id) }
                if(node != null) {
                    nodesHash[id] = node
                    mainRoot.children.add(node.root)
                    if(positionX != null && positionY != null) {
                        node.root.relocate(positionX.toDouble(), positionY.toDouble())
                    }
                }
            }
            val subscribes = json.get("subscribes") as List<Map<String, String>>?
            //создаем подписчков
            subscribes?.forEach { x ->
                val id = x.get("id") as String
                val targets = x.get("targets") as List<Map<String, String>>?
                val nodeStart = nodesHash.get(id)
                if(nodeStart != null) {
                    targets?.forEach { x ->
                        val endNodeId = x.get("id")
                        val linkName = x.get("linkName")
                        if(endNodeId != null && linkName != null) {
                            val nodeEnd = nodesHash.get(endNodeId)
                            val metric = nodeEnd?.inputMetrics?.get(linkName)
                            if(metric != null) {
                                addSubscribe(nodeStart, nodeEnd, metric)
                            }
                        }
                    }
                }
            }
            //изменяем значения филдов
            nodes?.forEach { x ->
                val id = x.get("id") as String
                val fields = x.get("fields") as List<Map<String, Any>>?
                val node = nodesHash[id]
                if(node != null) {
                    if(fields != null) {
                        fields.forEach { x ->
                            x.forEach { x ->
                                val field = node.fields[x.key]
                                if(field != null){
                                    field.input.textProperty().value = field.onChange(x.value.toString()).toString()
                                }
                            }
                        }
                    }
                }
            }
        }
    } catch (e: IOException) {
    }
}