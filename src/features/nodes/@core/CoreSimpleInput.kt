package GUISamples.features.nodes.`@core`

import javafx.scene.control.Button


class CoreSimpleInput <T> (input: CreateInputCore<T>)  {
    val node = CoreNode(updater = null)
    init {
        node.outValue = input.value
        input.addWatcher { x ->
            node.outValue = x
            node.triggerSubcribes()
        }


        node.centerBox.children.add(input.input);

        node.leftBox.children.add(Button());
        node.leftBox.children.add(Button());
        node.leftBox.children.add(Button());
    }
}