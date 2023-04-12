package vues

import com.soywiz.korau.sound.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.ktree.*
import com.soywiz.korio.file.std.*
import com.soywiz.klock.*
import com.soywiz.korev.*
import models.*
import models.Map
import vues.observer.*
import kotlin.math.*

class Game(val map: Map): Scene() {

    // Variables de jeu
    var score = 0
    var combo = 0
    var maxCombo = 0
    var life = 100
    var time = 0
    var notesPrinted = mutableListOf<Note>()
    var currentMapTime = -2000L
    var lastPrecisionNote = 0

    // GameState
    var gameState = GameState.GAME

    // Text
    lateinit var scoreText : QView
    lateinit var comboText : QView
    lateinit var lifeText : QView
    lateinit var precisionNoteText : QView

    // Observers
    private val gameObservers = mutableListOf<GameObserver>()

    override suspend fun SContainer.sceneInit() {
        // Initialisation de la scène
        val myTree = resourcesVfs["game.ktree"].readKTree(views)
        addChild(myTree)

        // Initialisation des textes
        scoreText = myTree["score"]
        comboText = myTree["combo"]
        //lifeText = myTree["life"]
        precisionNoteText = myTree["precisionNote"]

        // Initialisation des observers
        val comboTextObserver = ComboText(comboText)
        val scoreTextObserver = ScoreText(scoreText)
        //val lifeTextObserver = LifeText(lifeText)
        val precisionNoteTextObserver = PrecisionNoteText(precisionNoteText)

        // Ajout des observers
        gameObservers.add(comboTextObserver)
        gameObservers.add(scoreTextObserver)
        //gameObservers.add(lifeTextObserver)
        gameObservers.add(precisionNoteTextObserver)
    }

    override suspend fun SContainer.sceneMain() {
        // Récupération de la musique
        val music = resourcesVfs[map.musicPath].readSound()

        // Lancement de la map dans un thread
        val t = Thread {
            start(this)
        }

        // Lorsqu'une touche est pressée
        addUpdater {
            if (views.input.keys.justPressed(Key.A)) {
                println("A")
                keysPressed("A")
            }

            if (views.input.keys.justPressed(Key.Z)) {
                println("B")
                keysPressed("Z")
            }

            if (views.input.keys.justPressed(Key.E)) {
                println("C")
                keysPressed("E")
            }

            if (views.input.keys.justPressed(Key.R)) {
                println("D")
                keysPressed("R")
            }
        }

        // Initialise la speed des notes
        Utils.initSpeed()

        // Lancement de la map
        t.start()

        // Lancement de la musique 2 secondes après le début de la map
        delay(2.seconds)
        music.play()
    }


    private fun endMap() {
        // Reset game
        score = 0
        combo = 0
        life = 100
        time = 0
        notesPrinted = mutableListOf()
        currentMapTime = -2000L

        // Save score to bdd
        val score = Score(map.getId(), this.score, this.maxCombo)
        //score.save()

        // Print score
        println("Score : $score")

        // Go back to menu
    }


    /**
     * Affiche les notes de la map dans la fenêtre de temps
     */
    private fun start(sContainer: SContainer) {
        //Notes a afficher
        val notesToPrint = map.notes
        //Tant que la dernière note n'est pas passé
        while (currentMapTime < notesToPrint.last().time + 2000) {
            //Pour chaque note
            for (note in notesToPrint) {
                // Si la note est dans la fenêtre de temps (10 ms d'écart)
                if(Utils.isInInterval(note.time - Utils.TIME_BEFORE_SPAWN_NOTE, currentMapTime-5, currentMapTime+5) ) {
                    //Si la note n'a pas encore été affichée
                    if(!notesPrinted.contains(note)) {
                        //Afficher la note
                        note.afficher(sContainer)
                        //Ajouter la note à la liste des notes affichées
                        notesPrinted.add(note)
                        //Supprimer la note de la liste des notes
                    }
                }
            }

            currentMapTime += 10
            //Attendre 10ms
            try {
                // Pour chaque note affiché
                notesPrinted.forEach {
                    if (it.rect != null) { // S'il son rect n'est pas null
                        it.move() // On le fait bouger
                        if (it.rect!!.y > 680) { // S'il a dépassé la limite on le supprime
                            it.rect!!.removeFromParent()
                            it.rect = null
                            updateScore(0)
                        }
                    }
                }
                Thread.sleep(10)
            }catch (e: Exception) {
                println(e)
            }
        }

        endMap()
    }

