package vues

import Utils
import com.soywiz.kgl.KmlGl.Companion.RGBA
import com.soywiz.korag.*
import com.soywiz.korge.annotations.*
import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.filter.*
import com.soywiz.korge.view.roundRect
import com.soywiz.korio.file.std.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.font.*
import com.soywiz.korim.format.*
import com.soywiz.korma.geom.vector.*
import database.*
import java.util.*

class Option: Scene() {

    @OptIn(KorgeExperimental::class)
    override suspend fun SContainer.sceneMain() {
        // Set texte title option center on screen
        text("Options", textSize = 50.0, font = resourcesVfs["fonts/Roboto-Medium.ttf"].readTtfFont(), color = Colors["#ffffff"]) {
            position( (views.virtualWidth / 2.0) - 100, 100.0)
            filter = DropshadowFilter(3.0, 3.0, Colors.BLACK)
        }

        uiVerticalStack(300.0) {
            append(UIPropertyRow("Touche 1")) {
                container.uiTextInput(initialText = Utils.TOUCHES[0]) {
                    onFocusLost {
                        Utils.TOUCHES[0] = text.substring(0, 1).uppercase(Locale.getDefault())
                        text = Utils.TOUCHES[0]
                    }
                }
            }

            append(UIPropertyRow("Touche 2")) {
                container.uiTextInput(initialText = Utils.TOUCHES[1]) {
                    onFocusLost {
                        Utils.TOUCHES[1] = text.substring(0, 1).uppercase(Locale.getDefault())
                        text = Utils.TOUCHES[1]
                    }
                }
            }

            append(UIPropertyRow("Touche 3")) {
                container.uiTextInput(initialText = Utils.TOUCHES[2]) {
                    onFocusLost {
                        Utils.TOUCHES[2] = text.substring(0, 1).uppercase(Locale.getDefault())
                        text = Utils.TOUCHES[2]
                    }
                }
            }

            append(UIPropertyRow("Touche 4")) {
                container.uiTextInput(initialText = Utils.TOUCHES[3]) {
                    onFocusLost {
                        Utils.TOUCHES[3] = text.substring(0, 1).uppercase(Locale.getDefault())
                        text = Utils.TOUCHES[3]
                    }
                }
            }
        }.position(200.0, 200.0)

        // Button retour
        val bgField = roundRect(200.0, 75.0, 5.0, fill = Colors["#b9aea0"]) {
            position(views.virtualWidth / 2 - 75, views.virtualHeight - 200)
            onClick {
                //Save préférences
                PreferencesDAO().setPreferences(Utils.TOUCHES)
                sceneContainer.changeTo<Menu>()
            }
        }

        // Texte retour centré sur le bouton
        text("Sauvegarder", textSize = 20.0, font = resourcesVfs["fonts/Roboto-Medium.ttf"].readTtfFont(), color = Colors.WHITE) {
            centerOn(bgField)
        }
    }
}
