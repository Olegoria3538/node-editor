package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.CreateInputFloat
import GUISamples.features.nodes.`@core`.CoreSimpleInput

import javafx.scene.layout.GridPane

fun FloatNode(): GridPane {
    val field = CreateInputFloat(name = "kek")
    return CoreSimpleInput(field).node.root
}