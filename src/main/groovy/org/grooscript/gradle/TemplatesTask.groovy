package org.grooscript.gradle

import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import org.grooscript.GrooScript
import org.grooscript.gradle.template.Generator

class TemplatesTask extends TemplatesAbstractTask {

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
            errorParameters()
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
        conversionOptions.each { key, value ->
            GrooScript.setConversionProperty(key, value)
        }
        GrooScript.convert(classCode)
    }
}
