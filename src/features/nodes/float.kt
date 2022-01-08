package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.*

fun FloatNode(id: String): CoreNode {
    val field = CreateInputFloat(name = "kek")
    return CoreSimpleInput(field, nodesTypes.float, id, "Float").node
}