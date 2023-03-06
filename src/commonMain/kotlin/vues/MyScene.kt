package vues

import com.soywiz.kgl.KmlGl.Companion.RGBA
import com.soywiz.korag.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.roundRect
import com.soywiz.korio.file.std.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.font.*
import com.soywiz.korma.geom.vector.*

class MyScene : Scene() {
    override suspend fun SContainer.sceneMain() {
        val cellSize = views.virtualWidth / 5.0
        val fieldSize = 50 + 4 * cellSize
        val leftIndent = (views.virtualWidth - fieldSize) / 2
        val topIndent = 150.0



        val bgField = roundRect(fieldSize, fieldSize, 5.0, fill = Colors["#b9aea0"]) {
            position(leftIndent, topIndent)
        }
    }
}
