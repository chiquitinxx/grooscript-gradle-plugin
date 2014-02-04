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

    def checkProperties() {
        source = source ?: project.grooscript?.source
        destination = destination ?: project.grooscript?.destination
        classPath = classPath ?: project.grooscript?.classPath
        convertDependencies = convertDependencies ?: project.grooscript?.convertDependencies
        customization = customization ?: project.grooscript?.customization
        initialText = initialText ?: project.grooscript?.initialText
        finalText = finalText ?: project.grooscript?.finalText
        recursive = recursive ?: project.grooscript?.recursive
        mainContextScope = mainContextScope ?: project.grooscript?.mainContextScope
    }
}
