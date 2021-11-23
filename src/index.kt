package GUISamples

import GUISamples.features.nodes.FloatNode
import javafx.application.Application

import javafx.scene.Scene
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage

class Screenshot: Application() {
    override fun start(primaryStage: Stage) {
        var root = AnchorPane()
        val kek = FloatNode()
        root.children.add(kek)

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

