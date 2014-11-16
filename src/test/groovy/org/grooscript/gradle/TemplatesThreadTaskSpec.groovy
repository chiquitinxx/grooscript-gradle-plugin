package org.grooscript.gradle

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

import static org.grooscript.gradle.TemplatesTask.TEMPLATES_JS_FILENAME

/**
 * User: jorgefrancoleza
 * Date: 16/11/14
 */
class TemplatesThreadTaskSpec extends Specification {

    Project project
    TemplatesThreadTask task

    def setup() {
        project = ProjectBuilder.builder().build()
        project.extensions.templates = [:]

        task = project.task('templatesThread', type: TemplatesThreadTask)
        task.project = project
    }

    def 'create the task'() {
        expect:
        task instanceof TemplatesThreadTask
    }

    def 'generates templates on start thread'() {
        when:
        task.launchThread()

        then:
        thrown(GradleException)

        when:
        task.templatesPath = 'src/test/resources'
        task.templates = ['one.gtpl']
        task.destinationPath = '.'
        task.launchThread()
        def generatedFile = new File(TEMPLATES_JS_FILENAME)

        then:
        generatedFile.text.contains '''Templates.templates = gs.map().add("one.gtpl",function(model) {
  if (model === undefined) model = gs.map();
  return gs.mc(HtmlBuilder,"build",[function(it) {
    return gs.mc(Templates,"p",["Hello!"]);
  }]);
});'''

        cleanup:
        generatedFile.delete()
    }
}
