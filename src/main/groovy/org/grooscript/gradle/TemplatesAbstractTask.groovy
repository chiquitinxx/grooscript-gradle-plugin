package org.grooscript.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import org.grooscript.GrooScript
import org.grooscript.convert.ConversionOptions
import org.grooscript.gradle.template.Generator
import org.grooscript.util.GrooScriptException

class TemplatesAbstractTask extends DefaultTask {

    String templatesPath
    List<String> templates
    String destinationPath
    String classPath

    static final TEMPLATES_JS_FILENAME = 'gstemplates.js'

    void checkProperties() {
        templatesPath = templatesPath ?: project.extensions.templates?.templatesPath
        destinationPath = destinationPath ?: project.extensions.templates?.destinationPath
        templates = templates ?: project.extensions.templates?.templates
        classPath = classPath ?: project.extensions.templates?.classPath
    }

    void errorParameters() {
        throw new GradleException(
            'Have to define task properties: templatesPath, templates, destinationPath\n'+
                    "  templatesPath 'src/main/resources/templates'\n"+
                    "  templates ['one.gtpl', 'folder/two.tpl']\n"+
                    "  destinationPath 'src/main/webapp/js'"
        )
    }

    Map getConversionOptions() {
        [
            "${ConversionOptions.MAIN_CONTEXT_SCOPE.text}": ['HtmlBuilder'],
            "${ConversionOptions.CLASSPATH.text}": classPath
        ]
    }
}
