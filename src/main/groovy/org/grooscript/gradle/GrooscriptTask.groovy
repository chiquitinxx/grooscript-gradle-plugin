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

    def checkProperties() {
        source = source ?: project.grooscript?.source
        destination = destination ?: project.grooscript?.destination
        classPath = classPath ?: project.grooscript?.classPath
        convertDependencies = convertDependencies ?: project.grooscript?.convertDependencies
        customization = customization ?: project.grooscript?.customization
    }
}
