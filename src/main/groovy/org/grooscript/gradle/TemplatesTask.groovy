package org.grooscript.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import org.grooscript.GrooScript
import org.grooscript.convert.ConversionOptions

class TemplatesTask extends DefaultTask {

    String templatesPath
    List<String> templates
    String destination
    String classPath

    @TaskAction
    void generateTemplatesJs() {
        if (templatesPath && templates && destination) {
            File templatesFolder = new File(templatesPath)
            if (!templatesFolder.isDirectory()) {
                throw new GradleException('templatesPath has to be a folder: ' + templatesPath)
            }
        } else {
            throw new GradleException('Have to define task properties: templatesPath, templates, destination')
        }
    }

    private String doConversion(classCode) {
        GrooScript.clearAllOptions()
        GrooScript.setConversionProperty(ConversionOptions.CLASSPATH.text, classPath)
        GrooScript.setConversionProperty(ConversionOptions.CUSTOMIZATION.text, { ->
            ast(org.grooscript.gradle.asts.TemplateEnhancer)
        })
        GrooScript.convert(classCode)
    }
}
