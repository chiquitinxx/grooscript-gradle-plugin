package org.grooscript.gradle

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.grooscript.GrooScript
import spock.lang.Specification
import spock.lang.Unroll

/**
 * User: jorgefrancoleza
 * Date: 14/12/13
 */
class ConvertTaskSpec extends Specification {

    static final SOURCE = ['source']
    static final DESTINATION = 'destination'
    Project project
    ConvertTask task

    def setup() {
        project = ProjectBuilder.builder().build()

        task = project.task('convert', type: ConvertTask)
        task.project = project
        task.project.grooscript = [:]
    }

    def 'create the task'() {
        expect:
        task instanceof ConvertTask
    }

    def 'by default properties come from project.grooscript'() {
        given:
        GroovySpy(GrooScript, global: true)
        project.grooscript = [source: ['1'], destination: '2', customization: { -> },
                classPath: ['3'], convertDependencies: true, initialText: 'initial', finalText: 'final',
                recursive: true, mainContextScope: ['7']
        ]

        when:
        task.convert()

        then:
        1 * GrooScript.clearAllOptions()
        1 * GrooScript.setConversionProperty('customization', project.grooscript.customization)
        1 * GrooScript.setConversionProperty('classPath', project.grooscript.classPath)
        1 * GrooScript.setConversionProperty('convertDependencies', project.grooscript.convertDependencies)
        1 * GrooScript.setConversionProperty('initialText', project.grooscript.initialText)
        1 * GrooScript.setConversionProperty('finalText', project.grooscript.finalText)
        1 * GrooScript.setConversionProperty('recursive', project.grooscript.recursive)
        1 * GrooScript.setConversionProperty('mainContextScope', project.grooscript.mainContextScope)
        1 * GrooScript.convert(project.grooscript.source, project.grooscript.destination) >> true
        0 * _
    }

    def 'don\'t override task properties'() {
        given:
        GroovySpy(GrooScript, global: true)
        project.grooscript = [source: ['1'], destination: '2', customization: { -> },
                classPath: ['3'], convertDependencies: true]
        task.source = SOURCE

        when:
        task.convert()

        then:
        1 * GrooScript.convert(SOURCE, project.grooscript.destination) >> true
    }

    @Unroll
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
        given:
        GroovySpy(GrooScript, global: true)

        when:
        task.source = SOURCE
        task.destination = DESTINATION
        task.convert()

        then:
        1 * GrooScript.convert(SOURCE, DESTINATION) >> true
    }

    def 'convert tasks with options'() {
        given:
        GroovySpy(GrooScript, global: true)
        task.source = SOURCE
        task.destination = DESTINATION
        task.classPath = ['.']
        task.convertDependencies = true
        task.customization = { true }
        task.initialText = 'initial'
        task.finalText = 'final'
        task.recursive = true
        task.mainContextScope = [',']

        when:
        task.convert()

        then:
        1 * GrooScript.clearAllOptions()
        1 * GrooScript.setConversionProperty('customization', task.customization)
        1 * GrooScript.setConversionProperty('classPath', task.classPath)
        1 * GrooScript.setConversionProperty('convertDependencies', task.convertDependencies)
        1 * GrooScript.setConversionProperty('initialText', task.initialText)
        1 * GrooScript.setConversionProperty('finalText', task.finalText)
        1 * GrooScript.setConversionProperty('recursive', task.recursive)
        1 * GrooScript.setConversionProperty('mainContextScope', task.mainContextScope)
        1 * GrooScript.convert(SOURCE, DESTINATION) >> true
        0 * _
    }
}
