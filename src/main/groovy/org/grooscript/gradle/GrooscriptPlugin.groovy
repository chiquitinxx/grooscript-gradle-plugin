package org.grooscript.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.grooscript.gradle.util.InitToolsImpl

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
        configureThreadTask(project)
        configureInitStaticWeb(project)
        configureTemplates(project)
    }

    private configureConvertTask(Project project) {
        ConvertTask convertTask = project.tasks.create('convert', ConvertTask)
        convertTask.description = 'Convert groovy files to javascript files.'
        convertTask.group = GROOSCRIPT_GROUP
    }

    private configureDaemonTask(Project project) {
        DaemonTask daemonTask = project.tasks.create('daemon', DaemonTask)
        daemonTask.description = 'Launch grooscript conversion daemon. Block main thread and converting until killed.'
        daemonTask.group = GROOSCRIPT_GROUP
    }

    private configureThreadTask(Project project) {
        ThreadTask threadTask = project.tasks.create('thread', ThreadTask)
        threadTask.description = 'Start a conversion daemon in a thread. Main thread continues.'
        threadTask.group = GROOSCRIPT_GROUP
    }

    private configureInitStaticWeb(Project project) {
        InitStaticWebTask initStaticWebTask = project.tasks.create('initStaticWeb', InitStaticWebTask)
        initStaticWebTask.initTools = new InitToolsImpl()
        initStaticWebTask.description = 'Init static web project.'
        initStaticWebTask.group = GROOSCRIPT_GROUP
    }

    private configureTemplates(Project project) {
        TemplatesTask templatesTask = project.tasks.create('templates', TemplatesTask)
        templatesTask.description = 'Generate templates js file.'
        templatesTask.group = GROOSCRIPT_GROUP
    }
}
