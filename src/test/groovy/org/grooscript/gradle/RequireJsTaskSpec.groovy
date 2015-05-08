package org.grooscript.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.grooscript.GrooScript
import org.grooscript.convert.ConversionOptions
import spock.lang.Specification

/**
 * User: jorgefrancoleza
 * Date: 08/05/15
 */
class RequireJsTaskSpec extends Specification {

    static final SOURCE = 'source'
    static final DESTINATION = 'destination'
    Project project
    RequireJsTask task

    def setup() {
        project = ProjectBuilder.builder().build()

        task = project.task('require', type: RequireJsTask)
        task.project = project
        task.sourceFile = SOURCE
        task.destinationFolder = DESTINATION
    }

    def 'create the task'() {
        expect:
        task instanceof RequireJsTask
    }

    def 'do default conversion'() {
        given:
        GroovySpy(GrooScript, global: true)
        project.extensions.requireJs = [classPath: ['src/main/groovy']]

        when:
        task.convertRequireJs()

        then:
        1 * GrooScript.clearAllOptions()
        1 * GrooScript.getDefaultOptions()
        1 * GrooScript.setConversionProperty(ConversionOptions.CLASSPATH.text, [project.file('src/main/groovy').path])
        1 * GrooScript.setConversionProperty(ConversionOptions.INITIAL_TEXT.text, null)
        1 * GrooScript.setConversionProperty(ConversionOptions.FINAL_TEXT.text, null)
        1 * GrooScript.setConversionProperty(ConversionOptions.MAIN_CONTEXT_SCOPE.text, null)
        1 * GrooScript.setConversionProperty(ConversionOptions.CUSTOMIZATION.text, null)
        1 * GrooScript.convertRequireJs(project.file(SOURCE).path, project.file(DESTINATION).path) >> null
        0 * _
    }
}
