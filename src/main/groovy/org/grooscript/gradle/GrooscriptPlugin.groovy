package org.grooscript.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * User: jorgefrancoleza
 * Date: 17/12/13
 */
class GrooscriptPlugin implements Plugin<Project> {

    static final String GROOSCRIPT_GROUP = 'Grooscript'

    @Override
    void apply(Project project) {
        project.extensions.create("grooscript", GrooscriptPluginExtension)
        configureConvertTask(project)
        configureDaemonTask(project)
    }

    private configureConvertTask(Project project) {
        ConvertTask convertTask = project.tasks.create('convert', ConvertTask)
        convertTask.description = 'Convert groovy files to javascript files.'
        convertTask.group = GROOSCRIPT_GROUP
    }

    private configureDaemonTask(Project project) {
        DaemonTask daemonTask = project.tasks.create('daemon', DaemonTask)
        daemonTask.description = 'Launch grooscript conversion daemon.'
        daemonTask.group = GROOSCRIPT_GROUP
    }
}
