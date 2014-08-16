package org.grooscript.gradle

import org.gradle.api.DefaultTask

/**
 * User: jorgefrancoleza
 * Date: 17/12/13
 */
class GrooscriptTask extends DefaultTask {

    List<String> source
    String destination
    List<String> classPath
    boolean convertDependencies
    Closure customization
    String initialText
    String finalText
    boolean recursive
    List<String> mainContextScope
    String includeJsLib

    void checkProperties() {
        source = source ?: project.extensions.grooscript?.source
        destination = destination ?: project.extensions.grooscript?.destination
        classPath = classPath ?: project.extensions.grooscript?.classPath
        convertDependencies = convertDependencies ?: project.extensions.grooscript?.convertDependencies
        customization = customization ?: project.extensions.grooscript?.customization
        initialText = initialText ?: project.extensions.grooscript?.initialText
        finalText = finalText ?: project.extensions.grooscript?.finalText
        recursive = recursive ?: project.extensions.grooscript?.recursive
        mainContextScope = mainContextScope ?: project.extensions.grooscript?.mainContextScope
        includeJsLib = includeJsLib ?: project.extensions.grooscript?.includeJsLib
    }

    Map getConversionProperties() {
        [
            classPath: classPath,
            convertDependencies: convertDependencies,
            customization: customization,
            initialText: initialText,
            finalText: finalText,
            recursive: recursive,
            mainContextScope: mainContextScope,
            includeJsLib: includeJsLib
        ]
    }
}
