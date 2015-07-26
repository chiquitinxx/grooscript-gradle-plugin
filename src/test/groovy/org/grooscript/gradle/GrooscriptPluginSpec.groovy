package org.grooscript.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.grooscript.convert.ConversionOptions
import spock.lang.Specification

/**
 * User: jorgefrancoleza
 * Date: 17/12/13
 */
class GrooscriptPluginSpec extends Specification {

    def 'initialization of plugin'() {
        given:
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'org.grooscript.conversion'

        expect:
        project.tasks.convert instanceof ConvertTask
        project.tasks.daemon instanceof DaemonTask
        project.tasks.thread instanceof ThreadTask
        project.tasks.initStaticWeb instanceof InitStaticWebTask
        project.tasks.templatesJs instanceof TemplatesTask
        project.tasks.templatesThread instanceof TemplatesThreadTask
        project.tasks.spyChanges instanceof ChangesTask
        project.tasks.syncGsLibs instanceof SyncGrooscriptLibsTask
        project.tasks.requireJs instanceof RequireJsTask
        project.tasks.requireJsThread instanceof RequireJsThreadTask
        project.tasks.size() == 10

        and:
        project.extensions.grooscript instanceof ConversionExtension
        project.extensions.templates instanceof TemplatesExtension
        project.extensions.spy instanceof ChangesExtension
        project.extensions.requireJs instanceof RequireJsExtension

        and: 'without changes in conversion options'
        ConversionOptions.values().collect { it.text } ==
                ['classpath', 'customization', 'mainContextScope', 'initialText', 'finalText', 'recursive', 'addGsLib',
                 'requireJsModule', 'consoleInfo', 'includeDependencies']
    }
}
