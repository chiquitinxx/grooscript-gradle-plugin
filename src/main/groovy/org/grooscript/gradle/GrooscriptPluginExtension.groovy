package org.grooscript.gradle

/**
 * Created by jorge on 06/01/14.
 */
class GrooscriptPluginExtension {
    List<String> source = ['src/main/groovy']
    String destination = 'src/main/webapp/js'
    List<String> classPath
    boolean convertDependencies = false
    Closure customization
}
