package GUISamples.features.`side-bar`

import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import GUISamples.features.nodes.BlurNode
import GUISamples.features.nodes.FloatNode
import GUISamples.features.nodes.IntNode
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

    return container
}