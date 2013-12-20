package org.grooscript.gradle

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Ignore
import spock.lang.Specification

/**
 * User: jorgefrancoleza
 * Date: 15/12/13
 */
class DaemonTaskSpec extends Specification {

    static final SOURCE = ['source']
    static final DESTINATION = 'destination'

    Project project
    DaemonTask task

    def setup() {
        project = ProjectBuilder.builder().build()
        task = project.task('daemon', type: DaemonTask)
    }

    def 'need source and destination to start daemon'() {
        when:
        task.source = source
        task.destination = destination
        task.launchDaemon()

        then:
        thrown(GradleException)

        where:
        source  |destination
        ['one'] |null
        null    |null
        null    |'two'
    }

    @Ignore //Infinite task
    def 'test launch daemon'() {
        given:
        task.source = SOURCE
        task.destination = DESTINATION
        task.classPath = ['.']
        task.convertDependencies = true
        task.customization = { true }

        when:
        task.launchDaemon()

        then:
        true
    }
}
