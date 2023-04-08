package vues


import com.soywiz.korau.sound.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.ktree.*
import com.soywiz.korio.file.std.*
import com.soywiz.klock.*
import com.soywiz.kmem.*
import com.soywiz.korev.*
import com.soywiz.korge.input.*
import models.*
import models.Map
import vues.observer.*

class GameManager {}
/*
class GameManager(val map: Map) : Scene() {
    var score = 0
        set(value) {
            field = value
            notifyScoreObservers()
        }
    var combo = 0
        set(value) {
            field = value
            if (value > maxCombo) maxCombo = value
            notifyScoreObservers()
        }
    var maxCombo = 0
    var life = 100
    var time = 0
    var notesPrinted = mutableListOf<Note>()
    var currentMapTime = -2000L
    private val gameObservers = mutableListOf<GameObserver>()

    // Text
    lateinit var scoreText: QView
    lateinit var comboText: QView

    override suspend fun SContainer.sceneInit() {
        // Initialisation de la scène
        val myTree = resourcesVfs["game.ktree"].readKTree(views)
        addChild(myTree)

        // Initialisation des textes
        scoreText = myTree["score"]
        comboText = myTree["combo"]

        // Initialisation des observers
        val comboText = ComboText(comboText)
        val scoreText = ScoreText(scoreText)

        // Ajout des observers
        gameObservers.add(comboText)
        gameObservers.add(scoreText)
    }

    override suspend fun SContainer.sceneMain() {
        val music = resourcesVfs[map.musicPath].readSound()

        println("Game started")

        val t = Thread {
            startMap(this)
        }

        addUpdater {
            // Inputs
            if (views.input.keys.justPressed(Key.A)) {
                onKeyPressed("A")
            }

            if (views.input.keys.justPressed(Key.Z)) {
                onKeyPressed("B")
            }

            if (views.input.keys.justPressed(Key.E)) {
                onKeyPressed("C")
            }

            if (views.input.keys.justPressed(Key.R)) {
                onKeyPressed("D")
            }

            // Time management
            if (currentMapTime != -2000L) {
                val currentTime = DateTime.nowUnixLong()
                time = ((currentTime - currentMapTime) / 1000).toInt()

                if (time >= map.notes.last().time + 1000L) {
                    endMap()
                }
            }
        }

        music.play()

        t.start()
    }

    private fun startMap(container: SContainer) {
        val mapNotes = map.notes

        currentMapTime = DateTime.nowUnixLong()

        for (note in mapNotes) {
            val noteTime = note.time
            val delay = (noteTime - currentMapTime) / 1000
            val noteView = createNoteView(note.type, note.startPosition, delay)

            container.addChild(noteView)

            val tween = views.tween(noteView::y, noteView.y, noteView.y + 600, note.duration)
            tween.onComplete {
                if (note in notesPrinted) {
                    notesPrinted.remove(note)
                    container.removeChild(noteView)
                }
            }

            tween.start()
        }
    }

    private fun endMap() {
        println("Game over")

        // Reset game
        score = 0
        combo = 0
        life = 100
        time = 0
        notesPrinted = mutableListOf()
        currentMapTime = -2000L

        // Go back to menu
        sceneContainer.changeTo<Menu>()
    }

    private fun createNoteView(type: NoteType, startPosition: Pair<Double, Double>, delay: Long): QView {
        val noteSize = 80.0

        val noteView = when (type) {
            NoteType.LEFT -> resourcesVfs["left_note.png"].readBitmap()
            NoteType.DOWN -> resourcesVfs["down_note.png"].readBitmap()
            NoteType.UP -> resourcesVfs["up_note.png"].readBitmap()
            NoteType.RIGHT -> resourcesVfs["right_note.png"].readBitmap()
        }.toView().apply {
            xy(startPosition.first - noteSize / 2, startPosition.second - noteSize / 2)
            size(noteSize, noteSize)
            alpha(0.6)
            rotation = 45.degrees
        }

        val tween = views.tween(noteView::alpha, 0.6, 1.0, 500)

        tween.delay(delay.toDouble())

        tween.start()

        return noteView
    }

    private fun onKeyPressed(key: String) {
        val note = map.getNextNote()

        if (note == null || note in notesPrinted) {
            return
        }

        notesPrinted.add(note)

        if (note.type.key == key) {
            // Increase score and combo
            score += combo + 1
            combo++
        } else {
            // Decrease life and reset combo
            life -= 10
            combo = 0
        }
    }

    fun addScoreObserver(observer: GameObserver) {
        gameObservers.add(observer)
    }

    fun removeScoreObserver(observer: GameObserver) {
        gameObservers.remove(observer)
    }

    private fun notifyScoreObservers() {
        for (observer in gameObservers) {
            observer.onScoreChanged(score)
        }
    }

    override fun onKeyEvent(event: KeyEvent) {
        if (event.type == KeyEvent.Type.DOWN) {
            when (event.key) {
                Key.SPACE -> {
                    if (currentMapTime == -2000L) {
                        startMap(this)
                    }
                }
                Key.ESCAPE -> {
                    endMap()
                }
                Key.UP -> {
                    onKeyPresse

*/
