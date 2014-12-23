package org.grooscript.gradle

/**
 * Created by jorge on 12/09/14.
 */
class TemplatesExtension {
    String templatesPath = 'src/main/webapp/templates'
    List<String> templates
    String destinationFile = 'src/main/webapp/js/lib/Templates.js'
    List<String> classPath = ['src/main/groovy']
}
