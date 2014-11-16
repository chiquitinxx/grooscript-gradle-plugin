package org.grooscript.gradle

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.grooscript.daemon.ConversionDaemon
import org.grooscript.util.GsConsole
import spock.lang.Specification
import spock.lang.Unroll

/**
 * User: jorgefrancoleza
 * Date: 15/12/13
 */
class DaemonTaskSpec extends Specification {

    static final ANY_SOURCE = ['source']
    static final ANY_DESTINATION = 'destination'
    static final GOOD_SOURCE = ['src/test/resources/groovy']
    static final GOOD_DESTINATION = 'src/test/resources/js/app'

    Project project
    DaemonTask task

    def setup() {
        GroovySpy(ConversionDaemon, global:true)
        project = ProjectBuilder.builder().build()
        task = project.task('daemon', type: DaemonTask)
        task.project = project
        task.project.extensions.grooscript = [:]
        task.waitInfinite = false
    }

    @Unroll
    def 'need source and destination to start daemon'() {
        when:
        task.source = source
        task.destination = destination
        task.launchDaemon()

        then:
        0 * _
        thrown(GradleException)

        where:
        source  |destination
        ['one'] |null
        null    |null
        null    |'two'
    }

    def 'test launch daemon with bad options'() {
        given:
        GroovySpy(GsConsole, global: true)
        task.source = ANY_SOURCE
        task.destination = ANY_DESTINATION

        when:
        task.launchDaemon()
        //Wait until raise error in conversion
        Thread.sleep(450)

        then:
        1 * GsConsole.exception('FilesActor Error in file/folder source')
        1 * GsConsole.info('[0] Conversion daemon has converted files.')
        1 * ConversionDaemon.start(ANY_SOURCE, ANY_DESTINATION, _)
    }

    def 'test launch daemon and do conversion'() {
        given:
        GroovySpy(GsConsole, global: true)
        task.source = GOOD_SOURCE
        task.destination = GOOD_DESTINATION

        when:
        task.launchDaemon()

        then:
        1 * GsConsole.message('Listening file changes in : [src/test/resources/groovy]')
    }

    def 'test launch daemon with all conversion options'() {
        given:
        def customization = { -> }
        task.source = GOOD_SOURCE
        task.destination = GOOD_DESTINATION
        task.classPath = ['src']
        task.customization = customization
        task.initialText = 'initial'
        task.finalText = 'final'
        task.recursive = true
        task.mainContextScope = ['$']
        task.addGsLib = 'gs'

        when:
        task.launchDaemon()

        then:
        1 * ConversionDaemon.start(GOOD_SOURCE, GOOD_DESTINATION, [
                classPath : ['src'],
                customization: customization,
                initialText: 'initial',
                finalText: 'final',
                recursive: true,
                mainContextScope: ['$'],
                addGsLib: 'gs'
        ])
    }
}
