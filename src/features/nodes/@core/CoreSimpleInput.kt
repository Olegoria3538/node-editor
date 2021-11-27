package GUISamples.features.nodes.`@core`

import javafx.scene.control.Button


class CoreSimpleInput <T> (input: CreateInputCore<T>, outType: String)  {
    val node = CoreNode(outType)
    init {
        node.updateOutValue(input.value as Any)
        input.addWatcher { x ->
            node.updateOutValue(x as Any)
        }
        node.centerBox.children.add(input.input);
    }
}