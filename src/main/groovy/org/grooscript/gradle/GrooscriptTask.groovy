package org.grooscript.gradle

import org.gradle.api.DefaultTask
import org.grooscript.convert.ConversionOptions

/**
 * User: jorgefrancoleza
 * Date: 17/12/13
 */
class GrooscriptTask extends DefaultTask {

    List<String> source
    String destination
    List<String> classPath
    Closure customization
    String initialText
    String finalText
    boolean recursive = false
    List<String> mainContextScope
    String addGsLib
    boolean requireJs = false

    void checkProperties() {
        source = source ?: project.extensions.grooscript?.source
        destination = destination ?: project.extensions.grooscript?.destination
        classPath = classPath ?: project.extensions.grooscript?.classPath
        classPath = classPath.collect { project.file(it).path }
        customization = customization ?: project.extensions.grooscript?.customization
        initialText = initialText ?: project.extensions.grooscript?.initialText
        finalText = finalText ?: project.extensions.grooscript?.finalText
        recursive = recursive ?: project.extensions.grooscript?.recursive
        mainContextScope = mainContextScope ?: project.extensions.grooscript?.mainContextScope
        addGsLib = addGsLib ?: project.extensions.grooscript?.addGsLib
    }

    Map getConversionProperties() {
        ConversionOptions.values().collect { it.text }.inject([:]) { properties, property ->
            properties.put(property, this."$property")
            properties
        }
    }
}
