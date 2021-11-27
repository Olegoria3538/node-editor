package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.CreateInputInt
import GUISamples.features.nodes.`@core`.CoreSimpleInput
import GUISamples.features.nodes.`@core`.types

import javafx.scene.layout.GridPane

fun IntNode(): GridPane {
    val field = CreateInputInt(name = "kek")
    return CoreSimpleInput(field, types.int).node.root
}