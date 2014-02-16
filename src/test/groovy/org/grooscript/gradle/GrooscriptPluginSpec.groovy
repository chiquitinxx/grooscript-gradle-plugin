package org.grooscript.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * User: jorgefrancoleza
 * Date: 17/12/13
 */
class GrooscriptPluginSpec extends Specification {

    def 'test initialization of plugin'() {
        given:
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'grooscript'

        expect:
        project.tasks.convert instanceof ConvertTask
        project.tasks.daemon instanceof DaemonTask
        project.tasks.initStaticWeb instanceof InitStaticWebTask
    }
}
