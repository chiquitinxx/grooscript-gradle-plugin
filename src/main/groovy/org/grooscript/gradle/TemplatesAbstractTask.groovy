package org.grooscript.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import org.grooscript.GrooScript
import org.grooscript.convert.ConversionOptions
import org.grooscript.gradle.template.Generator
import org.grooscript.util.GrooScriptException
import org.grooscript.util.GsConsole

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
            'For templates, have to define task properties: templatesPath, templates, destinationPath\n'+
                    "  templatesPath = 'src/main/resources/templates'\n"+
                    "  templates = ['one.gtpl', 'folder/two.tpl']\n"+
                    "  destinationPath = 'src/main/webapp/js'"
        )
    }

    Map getConversionOptions() {
        [
            "${ConversionOptions.MAIN_CONTEXT_SCOPE.text}": ['HtmlBuilder'],
            "${ConversionOptions.CLASSPATH.text}": classPath
        ]
    }

    protected generateTemplate() {
        String classCode = new Generator().generateClassCode(getTemplatesMap())
        def pathDestination = new File(destinationPath)
        if (!pathDestination.isDirectory()) {
            throw new GradleException('destinationPath has to be a folder: ' + destinationPath)
        }
        def fileName = pathDestination.absolutePath + '/' + TEMPLATES_JS_FILENAME
        new File(fileName).text = doConversion(classCode)
        GsConsole.message("Generated template ${fileName}.")
    }

    private getTemplatesMap() {
        File path = new File(templatesPath)
        if (!path.isDirectory()) {
            throw new GradleException('templatesPath has to be a folder: ' + templatesPath)
        }
        templates.inject([:]) { templates, filePath ->
            templates << ["${filePath}": new File(path.absolutePath + '/' + filePath).text]
        }
    }

    private String doConversion(classCode) {
        GrooScript.clearAllOptions()
        conversionOptions.each { key, value ->
            GrooScript.setConversionProperty(key, value)
        }
        GrooScript.convert(classCode)
    }
}
