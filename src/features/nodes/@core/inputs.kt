package GUISamples.features.nodes.`@core`

import javafx.scene.control.TextField


class CreateInputCore<T>(
    onChange: ((x: String) -> T),
    initValue: T
) {
    val input = TextField(initValue.toString())
    var value = initValue


    private val wathchers = mutableListOf<((x: T) -> Unit)>()
    fun addWatcher(x: ((x: T) -> Unit)) {
        wathchers.add ( x )
    }

    init {
        input.textProperty().addListener { _, _, niu ->
            value = onChange(niu)
            wathchers.forEach {fn ->
                fn(value)
            }
        }
    }
}

fun CreateInputFloat(name: String):CreateInputCore<Float> {
   return CreateInputCore<Float>(onChange = {x -> x?.toFloatOrNull() ?: 0f}, initValue = 0f)
}

fun CreateInputInt(name: String): CreateInputCore<Int> {
    return CreateInputCore<Int>(onChange = {x -> x?.toIntOrNull() ?: 0}, initValue = 0)
}

fun CreateInputString(name: String): CreateInputCore<String> {
    return CreateInputCore<String>(onChange = {x -> x}, initValue = "")
}

