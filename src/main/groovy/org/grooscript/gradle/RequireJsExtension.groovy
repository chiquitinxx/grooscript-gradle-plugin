package org.grooscript.gradle

class RequireJsExtension {
    String sourceFile
    List<String> classPath = ['src/main/groovy']
    String destinationFolder
    Closure customization
    String initialText
    String finalText
    List<String> mainContextScope
}
