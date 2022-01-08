package GUISamples.features.nodes.`@core`

import javafx.scene.control.Button

open class InputMetricCore (
    val name: String,
    val type: String,
    text: String,
    val fn: ((x: Any?) -> Unit)
) {
    val btn = Button(text)
}