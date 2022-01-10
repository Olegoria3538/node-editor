package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.*

fun StringNode(id: String): CoreNode {
    val field = CreateInputString(name = "kek")
    return CreateNodeSimplyInput(field, nodesTypes.string, id)
}