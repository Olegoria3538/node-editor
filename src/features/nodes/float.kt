package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.*

fun FloatNode(id: String): CoreNode {
    val field = CreateInputFloat(name = "kek")
    return CreateNodeSimplyInput<Float>(field, nodesTypes.float, id)
}