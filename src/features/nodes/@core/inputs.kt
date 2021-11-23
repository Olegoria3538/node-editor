package GUISamples.features.nodes.`@core`

import javafx.scene.control.TextField


class CreateInputCore<T>(val name: String,
                         val type: String,
                         onChange: ((x: String) -> T),
                         initValue: T) {
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
   return CreateInputCore<Float>(name, type ="text-field", onChange = {x -> x?.toFloatOrNull() ?: 0f}, initValue = 0f)
}

fun CreateInputInt(name: String): CreateInputCore<Int> {
    return CreateInputCore<Int>(name, type ="text-field", onChange = {x -> x?.toIntOrNull() ?: 0}, initValue = 0)
}

fun CreateInputString(name: String): CreateInputCore<String> {
    return CreateInputCore<String>(name, type ="text-field", onChange = {x -> x}, initValue = "")
}

//class CreateAccInputs<T, Q>(val inputs: List<CreateInputCore<Q>>, mapValues: ((data: MutableMap<String, Q>) -> T), initValue: T) {
//    val values = mutableMapOf<String, Q>()
//    var accValue = initValue
//
//    private val wathchers = mutableListOf<((x: T) -> Unit)>()
//    fun addWatcher(x: ((x: T) -> Unit)) {
//        wathchers.add ( x )
//    }
//
//    init {
//        inputs.forEach {input ->
//            input.addWatcher { x ->
//                values[input.name] = x
//                accValue = mapValues(values)
//                wathchers.forEach {fn ->
//                    fn(accValue)
//                }
//            }
//        }
//
//    }
//}