package org.grooscript.gradle

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS
import static org.grooscript.gradle.InitStaticWebTask.SEP

/**
 * Created by jorge on 22/8/15.
 */
class ConversionsFunctionalSpec extends AbstractFunctionalSpec {

    def "convert files"() {
        given:
        copyTestResourcesFiles('C.groovy', 'UseC.groovy', 'E.groovy')
        buildFile << """
grooscript {
    source = ['${fileToConvert}.groovy']
    destination = '.'
    classpath = ['.']
}"""

        when:
        def result = runWithArguments('convert')
        def generatedFile = new File(testProjectDir.root.absolutePath + SEP + fileToConvert + '.js')

        then:
        result.task(":convert").outcome == SUCCESS

        and:
        generatedFile.exists()

        where:
        fileToConvert << ['C', 'UseC', 'E']
    }
}
