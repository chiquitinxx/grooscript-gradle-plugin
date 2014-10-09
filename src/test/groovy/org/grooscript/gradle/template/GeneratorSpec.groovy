package org.grooscript.gradle.template

import spock.lang.Specification

/**
 * User: jorgefrancoleza
 * Date: 08/10/14
 */
class GeneratorSpec extends Specification {

    def 'generate first template'() {
        given:
        def templates = ['one.gtpl': "p 'Hello!'"]

        expect:
        generator.generateClassCode(templates) == '''package org.grooscript.gradle.template

@org.grooscript.gradle.asts.TemplateEnhancer
class Templates {

  static Map templates = [
    'one.gtpl': { model = [:] ->
      HtmlBuilder.build {
        p 'Hello!'
      }
    }
  ]

  static String applyTemplate(String name, model = [:]) {
    def cl = templates[name]
    cl.delegate = model
    cl(model)
  }
}'''
    }

    def 'generate two templates'() {
        given:
        def templates = ['one.gtpl': "p 'Hello!'", 'two.gtpl': "p 'Bye!'"]

        expect:
        generator.generateClassCode(templates) == '''package org.grooscript.gradle.template

@org.grooscript.gradle.asts.TemplateEnhancer
class Templates {

  static Map templates = [
    'one.gtpl': { model = [:] ->
      HtmlBuilder.build {
        p 'Hello!'
      }
    },
    'two.gtpl': { model = [:] ->
      HtmlBuilder.build {
        p 'Bye!'
      }
    }
  ]

  static String applyTemplate(String name, model = [:]) {
    def cl = templates[name]
    cl.delegate = model
    cl(model)
  }
}'''
    }

    Generator generator = new Generator()
}
