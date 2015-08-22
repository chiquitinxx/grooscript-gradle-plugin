package org.grooscript.gradle.functional

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import static org.gradle.testkit.runner.TaskOutcome.*
import static org.grooscript.gradle.InitStaticWebTask.*
/**
 * Created by jorge on 22/8/15.
 */
class InitStaticWebFunctionalSpec extends Specification {

    def "init static web task and convert Presenter.groovy file"() {
        given:
        buildFile << 'apply plugin: org.grooscript.gradle.GrooscriptPlugin'

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments('initStaticWeb')
                .build()

        then:
        result.standardOutput.contains('Generation completed.')
        result.task(":initStaticWeb").outcome == SUCCESS

        when:
        result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments('convert')
                .build()
        def generatedFile = new File(testProjectDir.root.absolutePath + SEP + JS_APP_DIR + SEP + 'Presenter.js')

        then:
        generatedFile.exists()
        generatedFile.text.startsWith('function Presenter() {')
        result.task(":convert").outcome == SUCCESS
    }

    @Rule final TemporaryFolder testProjectDir = new TemporaryFolder()
    File buildFile

    def setup() {
        buildFile = testProjectDir.newFile('build.gradle')

        def pluginClasspathResource = getClass().classLoader.findResource("plugin-classpath.txt")
        if (pluginClasspathResource == null) {
            throw new IllegalStateException("Did not find plugin classpath resource, run `testClasses` build task.")
        }

        def pluginClasspath = pluginClasspathResource.readLines()
                .collect { it.replace('\\', '\\\\') } // escape backslashes in Windows paths
                .collect { "'$it'" }
                .join(", ")

        // Add the logic under test to the test build
        buildFile << """
            buildscript {
                dependencies {
                    classpath files($pluginClasspath)
                }
            }
        """
    }
}
