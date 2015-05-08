package org.grooscript.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.grooscript.GrooScript
import org.grooscript.convert.ConversionOptions

class RequireJsAbstractTask extends DefaultTask {

    String sourceFile
    List<String> classPath
    String destinationFolder
    Closure customization
    String initialText
    String finalText
    List<String> mainContextScope

    void checkProperties() {
        sourceFile = sourceFile ?: project.extensions.requireJs?.sourceFile
        if (sourceFile)
            sourceFile = project.file(sourceFile).path
        destinationFolder = destinationFolder ?: project.extensions.requireJs?.destinationFolder
        if (destinationFolder)
            destinationFolder = project.file(destinationFolder).path
        customization = customization ?: project.extensions.requireJs?.customization
        initialText = initialText ?: project.extensions.requireJs?.initialText
        finalText = finalText ?: project.extensions.requireJs?.finalText
        mainContextScope = mainContextScope ?: project.extensions.requireJs?.mainContextScope
        classPath = classPath ?: project.extensions.requireJs?.classPath
        if (classPath)
            classPath = classPath.collect { project.file(it).path }
    }

    void errorParameters() {
        throw new GradleException(
            'For require js, have to define task properties: sourceFile, classPath, destinationFolder\n'+
                    "  sourceFile = 'src/main/groovy/Start.groovy'\n"+
                    "  classPath = ['src/main/groovy']\n"+
                    "  destinationFolder = 'src/main/webapp/js'"
        )
    }

    void convertRequireJsFile() {
        GrooScript.clearAllOptions()
        GrooScript.setConversionProperty(ConversionOptions.CLASSPATH.text, classPath)
        GrooScript.setConversionProperty(ConversionOptions.INITIAL_TEXT.text, initialText)
        GrooScript.setConversionProperty(ConversionOptions.FINAL_TEXT.text, finalText)
        GrooScript.setConversionProperty(ConversionOptions.MAIN_CONTEXT_SCOPE.text, mainContextScope)
        GrooScript.setConversionProperty(ConversionOptions.CUSTOMIZATION.text, customization)
        GrooScript.convertRequireJs(sourceFile, destinationFolder)
    }
}
