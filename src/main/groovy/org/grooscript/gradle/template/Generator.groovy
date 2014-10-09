package org.grooscript.gradle.template

import groovy.text.SimpleTemplateEngine

/**
 * User: jorgefrancoleza
 * Date: 08/10/14
 */
class Generator {

    String TEMPLATES_TEMPLATE = '''package org.grooscript.gradle.template

class Templates {

  static Map templates = $templates

  static String applyTemplate(String name, model = [:]) {
    def cl = templates[name]
    cl.delegate = model
    cl(model)
  }
}'''

    String generateClassCode(Map<String, String> templates) {
        def templatesFormat = templates.collect { entry ->
            [first: "'${entry.key}'",
             second: "{ model = [:] ->\n      HtmlBuilder.build {\n" +
                    "        ${entry.value}\n" +
                    "      }\n    }"]
        }.collect { strings ->
            '\n    ' + strings.first + ": " + strings.second
        }.join ','
        def engine = new SimpleTemplateEngine()
        engine.createTemplate(TEMPLATES_TEMPLATE).make([templates: '[' + templatesFormat + '\n  ]'])
    }
}
