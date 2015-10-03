package org.grooscript.gradle

import org.gradle.testkit.runner.GradleRunner

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS
import static org.grooscript.gradle.InitStaticWebTask.SEP

/**
 * Created by jorge on 22/8/15.
 */
class ConversionsFunctionalSpec extends AbstractFunctionalSpec {

    def "convert files"() {
        given:
        copyTestResourcesFiles('C.groovy', 'UseC.groovy', 'E.groovy')
        buildFile << """apply plugin: org.grooscript.gradle.GrooscriptPlugin

grooscript {
    source = ['${fileToConvert}.groovy']
    destination = '.'
    classpath = ['.']
}"""

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments('convert')
                .build()
        def generatedFile = new File(testProjectDir.root.absolutePath + SEP + fileToConvert + '.js')

        then:
        result.task(":convert").outcome == SUCCESS

        and:
        generatedFile.exists()

        where:
        fileToConvert << ['C', 'UseC', 'E']
    }
}
