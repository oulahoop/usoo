import com.soywiz.korge.*
import com.soywiz.korge.scene.*
import com.soywiz.korim.color.*
import vues.*

suspend fun main() = Korge(width = 1100, height = 680, virtualWidth = 1100, virtualHeight = 680, bgcolor = Colors["#2b2b2b"]) {
	val sceneContainer = sceneContainer()
	sceneContainer.changeTo({ Menu() })
}

