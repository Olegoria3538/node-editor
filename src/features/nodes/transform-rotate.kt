package GUISamples.features.nodes

import GUISamples.features.nodes.`@core`.CoreNode
import GUISamples.features.nodes.`@core`.CreateImageView
import GUISamples.features.nodes.`@core`.InputMetric
import GUISamples.features.nodes.`@core`.types
import GUISamples.features.nodes.utils.matToImage
import GUISamples.features.nodes.utils.imageToMat
import javafx.scene.image.ImageView
import javafx.scene.image.WritableImage
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.imgproc.Imgproc


fun TransformRotateNode(): CoreNode {
    val node = CoreNode(types.img);
    val img = CreateImageView()
    var image: WritableImage? = null
    var angle = 0.0

    fun blur() {
        if(img.imageView != null)
            node.centerBox.children.remove(img.imageView)
        if(image != null) {
            val src = imageToMat(image as WritableImage)!!
            val dst = Mat()
            val angleInt = angle.toInt()

            if (angleInt == 90 || angleInt == -270) {
                Core.transpose(src, dst);
                Core.flip(dst, dst, 1);
            } else if (angleInt == 180 || angleInt == -180)
                Core.flip(src, dst, -1);
            else if (angleInt == 270 || angleInt == -90) {
                Core.transpose(src, dst);
                Core.flip(dst, dst, 0);
            } else {
                val rotPoint = Point(src.cols() / 2.0,  src.rows() / 2.0)
                val rotMat = Imgproc.getRotationMatrix2D(rotPoint, angle, 1.0);
                Imgproc.warpAffine(src, dst, rotMat, src.size(), Imgproc.WARP_INVERSE_MAP);
            }

            val writableImage = matToImage(dst)!!
            img.setImageView(writableImage)
            node.centerBox.children.add(0, img.imageView)
            node.updateOutValue(writableImage)
        } else {
            node.updateOutValue(null)
        }
    }


    val inFloat = InputMetric("transformRotate", types.float, "transformRotate", fn = { x ->
        angle = x.toString().toDouble()
        blur()
    })

    val inImage = InputMetric("image", types.img, "image", fn = { img ->
        image = img as WritableImage?
        blur()
    })

    node.addInputMetrics(inFloat)
    node.addInputMetrics(inImage)

    return node
}