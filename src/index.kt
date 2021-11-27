package GUISamples

import GUISamples.features.nodes.BlurNode
import GUISamples.features.nodes.FloatNode
import GUISamples.features.nodes.InitImageNode
import GUISamples.features.nodes.IntNode
import javafx.application.Application

import javafx.scene.Scene
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage

class Screenshot: Application() {
    override fun start(primaryStage: Stage) {
        var root = AnchorPane()
        val kek = FloatNode()
        root.children.add(kek)

        val kek1 = BlurNode()
        root.children.add(kek1)

        val kek2 = IntNode()
        root.children.add(kek2)

        val kek3 = InitImageNode()
        root.children.add(kek3)

        var scene = Scene(root, 640.0, 480.0)
        primaryStage.scene = scene
        primaryStage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Screenshot::class.java)
        }
    }
}

