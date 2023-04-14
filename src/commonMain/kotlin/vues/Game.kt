package vues

import Utils
import com.soywiz.korau.sound.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.ktree.*
import com.soywiz.korio.file.std.*
import com.soywiz.klock.*
import com.soywiz.korge.animate.*
import com.soywiz.korge.tween.*
import com.soywiz.korim.color.*
import com.soywiz.korio.async.*
import com.soywiz.korio.async.launch
import kotlinx.coroutines.*
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

    // Variables de précision
    var x300 = 0
    var x100 = 0
    var x50 = 0
    var miss = 0

    // GameState
    var gameState = GameState.GAME

    // Text
    lateinit var scoreText : QView
    lateinit var comboText : QView
    lateinit var lifeText : QView
    lateinit var precisionNoteText : QView

    // Music
    lateinit var music: Sound
    lateinit var soundPlaying: SoundChannel

    // Rectangle de clique
    lateinit var rectA: QView
    lateinit var rectB: QView
    lateinit var rectC: QView
    lateinit var rectD: QView

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

        // Initialisation des rectangles de clique
        rectA = myTree["rectA"]
        rectB = myTree["rectB"]
        rectC = myTree["rectC"]
        rectD = myTree["rectD"]

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
        music = resourcesVfs[map.musicPath].readSound()

        // Lancement de la map dans un thread
        val t = Thread {
            start(this)
        }

        // Lorsqu'une touche est pressée
        addUpdater {
            // Récupéré la touche associée à la note
            val key1 = Utils.getKey(0)
            val key2 = Utils.getKey(1)
            val key3 = Utils.getKey(2)
            val key4 = Utils.getKey(3)

            // Lorsque la touche 1 est pressée
            if (key1 != null && views.input.keys.justPressed(key1)) {
                // Animation sur rectA pour visualiser le clic
                launchImmediately {
                    rectA.colorMul = Colors["#ff2f3e"]
                }
                keysPressed(key1.name)
            }

            // Lorsque la touche 1 est relachée
            if (key1 != null && views.input.keys.justReleased(key1)) {
                launchImmediately {
                    rectA.colorMul = Colors.RED
                }
            }

            // Lorsque la touche 2 est pressée
            if (key2 != null && views.input.keys.justPressed(key2)) {
                // Animation sur rectB pour visualiser le clic
                launchImmediately {
                    rectB.colorMul = Colors["#79ff74"]
                }

                keysPressed(key2.name)
            }

            // Lorsque la touche 2 est relachée
            if (key2 != null && views.input.keys.justReleased(key2)) {
                launchImmediately {
                    rectB.colorMul = Colors.GREEN
                }
            }

            // Lorsque la touche 3 est pressée
            if (key3 != null && views.input.keys.justPressed(key3)) {
                launchImmediately {
                    rectC.colorMul = Colors["#6765ff"]
                }
                keysPressed(key3.name)
            }

            // Lorsque la touche 3 est relachée
            if (key3 != null && views.input.keys.justReleased(key3)) {
                launchImmediately {
                    rectC.colorMul = Colors.BLUE
                }
            }

            // Lorsque la touche 4 est pressée
            if (key4 != null && views.input.keys.justPressed(key4)) {
                // Animation sur rectD pour visualiser le clic
                launchImmediately {
                    rectD.colorMul = Colors["#f9ff9b"]
                }
                keysPressed(key4.name)
            }

            // Lorsque la touche 4 est relachée
            if (key4 != null && views.input.keys.justReleased(key4)) {
                launchImmediately {
                    rectD.colorMul = Colors.YELLOW
                }
            }

        }

        // Initialise la speed des notes
        Utils.initSpeed()

        // Lancement de la map
        t.start()

        // Lancement de la musique 2 secondes après le début de la map
        delay(2.seconds)
        soundPlaying = music.play()
    }


    private suspend fun endMap() {
        // Save score to bdd
        val scoreFinal = Score(map.getId(), this.score, this.maxCombo, this.x300, this.x100, this.x50, this.miss)
        scoreFinal.save()

        // Reset game
        score = 0
        combo = 0
        life = 100
        time = 0
        notesPrinted = mutableListOf()
        currentMapTime = -2000L


        // Print score
        println("Score : $scoreFinal")

        // Retour au menu
        sceneContainer.changeTo({ ScoreMenuScene(scoreFinal, soundPlaying, map)})
    }


    /**
     * Affiche les notes de la map dans la fenêtre de temps
     */
    private fun start(sContainer: SContainer) {
        //Notes a afficher
        val notesToPrint = map.notes

        // Afficher le temps de chaque note
        for (note in notesToPrint) {
            println(note.time)
        }
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

            currentMapTime += 10 // On incrémente le temps de la map
            //Attendre 10ms
            Thread.sleep(9)
            println(currentMapTime)
            try {
                // Pour chaque note affiché
                notesPrinted.forEach {
                    if (it.rect != null) { // S'il son rect n'est pas null
                        it.move() // On le fait bouger
                        if (it.rect!!.y > 680) { // S'il a dépassé la limite on le supprime
                            it.rect!!.removeFromParent()
                            it.rect = null
                            miss++
                            updateScore(0)
                        }
                    }
                }
            }catch (e: Exception) {
                println(e)
            }
        }

        // Si on sort de la boucle alors la map est fini et on envoie l'écran de score
        launch {
            endMap()
        }
    }

    /**
     * Lorsqu'on press une touche, on check si c'est au bon timing en fonction la note et de la lane
     *
     * @param key La touche pressée
     */
    fun keysPressed(key: String) {
        // Récupère le temps le plus proche du temps actuel de la map
        val notesToCheck = getClosestNotesPrinted()

        for (note in notesToCheck) {
            // Si la note est déjà disparue
            if (note.rect == null) {
                this.isNoteWellPressed(note)
            }

            if (key == Utils.TOUCHES[0] && note.lane == Bloc.A) {
                this.isNoteWellPressed(note)
            }

            if (key == Utils.TOUCHES[1] && note.lane == Bloc.B) {
                this.isNoteWellPressed(note)
            }

            if (key == Utils.TOUCHES[2] && note.lane == Bloc.C) {
                this.isNoteWellPressed(note)
            }

            if (key == Utils.TOUCHES[3] && note.lane == Bloc.D) {
                this.isNoteWellPressed(note)
            }
        }
    }

    /**
     * Récupère les notes les plus proches du temps actuel de la map
     */
    fun getClosestNotesPrinted(): MutableList<Note> {
        var closestTime = Long.MIN_VALUE
        val closestNotes = mutableListOf<Note>()
        val lanes = mutableListOf<Bloc>()

        for (note in notesPrinted) {
            /*
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
             */

            // SI note pas affiché, on skip
            if (note.rect == null) {
                continue
            }

            // SI lane déjà pris en compte, on skip car on veut pas double clique
            if (lanes.contains(note.lane)) {
                continue
            }

            // On ajoute la note à la liste
            closestNotes.add(note)
            lanes.add(note.lane)
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
            x300++
        }
        // Good timing
        else if (Utils.isInInterval(currentMapTime, note.time - 200, note.time + 200)) {
            println("Good timing")
            note.disappear()
            updateScore(100)
            x100++
        }
        // Bad timing
        else if (Utils.isInInterval(currentMapTime, note.time - 300 , note.time + 300)) {
            println("Bad timing")
            note.disappear()
            updateScore(50)
            x50++
        }
        // False timing
        else if (Utils.isInInterval(currentMapTime, note.time - 600, note.time + 600)) {
            println("False timing")
            note.disappear()
            updateScore(0)
            miss++
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
        score += value * combo
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
