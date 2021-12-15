package GUISamples.features.`side-bar`

import GUISamples.features.nodes.*
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import GUISamples.features.nodes.`@core`.CoreNode
import GUISamples.features.nodes.model.mainRoot

val sideBarWidth = 200.0

fun SideBar(): VBox {
    val container = VBox()
    container.minWidth = sideBarWidth
    container.maxWidth = sideBarWidth
    fun createBtn (title: String, fn: (() -> CoreNode)) {
        val btn = Button(title)
        btn.minWidth = sideBarWidth
        btn.onAction = EventHandler { _ ->
           val node = fn()
           mainRoot.children.add(node.root)
        }
        container.children.add(btn)
    }
    createBtn("BlurNode") { BlurNode() }
    createBtn("FloatNode") { FloatNode() }
    createBtn("IntNode") { IntNode() }
    createBtn("TransformRotateNode") { TransformRotateNode() }
    createBtn("InvertNode") { InvertNode() }
    createBtn("BrightnessNode") { BrightnessNode() }
    createBtn("GrayNode"){GrayNode()}

    return container
}