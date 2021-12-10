package GUISamples.features.nodes.`@core`

import javafx.scene.control.Button

class InputMetric (
    val name: String,
    val type: String,
    text: String,
    val fn: ((x: Any?) -> Unit)
) {
    val btn = Button(text)
}