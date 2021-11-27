package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.CreateInputFloat
import GUISamples.features.nodes.`@core`.CoreSimpleInput
import GUISamples.features.nodes.`@core`.types

import javafx.scene.layout.GridPane

fun FloatNode(): GridPane {
    val field = CreateInputFloat(name = "kek")
    return CoreSimpleInput(field, types.float).node.root
}