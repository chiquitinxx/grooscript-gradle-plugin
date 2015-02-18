package org.grooscript.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.grooscript.GrooScript
import org.grooscript.convert.ConversionOptions
import org.grooscript.gradle.template.Generator
import org.grooscript.util.GsConsole

class TemplatesAbstractTask extends DefaultTask {

    String templatesPath
    List<String> templates
    String destinationFile
    List<String> classPath
    String customTypeChecker

    void checkProperties() {
        templatesPath = templatesPath ?: project.extensions.templates?.templatesPath
        if (templatesPath)
            templatesPath = project.file(templatesPath).path
        destinationFile = destinationFile ?: project.extensions.templates?.destinationFile
        if (destinationFile)
            destinationFile = project.file(destinationFile).path
        templates = templates ?: project.extensions.templates?.templates
        classPath = classPath ?: project.extensions.templates?.classPath
        if (classPath)
            classPath = classPath.collect { project.file(it).path }
    }

    void errorParameters() {
        throw new GradleException(
            'For templates, have to define task properties: templatesPath, templates, destinationPath\n'+
                    "  templatesPath = 'src/main/resources/templates'\n"+
                    "  templates = ['one.gtpl', 'folder/two.tpl']\n"+
                    "  destinationFile = 'src/main/webapp/js'"
        )
    }

    Map getConversionOptions() {
        [
            "${ConversionOptions.MAIN_CONTEXT_SCOPE.text}": ['HtmlBuilder'],
            "${ConversionOptions.CLASSPATH.text}": classPath
        ]
    }

    protected generateTemplate() {
        String classCode = new Generator(classPath: classPath, customTypeChecker: customTypeChecker).
                generateClassCode(getTemplatesMap())
        if (!destinationFile.toUpperCase().endsWith('.JS')) {
            throw new GradleException('destinationFile has to be a js file: ' + destinationFile)
        }
        new File(destinationFile).text = doConversion(classCode)
        GsConsole.message("Generated template ${destinationFile}.")
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

    private String doConversion(String classCode) {
        GrooScript.clearAllOptions()
        conversionOptions.each { key, value ->
            GrooScript.setConversionProperty(key, value)
        }
        GrooScript.convert(classCode)
    }
}
