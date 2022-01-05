package GUISamples.features.nodes.`@core`

import GUISamples.features.nodes.model.mainRoot
import javafx.geometry.Bounds
import javafx.scene.Node
import javafx.scene.paint.Color
import javafx.scene.shape.Line
import javafx.scene.shape.StrokeLineCap


class BoundLine(
   val start: Node,
   val end: Node
) :
    Line() {

    fun getStartPosition (): Bounds? {
        return start.localToScene(start.getBoundsInLocal())
    }

    fun getEndPosition (): Bounds? {
        return end.localToScene(end.getBoundsInLocal());
    }

    fun updatePosition () {
        val startBounds = getStartPosition()!!
        val endBounds = getEndPosition()!!
        setStartX(startBounds.minX)
        setStartY(startBounds.minY)
        setEndX(endBounds.minX)
        setEndY(endBounds.minY)
    }

    fun removeScene() {
        mainRoot.children.remove(this)
    }

    init {
        updatePosition()
        setStrokeWidth(2.0)
        setStroke(Color.GRAY.deriveColor(0.0, 1.0, 1.0, 0.5))
        setStrokeLineCap(StrokeLineCap.BUTT)
        getStrokeDashArray().setAll(10.0, 5.0)
        setMouseTransparent(true)
    }
}
