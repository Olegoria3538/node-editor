package GUISamples.features.nodes.`@core`

class CoreSimpleInput <T> (input: CreateInputCore<T>, outType: String, id: String, title: String)  {
    val node = CoreNode(outType, id, title)
    init {
        node.updateOutValue(input.value as Any)
        node.fields[input.name] = input as CreateInputCore<Any>
        input.addWatcher { x ->
            node.updateOutValue(x as Any)
        }
        node.centerBox.children.add(input.input);
    }
}