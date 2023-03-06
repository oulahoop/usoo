package vues

import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.filter.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.font.*


class Menu: Scene() {
    override suspend fun SContainer.sceneMain() {
        //Set background images/background.png with a dark filter
        image(resourcesVfs["images/background.png"].readBitmap()) {
            filter = BlurFilter(8.0)
            scale = 0.5
        }

        //Set button rect
        val buttonRect = roundRect(300.0, 100.0, 20.0, fill = Colors["#00000020"]) {
            position(400.0, 300.0)
            filter = DropshadowFilter(3.0, 3.0, Colors.BLACK)
        }

        //Set button text
        text("Play", textSize = 50.0, font = resourcesVfs["fonts/Roboto-Medium.ttf"].readTtfFont(), color = Colors["#ffffff"]) {
            centerOn(buttonRect)
            filter = DropshadowFilter(3.0, 3.0, Colors.BLACK)
        }

        //On click on button, change scene to Game
        buttonRect.onClick {
            sceneContainer.changeTo({ ChooseMapMenu() })
        }
    }
}

