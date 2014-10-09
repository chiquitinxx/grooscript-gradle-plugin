package org.grooscript.gradle

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification
import spock.lang.Unroll

import static org.grooscript.gradle.TemplatesTask.TEMPLATES_JS_FILENAME

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

    def 'generates templates needs variables setted'() {
        when:
        task.generateTemplatesJs()

        then:
        thrown(GradleException)

        when:
        task.templatesPath = 'src/test/resources'
        task.templates = ['one.gtpl']
        task.destinationPath = '.'
        task.generateTemplatesJs()
        def generatedFile = new File(TEMPLATES_JS_FILENAME)

        then:
        generatedFile.text.contains '''Templates.templates = gs.map().add("one.gtpl",function(model) {
  if (model === undefined) model = gs.map();
  return gs.mc(gs.fs('HtmlBuilder', this),"build",[function(it) {
    return gs.mc(this,"p",["Hello!"], gSobject);
  }]);
});'''

        cleanup:
        generatedFile.delete()
    }

    @Unroll
    def 'templatesPath and destination have to be folders'() {
        given:
        task.templatesPath = 'src/test/resources'
        task.templates = ['one.gtpl']
        task.destinationPath = '.'

        when:
        task."$property" = value
        task.generateTemplatesJs()

        then:
        thrown(GradleException)

        where:
        property          | value
        'templatesPath'   | 'build.gradle'
        'destinationPath' | 'build.gradle'
    }

    def 'generates templates with an include'() {
        when:
        task.templatesPath = 'src/test/resources'
        task.templates = ['three.gtpl']
        task.destinationPath = '.'
        task.generateTemplatesJs()
        def generatedFile = new File(TEMPLATES_JS_FILENAME)

        then:
        generatedFile.text.contains "Templates,'applyTemplate'"

        cleanup:
        generatedFile.delete()
    }
}
