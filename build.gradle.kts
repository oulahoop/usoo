import com.soywiz.korge.gradle.*

plugins {
	alias(libs.plugins.korge)
}

korge {
	id = "fr.nantes.lp.example"
// To enable all targets at once

	//targetAll()

// To enable targets based on properties/environment variables
	//targetDefault()

// To selectively enable targets

	targetJvm()
	//targetJs()
	//targetDesktop()
	//targetIos()
	//targetAndroidIndirect() // targetAndroidDirect()
}


kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation("org.xerial:sqlite-jdbc:3.36.0.3")
            }
        }
    }
}
