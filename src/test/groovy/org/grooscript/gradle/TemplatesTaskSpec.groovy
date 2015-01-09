package org.grooscript.gradle

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification
import spock.lang.Unroll

/**
 * User: jorgefrancoleza
 * Date: 14/12/13
 */
class TemplatesTaskSpec extends Specification {

    def 'create the task'() {
        expect:
        task instanceof TemplatesTask
    }

    def 'default properties'() {
        when:
        def extension = new TemplatesExtension()
        project.extensions.templates.templatesPath = extension.templatesPath
        project.extensions.templates.templates = extension.templates
        project.extensions.templates.destinationFile = extension.destinationFile
        project.extensions.templates.classPath = extension.classPath
        task.checkProperties()

        then:
        task.templatesPath == 'src/main/webapp/templates'
        task.templates == null
        task.destinationFile == 'src/main/webapp/js/lib/Templates.js'
        task.classPath == ['src/main/groovy']
    }

    def 'generates templates needs variables setted'() {
        when:
        task.generateTemplatesJs()

        then:
        thrown(GradleException)

        when:
        task.templatesPath = 'src/test/resources'
        task.templates = ['one.gtpl']
        task.destinationFile = FILE_NAME
        task.generateTemplatesJs()
        def generatedFile = new File(FILE_NAME)

        then:
        generatedFile.text.contains '''Templates.templates = gs.map().add("one.gtpl",function(model) {
  if (model === undefined) model = gs.map();
  return gs.mc(HtmlBuilder,"build",[function(it) {
    return gs.mc(Templates,"p",["Hello!"]);
  }]);
});'''

        cleanup:
        if (generatedFile)
            generatedFile.delete()
    }

    @Unroll
    def 'templatesPath and destination have to be folders'() {
        given:
        task.templatesPath = 'src/test/resources'
        task.templates = ['one.gtpl']
        task.destinationFile = FILE_NAME

        when:
        task."$property" = value
        task.generateTemplatesJs()

        then:
        thrown(GradleException)

        where:
        property          | value
        'templatesPath'   | 'build.gradle'
        'destinationFile' | 'build.gradle'
    }

    def 'generates templates with an include'() {
        when:
        task.templatesPath = 'src/test/resources'
        task.templates = ['three.gtpl']
        task.destinationFile = FILE_NAME
        task.generateTemplatesJs()
        def generatedFile = new File(FILE_NAME)

        then:
        generatedFile.text.contains "Templates,'applyTemplate'"

        cleanup:
        generatedFile.delete()
    }

    Project project
    TemplatesTask task
    private static final FILE_NAME = 'Templates.js'

    def setup() {
        project = ProjectBuilder.builder().build()
        project.extensions.templates = [:]

        task = project.task('templatesJs', type: TemplatesTask)
        task.project = project
    }
}
