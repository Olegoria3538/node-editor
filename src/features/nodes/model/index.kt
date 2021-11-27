package GUISamples.features.nodes.model
import GUISamples.features.nodes.`@core`.CoreNode

val graphs = mutableMapOf<CoreNode, MutableSet<CoreNode>>();

class CreateSelectNode {
    var node : CoreNode? = null
    fun setSelectNode (target: CoreNode) {
        node = target
    }
    fun removeSelectNode () {
        node = null
    }
}

val selectNode = CreateSelectNode()

fun addNode(node: CoreNode) {
    graphs.put(node, mutableSetOf<CoreNode>())
    println(graphs)
}

fun addSubscribe(node: CoreNode, target: CoreNode) {
    graphs.get(node)?.add(target)
    println(graphs)
}


fun removeSubscribe(node: CoreNode, target: CoreNode) {
    graphs.get(node)?.remove(target)
}

fun removeNode(node: CoreNode) {
    graphs.remove(node)
    println(graphs)
}

fun shakeTree(startNode: CoreNode) {
    graphs.get(startNode)?.forEach { x ->

    }
}
