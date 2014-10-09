package org.grooscript.gradle

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * User: jorgefrancoleza
 * Date: 14/12/13
 */
class TemplatesTaskSpec extends Specification {

    Project project
    TemplatesTask task

    def setup() {
        project = ProjectBuilder.builder().build()

        task = project.task('templates', type: TemplatesTask)
        task.project = project
    }

    def 'create the task'() {
        expect:
        task instanceof TemplatesTask
    }

    def 'generates templates js file'() {

        when:
        task.generateTemplatesJs()

        then:
        thrown(GradleException)
    }
}
