package org.grooscript.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * User: jorgefrancoleza
 * Date: 15/12/13
 */
class ThreadTaskSpec extends Specification {

    Project project
    ThreadTask task

    def setup() {
        project = ProjectBuilder.builder().build()
        task = project.task('thread', type: ThreadTask)
        task.project = project
    }

    def 'it doesn\'t wait infinite'() {
        expect:
        task.waitInfinite == false
    }
}
