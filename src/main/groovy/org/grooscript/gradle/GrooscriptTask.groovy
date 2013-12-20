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

}
