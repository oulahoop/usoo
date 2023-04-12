package vues

import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.filter.*
import com.soywiz.korge.view.ktree.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.font.*
import database.*

class ScoreMenuScene: Scene() {

    override suspend fun SContainer.sceneMain() {
        val score = Utils.LAST_SCORE

        // Initialisation de la scène
        val myTree = resourcesVfs["game.ktree"].readKTree(views)
        addChild(myTree)

        // Set texte from score
        val scoreText = myTree["score"]
        val x300 = myTree["x300"]
        val x100 = myTree["x100"]
        val x50 = myTree["x50"]
        val miss = myTree["x0"]
        val top = myTree["top"]

        scoreText.setText(score.score.toString())
        x300.setText(score.x300.toString())
        x100.setText(score.x100.toString())
        x50.setText(score.x50.toString())
        miss.setText(score.miss.toString())
        top.setText(ScoreDAO().getTop(score).toString())
    }
}
