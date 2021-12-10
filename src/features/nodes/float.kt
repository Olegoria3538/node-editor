package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.CoreNode
import GUISamples.features.nodes.`@core`.CreateInputFloat
import GUISamples.features.nodes.`@core`.CoreSimpleInput
import GUISamples.features.nodes.`@core`.types

import javafx.scene.layout.GridPane

fun FloatNode(): CoreNode {
    val field = CreateInputFloat(name = "kek")
    return CoreSimpleInput(field, types.float).node
}