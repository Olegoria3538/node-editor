package GUISamples.features.nodes.model
import GUISamples.features.nodes.`@core`.*

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

fun addSubscribe(node: CoreNode, target: CoreNode, metric: InputMetricCore) {
    graphs.get(node)?.put(target, metric.name)
    val line = BoundLine(
        target.inputMetrics[metric.name]!!.btn,
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


fun removeSubscribe(node: CoreNode, metric: InputMetricCore) {
    graphs.entries.forEach {x ->
        if(node in x.value && x.value[node] == metric.name) {
            x.value.remove(node)
            val line = lines.get(node)?.find { line ->
                line.start == metric.btn || line.end == metric.btn
            }
            if(line != null) {
                line.removeScene()
                lines.get(node)?.remove(line)
                lines.get(x.key)?.remove(line)
            }
            node.inputMetrics[metric.name]?.fn?.let { it(null) }
        }
    }
}

fun removeNode(node: CoreNode) {
    val subscribes = graphs.get(node)
    graphs.remove(node)
    val ar = lines.get(node)
    if(ar != null) {
        ar.forEach { line ->
           val filterAr = lines.filter { x -> x.value.find { q -> q == line } != null && x.key != node }
            filterAr.forEach { x ->
                x.value.remove(line)
            }
            mainRoot.children.remove(line)
        }
    }
    lines.remove(node)
    subscribes?.forEach { x ->
        x.key.inputMetrics[x.value]?.fn?.let { it(null) }
    }
}

fun shakeTree(startNode: CoreNode) {
    graphs.get(startNode)?.forEach { x ->
        val node = x.key
        val name = x.value
        node.inputMetrics[name]?.fn?.let { it(startNode.outValue) }
    }
}

fun isMakeSubscribe(node: CoreNode?, target: CoreNode?, metric: InputMetricCore): Boolean {
    var flag = node != null &&
            target != null &&
            node != target &&
            node.outType == metric.type
    println(node?.outType)
    println(metric.type)
    if(!flag) return false
    graphs.values.forEach { x ->
            x.forEach { x ->
                if (x.key == target && x.value == metric.name) {
                    flag = false
                }
            }
    }
    return flag
}

fun removeAllScene() {
    val ids = graphs.keys.map{ x ->
        x.id
    }
    ids.forEach { id ->
       val f = graphs.keys.find { x -> x.id == id }
        f?.remove()
    }
}