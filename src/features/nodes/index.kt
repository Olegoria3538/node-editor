package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.CoreNode
import GUISamples.features.nodes.`@core`.typesNode

val nodesMap = hashMapOf(
    typesNode.blur to fun(id: String): CoreNode {
        return BlurNode(id)
    },
    typesNode.brightness to fun(id: String): CoreNode {
        return BrightnessNode(id)
    },
    typesNode.float to fun(id: String): CoreNode {
        return FloatNode(id)
    },
    typesNode.gray to fun(id: String): CoreNode {
        return GrayNode(id)
    },
    typesNode.initImage to fun(id: String): CoreNode {
        return InitImageNode(id)
    },
    typesNode.int to fun(id: String): CoreNode {
        return IntNode(id)
    },
    typesNode.invert to fun(id: String): CoreNode {
        return InvertNode(id)
    },
    typesNode.saveImage to fun(id: String): CoreNode {
        return SaveImageNode(id)
    },
    typesNode.transformRotate to fun(id: String): CoreNode {
        return TransformRotateNode(id)
    }
)