    /**
     * Lorsqu'on press une touche, on check si c'est au bon timing en fonction la note et de la lane
     *
     * @param key La touche pressée
     */
    fun keysPressed(key: String) {
        // Récupère le temps le plus proche du temps actuel de la map
        val notesToCheck = getClosestNotesPrinted()

        println(notesToCheck)


        for (note in notesToCheck) {
            // Si la note est déjà disparue
            if (note.rect == null) {
                this.isNoteWellPressed(note)
            }

            if (key == "A" && note.lane == Bloc.A) {
                this.isNoteWellPressed(note)
            }

            if (key == "Z" && note.lane == Bloc.B) {
                this.isNoteWellPressed(note)
            }

            if (key == "E" && note.lane == Bloc.C) {
                this.isNoteWellPressed(note)
            }

            if (key == "R" && note.lane == Bloc.D) {
                this.isNoteWellPressed(note)
            }
        }
    }

    fun getClosestNotesPrinted(): MutableList<Note> {
        var closestTime = Long.MIN_VALUE
        val closestNotes = mutableListOf<Note>()

        for (note in notesPrinted) {
            // Si la note n'est pas affiché on la skip
            if (note.rect == null) {
                continue
            }

            // Si le temps le plus proche est Long.MIN_VALUE on le met à la valeur de la note
            if (closestTime == Long.MIN_VALUE) {
                closestTime = note.time
                closestNotes.add(note)
            } else {

                // On regarde si le temps de la note est inférieur au temps le plus proche courant
                if (note.time < closestTime) {
                    // Si oui on supprime la liste et on ajoute la note et on assigne le closestTime
                    closestTime = note.time
                    closestNotes.clear()
                    closestNotes.add(note)
                } else if (note.time == closestTime) { // Si c'est égal (notes en même temps)
                    // On ajoute la note à la liste
                    closestNotes.add(note)
                }
            }
        }
        return closestNotes
    }

    /**
     * Lorsque la note est pressée, on check si le timing est bon
     *
     * @param note La note affichée
     */
    private fun isNoteWellPressed(note: Note) {
        // Perfect timing
        if (Utils.isInInterval(currentMapTime, note.time - 100, note.time + 100)) {
            println("Perfect timing")
            note.disappear()
            updateScore(300)
        }
        // Good timing
        else if (Utils.isInInterval(currentMapTime, note.time - 200, note.time + 200)) {
            println("Good timing")
            note.disappear()
            updateScore(100)
        }
        // Bad timing
        else if (Utils.isInInterval(currentMapTime, note.time - 300 , note.time + 300)) {
            println("Bad timing")
            note.disappear()
            updateScore(50)
        }
        // False timing
        else if (Utils.isInInterval(currentMapTime, note.time - 600, note.time + 600)) {
            println("False timing")
            note.disappear()
            updateScore(0)
        }
    }

    /**
     * Met à jour le score, le combo et la vie en fonction de la valeur passée en paramètre
     */
    private fun updateScore(value: Int) {
        lastPrecisionNote = value
        // Si c'est 0, c'est un miss donc on perd de la vie et on reset le combo
        if(value == 0) {
            life -= 10
            combo = 0
        }
        // Sinon on gagne de la vie et on augmente le combo
        else {
            life = min(100, life + 10)
            combo += 1
        }

        // On met à jour le score et le combo max
        score += value
        maxCombo = max(maxCombo, combo)

        println("Score : $score, Combo : $combo, Life : $life")

        // On notifie les observers du changement
        notifyScoreObservers()
    }

    /**
     * Notifie les observers du changement de score/combo/life
     */
    private fun notifyScoreObservers() {
        gameObservers.forEach {
            it.onScoreChanged(score, combo, life, lastPrecisionNote)
        }
    }
}
