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
        def daemon
        daemon = task.launchDaemon()
        Thread.sleep(200)

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
}
