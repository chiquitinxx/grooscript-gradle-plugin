package org.grooscript.gradle

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.grooscript.GrooScript
import spock.lang.Specification

/**
 * User: jorgefrancoleza
 * Date: 14/12/13
 */
class ConvertTaskSpec extends Specification {

    static final SOURCE = ['source']
    static final DESTINATION = 'destination'
    static final JS_FILE = "${DESTINATION}/Item.js"
    Project project
    ConvertTask task

    def setup() {
        project = ProjectBuilder.builder().build()
        task = project.task('convert', type: ConvertTask)
        new File(JS_FILE).delete()
    }

    def 'create the task'() {
        expect:
        task instanceof ConvertTask
    }

    def 'run the task without source or destination throws error'() {
        when:
        task.source = source
        task.destination = destination
        task.convert()

        then:
        thrown(GradleException)

        where:
        source  |destination
        ['one'] |null
        null    |null
        null    |'two'
    }

    def 'run the task with correct data'() {
        when:
        task.source = SOURCE
        task.destination = DESTINATION
        task.convert()

        then:
        fileConverted()
    }

    private void fileConverted() {
        def jsFile = new File(JS_FILE)
        assert jsFile.isFile() && jsFile.exists()
    }

    def 'convert tasks with options'() {
        given:
        GroovySpy(GrooScript, global: true)
        task.source = SOURCE
        task.destination = DESTINATION
        task.classPath = ['.']
        task.convertDependencies = true
        task.customization = { true }

        when:
        task.convert()

        then:
        1 * GrooScript.clearAllOptions()
        1 * GrooScript.setConversionProperty('customization', task.customization)
        1 * GrooScript.setConversionProperty('classPath', task.classPath)
        1 * GrooScript.setConversionProperty('convertDependencies', task.convertDependencies)
        1 * GrooScript.convert(SOURCE[0], DESTINATION) >> true
    }
}
