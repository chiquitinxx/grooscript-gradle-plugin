package org.grooscript.gradle

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.grooscript.daemon.ConversionDaemon
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

/**
 * User: jorgefrancoleza
 * Date: 15/12/13
 */
class DaemonTaskSpec extends Specification {

    static final SOURCE = ['source']
    static final DESTINATION = 'destination'

    Project project
    DaemonTask task
    ConversionDaemon daemon = Mock(ConversionDaemon)

    def setup() {
        project = ProjectBuilder.builder().build()
        task = project.task('daemon', type: DaemonTask)
        task.project = project
        task.project.grooscript = [:]
        task.daemon = daemon
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

    @Ignore
    def 'test launch daemon'() {
        given:
        def daemonStarted = false
        task.metaClass.startDaemon = { -> daemonStarted = true }
        task.source = SOURCE
        task.destination = DESTINATION
        task.classPath = ['.']
        task.convertDependencies = true
        task.customization = { true }
        task.initialText = 'initial'
        task.finalText = 'final'
        task.recursive = true
        task.mainContextScope = ['7']

        when:
        task.launchDaemon()

        then:
        //1 * daemon.setSource(SOURCE)
        //1 * daemon.setDestinationFolder(DESTINATION)
        //1 * daemon.setDoAfter(_ instanceof Closure)
        //1 * daemon.setConversionOptions([:])
        daemonStarted
    }
}
