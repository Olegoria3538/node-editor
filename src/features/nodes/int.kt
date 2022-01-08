package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.*

fun IntNode(id: String): CoreNode {
    val field = CreateInputInt(name = "kek")
    return CoreSimpleInput(field, nodesTypes.int, id, "Integer").node
}