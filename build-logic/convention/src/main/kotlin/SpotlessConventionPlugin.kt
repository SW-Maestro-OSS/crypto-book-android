import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class SpotlessConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.diffplug.spotless")

            extensions.configure<SpotlessExtension> {
                val buildDirectory = layout.buildDirectory.asFileTree
                kotlin {
                    target("**/*.kt")
                    targetExclude(buildDirectory)
                    ktlint().editorConfigOverride(
                        mapOf(
                            "android" to "true",
                            "ktlint_function_naming_ignore_when_annotated_with" to "Composable",
                        ),
                    )
                    trimTrailingWhitespace()
                    endWithNewline()
                }
            }
        }
    }
}
