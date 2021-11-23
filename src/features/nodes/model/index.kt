package GUISamples.features.nodes.model
import GUISamples.features.nodes.`@core`.CoreNode

val graphs = mutableMapOf<CoreNode, MutableSet<CoreNode>>();

fun addNode(node: CoreNode) {
    graphs.put(node, mutableSetOf<CoreNode>())
    println(graphs)
}

fun addSubscribe(node: CoreNode, target: CoreNode) {
    graphs.get(node)?.add(target)
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
        if (x.updater != null) {
            x.updater?.let { it(startNode.outValue as Any) }
        }
    }
}
