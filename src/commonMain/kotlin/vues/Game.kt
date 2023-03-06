package vues

import com.soywiz.korau.sound.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.ktree.*
import com.soywiz.korio.file.std.*
import com.soywiz.korim.color.Colors
import com.soywiz.klock.*
import com.soywiz.kmem.*
import com.soywiz.korge.input.*
import models.Map

class Game(val map: Map): Scene() {
    override suspend fun SContainer.sceneInit() {
        val myTree = resourcesVfs["game.ktree"].readKTree(views)
        addChild(myTree)

        val score = myTree["score"]
        val combo = myTree["combo"]

        val touchA = myTree["rectA"]
        val touchB = myTree["rectB"]
        val touchC = myTree["rectC"]
        val touchD = myTree["rectD"]

        touchA.onClick {
            score.setText("A")
        }

        touchB.onClick {
            score.setText("B")
        }

        touchC.onClick {
            score.setText("C")
        }

        touchD.onClick {
            score.setText("D")
        }
    }

    override suspend fun SContainer.sceneMain() {
        //Start the music in musicPath with korge
        val music = resourcesVfs[map.musicPath].readSound()
        music.play()

        println("Game started")

        val t = Thread {
            map.start(this)
        }


        t.start()

        addUpdater {

        }
    }

}
