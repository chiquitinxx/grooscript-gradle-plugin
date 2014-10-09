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
    ConversionDaemon daemon = Mock(ConversionDaemon)

    def setup() {
        project = ProjectBuilder.builder().build()
        task = project.task('daemon', type: DaemonTask)
        task.project = project
        task.project.extensions.grooscript = [:]
        task.daemon = daemon
        task.waitInfinite = false
    }

    @Unroll
    def 'need source and destination to start daemon'() {
        when:
        task.source = source
        task.destination = destination
        task.launchDaemon()

        then:
        0 * daemon.start()
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
        def daemon = task.launchDaemon()
        //Wait until raise error in conversion
        Thread.sleep(450)

        then:
        1 * GsConsole.message('Daemon Started.')
        1 * GsConsole.exception('Exception in daemon: null')
        daemon
        daemon.convertActor.isActive() == false
    }

    def 'test launch daemon and do conversion'() {
        given:
        GroovySpy(GsConsole, global: true)
        task.source = GOOD_SOURCE
        task.destination = GOOD_DESTINATION

        when:
        task.launchDaemon()

        then:
        1 * GsConsole.message('Daemon Started.')
    }

    def 'test launch daemon with all conversion options'() {
        given:
        def conversionDaemon = Mock(ConversionDaemon)
        task.metaClass.getNewConversionDaemon = { ->
            conversionDaemon
        }
        task.source = GOOD_SOURCE
        task.destination = GOOD_DESTINATION
        task.classPath = ['src']
        task.customization = { -> }
        task.initialText = 'initial'
        task.finalText = 'final'
        task.recursive = true
        task.mainContextScope = ['$']
        task.includeJsLib = 'gs'

        when:
        task.launchDaemon()

        then:
        1 * conversionDaemon.setSource(GOOD_SOURCE)
        1 * conversionDaemon.setDestinationFolder(GOOD_DESTINATION)
        1 * conversionDaemon.setConversionOptions({ map ->
            map.classPath == ['src'] &&
            map.customization instanceof Closure &&
            map.initialText == 'initial' &&
            map.finalText == 'final' &&
            map.recursive == true &&
            map.mainContextScope == ['$'] &&
            map.includeJsLib == 'gs'
        })
        1 * conversionDaemon.setDoAfter({ it instanceof Closure})
        1 * conversionDaemon.start()
        0 * _
    }
}
