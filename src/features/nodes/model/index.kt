package GUISamples.features.nodes.model
import GUISamples.features.nodes.`@core`.CoreNode
import GUISamples.features.nodes.`@core`.BoundLine
import GUISamples.features.nodes.`@core`.InputMetric

import javafx.scene.layout.AnchorPane

val mainRoot = AnchorPane()

val graphs = mutableMapOf<CoreNode, MutableMap<CoreNode, String>>();
val lines = mutableMapOf<CoreNode, MutableSet<BoundLine>>();

class CreateSelectNode {
    var node : CoreNode? = null
    fun setSelectNode (target: CoreNode) {
        if(node != null) removeSelectNode()
        target.out.style = "-fx-border-color: #f48225; -fx-border-width: 3px;";
        node = target
    }
    fun removeSelectNode () {
        node?.out?.style = ""
        node = null
    }
}

val selectNode = CreateSelectNode()

fun addNode(node: CoreNode) {
    graphs.put(node, mutableMapOf())
    lines.put(node, mutableSetOf())
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
    val ar = lines.get(node)
    if(ar != null) {
        ar.forEach { line ->
           val filterAr = lines.filter { x -> x.value.find { q -> q == line } != null && x.key != node }
            println(filterAr)
            filterAr.forEach { x ->
                x.value.remove(line)
            }
            mainRoot.children.remove(line)
        }
    }
    lines.remove(node)
}

fun shakeTree(startNode: CoreNode) {
    graphs.get(startNode)?.forEach { x ->
        val node = x.key
        val name = x.value
        node.inputMetrics[name]?.fn?.let { it(startNode.outValue) }
    }
}

fun isMakeSubscribe(node: CoreNode?, target: CoreNode?, metric: InputMetric): Boolean {
    var flag = node != null &&
            target != null &&
            node != target &&
            node.outType == metric.type
    if(!flag) return  false
    graphs.values.forEach { x ->
            x.forEach { x ->
                if (x.key == target && x.value == metric.name) {
                    flag = false
                }
            }
    }
    return flag
}
