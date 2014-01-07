package org.grooscript.gradle

import org.gradle.api.DefaultTask

/**
 * User: jorgefrancoleza
 * Date: 17/12/13
 */
class GrooscriptTask extends DefaultTask {

    List<String> source = ['src/main/groovy']
    String destination = 'src/main/webapp/js'
    List<String> classPath
    boolean convertDependencies = false
    Closure customization

    def checkProperties() {
        source = project.grooscript?.source ?: source
        destination = project.grooscript?.destination ?: destination
        classPath = project.grooscript?.classPath ?: classPath
        convertDependencies = project.grooscript?.convertDependencies ?: convertDependencies
        customization = project.grooscript?.customization ?: customization
    }
}
