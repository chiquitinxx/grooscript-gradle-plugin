package org.grooscript.gradle

import org.gradle.testkit.runner.GradleRunner

import static org.gradle.testkit.runner.TaskOutcome.*
import static org.grooscript.gradle.InitStaticWebTask.*

/**
 * Created by jorge on 22/8/15.
 */
class InitStaticWebFunctionalSpec extends AbstractFunctionalSpec {

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
}
