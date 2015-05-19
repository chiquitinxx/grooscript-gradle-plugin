package org.grooscript.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.grooscript.gradle.require.RequireJsActor
import spock.lang.Specification

/**
 * User: jorgefrancoleza
 * Date: 19/05/15
 */
class RequireJsThreadTaskSpec extends Specification {

    static final SOURCE = 'source'
    static final DESTINATION = 'destination'
    Project project
    RequireJsThreadTask task

    def setup() {
        project = ProjectBuilder.builder().build()

        task = project.task('require', type: RequireJsThreadTask)
        task.project = project
        task.sourceFile = SOURCE
        task.destinationFolder = DESTINATION
        project.extensions.requireJs = [classPath: ['src/main/groovy']]
    }

    def 'create the task'() {
        expect:
        task instanceof RequireJsThreadTask
    }

    def 'start default thread'() {
        given:
        GroovySpy(RequireJsActor, global: true)
        def actor = Mock(RequireJsActor)

        when:
        task.startThread()

        then:
        1 * RequireJsActor.getInstance() >> actor
        1 * actor.setProperty('convertAction', { it.delegate == task && it.resolveStrategy == Closure.DELEGATE_ONLY})
        2 * actor.start() //????? No idea why. 1 is ok, 2 is strange, maybe super call
        1 * actor.send(project.file(SOURCE).path)
    }
}
