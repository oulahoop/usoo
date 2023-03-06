import com.soywiz.klock.*
import com.soywiz.korge.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*
import com.soywiz.korma.interpolation.*
import controllers.*
import database.*
import vues.*

suspend fun main() = Korge(width = 1100, height = 680, virtualWidth = 1100, virtualHeight = 680, bgcolor = Colors["#2b2b2b"]) {
	val sceneContainer = sceneContainer()
    //Database.getInstance()
    //println(ReadMap.readMap("src/commonMain/resources/maps/crazy_frog.txt"))
	sceneContainer.changeTo({ Menu() })
}

