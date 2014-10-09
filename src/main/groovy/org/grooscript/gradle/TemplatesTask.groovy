package org.grooscript.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import org.grooscript.GrooScript
import org.grooscript.convert.ConversionOptions
import org.grooscript.gradle.template.Generator

class TemplatesTask extends DefaultTask {

    String templatesPath
    List<String> templates
    String destinationPath
    String classPath

    static final TEMPLATES_JS_FILENAME = 'gstemplates.js'

    @TaskAction
    void generateTemplatesJs() {
        if (templatesPath && templates && destinationPath) {
            String classCode = new Generator().generateClassCode(getTemplatesMap())
            def pathDestination = new File(destinationPath)
            if (!pathDestination.isDirectory()) {
                throw new GradleException('destinationPath has to be a folder: ' + destinationPath)
            }
            new File(pathDestination.absolutePath + '/' + TEMPLATES_JS_FILENAME).text = doConversion(classCode)
        } else {
            throw new GradleException(
                    'Have to define task properties: templatesPath, templates, destinationPath\n'+
                    "  templatesPath 'src/main/resources/templates'\n"+
                    "  templates ['one.gtlp', 'folder/two.tpl']\n"+
                    "  destinationPath 'src/main/webapp/js'"
            )
        }
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
        GrooScript.setConversionProperty(ConversionOptions.CLASSPATH.text, classPath)
        GrooScript.convert(classCode)
    }
}
