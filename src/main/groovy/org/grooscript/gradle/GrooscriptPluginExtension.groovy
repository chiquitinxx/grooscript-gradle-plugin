package org.grooscript.gradle

/**
 * Created by jorge on 06/01/14.
 */
class GrooscriptPluginExtension {
    def source = ['src/main/groovy']
    String destination = 'src/main/webapp/js/app'
    List<String> classPath
    boolean convertDependencies = false
    Closure customization
    String initialText
    String finalText
    boolean recursive = false
    List<String> mainContextScope
}
