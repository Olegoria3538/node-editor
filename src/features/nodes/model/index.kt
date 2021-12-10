package GUISamples.features.nodes.model
import GUISamples.features.nodes.`@core`.CoreNode
import GUISamples.features.nodes.`@core`.BoundLine
import javafx.scene.layout.AnchorPane

val mainRoot = AnchorPane()

val graphs = mutableMapOf<CoreNode, MutableMap<CoreNode, String>>();
val lines = mutableMapOf<CoreNode, MutableSet<BoundLine>>();

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
    graphs.put(node, mutableMapOf())
    lines.put(node, mutableSetOf<BoundLine>())
}

fun addSubscribe(node: CoreNode, target: CoreNode, nameMetric: String) {
    graphs.get(node)?.put(target, nameMetric)
    val line = BoundLine(
        target.inputMetrics[nameMetric]!!.btn,
        node.out
    )
    lines.get(node)?.add(line)
    lines.get(target)?.add(line)
    mainRoot.children.add(line)
}

fun upgradeLinesPosition(node: CoreNode) {
     lines.get(node)?.forEach { x ->
         x.updatePosition()
     }
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
        val node = x.key
        val name = x.value
        node.inputMetrics[name]?.fn?.let { it(startNode.outValue) }
    }
}
