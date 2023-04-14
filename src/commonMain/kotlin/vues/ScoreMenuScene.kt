package vues

import com.soywiz.korau.sound.*
import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.filter.*
import com.soywiz.korge.view.ktree.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*
import com.soywiz.korio.file.std.*
import database.*
import models.*
import models.Map

class ScoreMenuScene(val score: Score, val soundPlaying: SoundChannel, val map: Map) : Scene() {

    override suspend fun SContainer.sceneMain() {
        // Initialisation de la scène
        val myTree = resourcesVfs["scoreMenu.ktree"].readKTree(views)
        addChild(myTree)

        // Set texte from score
        val scoreText = myTree["score"]
        val x300 = myTree["x300"]
        val x100 = myTree["x100"]
        val x50 = myTree["x50"]
        val miss = myTree["x0"]
        val top = myTree["top"]

        //Bouton et listener

        // Bouton menu
        val bgMenu = roundRect(200.0, 100.0, 20.0, fill = Colors["#00000020"]) {
            position(370.0, 575.0)
            filter = DropshadowFilter(3.0, 3.0, Colors.BLACK)
        }

        //Set button text
        text("Menu", textSize = 50.0, font = resourcesVfs["fonts/Roboto-Medium.ttf"].readTtfFont(), color = Colors["#ffffff"]) {
            centerOn(bgMenu)
            filter = DropshadowFilter(3.0, 3.0, Colors.BLACK)
        }

        bgMenu.onClick {
            // Arret de la musique
            soundPlaying.stop()
            // Changement de scene au menu
            sceneContainer.changeTo<Menu>()
        }

        // Bouton retour
        val bgRejouer = roundRect(200.0, 100.0, 20.0, fill = Colors["#00000020"]) {
            position(560.0, 575.0)
            filter = DropshadowFilter(3.0, 3.0, Colors.BLACK)
        }
        text("Rejouer", textSize = 50.0, font = resourcesVfs["fonts/Roboto-Medium.ttf"].readTtfFont(), color = Colors["#ffffff"]) {
            centerOn(bgRejouer)
            filter = DropshadowFilter(3.0, 3.0, Colors.BLACK)
        }

        bgRejouer.onClick {
            // Arret de la musique
            soundPlaying.stop()
            // Changement de scene au menu
            sceneContainer.changeTo<Game>()
        }

        scoreText.setText(score.score.toString())
        x300.setText(score.x300.toString())
        x100.setText(score.x100.toString())
        x50.setText(score.x50.toString())
        miss.setText(score.miss.toString())
        top.setText(ScoreDAO().getTop(score).toString())
    }
}
