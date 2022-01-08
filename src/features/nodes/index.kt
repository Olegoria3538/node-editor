package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.CoreNode
import GUISamples.features.nodes.`@core`.nodesTypes

val nodesMap = hashMapOf(
    nodesTypes.blur to fun(id: String): CoreNode {
        return BlurNode(id)
    },
    nodesTypes.brightness to fun(id: String): CoreNode {
        return BrightnessNode(id)
    },
    nodesTypes.float to fun(id: String): CoreNode {
        return FloatNode(id)
    },
    nodesTypes.gray to fun(id: String): CoreNode {
        return GrayNode(id)
    },
    nodesTypes.initImage to fun(id: String): CoreNode {
        return InitImageNode(id)
    },
    nodesTypes.int to fun(id: String): CoreNode {
        return IntNode(id)
    },
    nodesTypes.invert to fun(id: String): CoreNode {
        return InvertNode(id)
    },
    nodesTypes.saveImage to fun(id: String): CoreNode {
        return SaveImageNode(id)
    },
    nodesTypes.transformRotate to fun(id: String): CoreNode {
        return TransformRotateNode(id)
    }
)