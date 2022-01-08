package GUISamples.features.nodes.`@core`

import javafx.scene.control.TextField


class CreateInputCore<T>(
    val onChange: ((x: String) -> T),
    val initValue: T,
    val name: String
) {
    val input = TextField(initValue.toString())
    var value = initValue


    private val wathchers = mutableListOf<((x: T) -> Unit)>()
    fun addWatcher(x: ((x: T) -> Unit)) {
        wathchers.add ( x )
    }

    private fun shotWatcher() {
        wathchers.forEach {fn ->
            fn(value)
        }
    }

    init {
        input.textProperty().addListener { _, _, niu ->
            value = onChange(niu)
            shotWatcher()
        }
        input.focusedProperty().addListener { _, _, newVal ->
            if(!newVal) {
                input.textProperty().value = value.toString()
            }
        }
    }
}

fun CreateInputFloat(name: String):CreateInputCore<Float> {
   return CreateInputCore<Float>(onChange = {x -> x?.toFloatOrNull() ?: 0f}, initValue = 0f, name)
}

fun CreateInputInt(name: String): CreateInputCore<Int> {
    return CreateInputCore<Int>(onChange = {x -> x?.toFloatOrNull()?.toInt() ?: 0}, initValue = 0, name)
}

fun CreateInputString(name: String): CreateInputCore<String> {
    return CreateInputCore<String>(onChange = {x -> x}, initValue = "", name)
}

