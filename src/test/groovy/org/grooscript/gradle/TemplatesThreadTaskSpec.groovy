package org.grooscript.gradle

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

import static org.grooscript.util.Util.SEP
/**
 * User: jorgefrancoleza
 * Date: 16/11/14
 */
class TemplatesThreadTaskSpec extends Specification {

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
        task.templatesPath = "src${SEP}test${SEP}resources"
        task.templates = ['one.gtpl']
        task.destinationFile = TEMPLATES_FILE
        task.configureAndStartThread()
        def generatedFile = new File(TEMPLATES_FILE)

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

    Project project
    TemplatesThreadTask task
    private static final TEMPLATES_FILE = 'Templates.js'

    def setup() {
        project = ProjectBuilder.builder().build()
        project.extensions.templates = [:]

        task = project.task('templatesThread', type: TemplatesThreadTask)
        task.project = project
    }
}
