package GUISamples

import GUISamples.features.`side-bar`.SideBar
import GUISamples.features.`side-bar`.sideBarWidth
import GUISamples.features.nodes.InitImageNode
import GUISamples.features.nodes.model.mainRoot
import GUISamples.features.nodes.SaveImageNode
import javafx.application.Application

import javafx.scene.Scene
import javafx.scene.layout.HBox
import javafx.stage.Stage
import org.opencv.core.Core
import java.awt.Toolkit

val size = Toolkit.getDefaultToolkit().screenSize

class Screenshot: Application() {
    override fun start(primaryStage: Stage) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        val init = InitImageNode("_start_")
        val end =  SaveImageNode("_end_")
        mainRoot.children.addAll(init.root, end.root)
        end.root.relocate(400.0, 0.0)
        val container = HBox()
        val sideBar = SideBar()
        mainRoot.minWidth = size.width.toDouble() - sideBarWidth
        mainRoot.maxWidth = size.width.toDouble() - sideBarWidth
        container.children.addAll(mainRoot, sideBar)
        val scene = Scene(container, size.width.toDouble(), size.height.toDouble())
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

