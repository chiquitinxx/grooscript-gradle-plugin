package org.grooscript.gradle.template

import org.grooscript.GrooScript
import org.grooscript.test.JsTestResult
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

    def 'convert a template with a model variable'() {
        given:
        def templates = ['hello.gtpl': "p 'Hello ' + model.name+ '!'"]
        def code = generator.generateClassCode(templates)
        code += "\nprintln Templates.applyTemplate('hello.gtpl', [name: 'Jorge'])\n"

        when:
        JsTestResult result = GrooScript.evaluateGroovyCode(code, 'grooscript-tools')

        then:
        !result.exception
        result.console == '<p>Hello Jorge!</p>'
    }

    def 'convert a template with a variable'() {
        given:
        def templates = ['hello.gtpl': "p 'Hello ' + name+ '!'"]
        def code = generator.generateClassCode(templates)
        code += "\nprintln Templates.applyTemplate('hello.gtpl', [name: 'Jorge'])\n"

        when:
        JsTestResult result = GrooScript.evaluateGroovyCode(code, 'grooscript-tools')

        then:
        !result.exception
        result.console == '<p>Hello Jorge!</p>'
    }

    def 'convert a template with an include'() {
        given:
        def templates = ['hello.gtpl': "p 'Hello ' + name+ '!'",
                         'initial.gtpl': "[1, 2].each { include template: 'hello.gtpl'}"]
        def code = generator.generateClassCode(templates)
        code += "\nprintln Templates.applyTemplate('initial.gtpl', [name: 'Jorge'])\n"

        when:
        JsTestResult result = GrooScript.evaluateGroovyCode(code, 'grooscript-tools')

        then:
        !result.exception
        result.console == '<p>Hello Jorge!</p><p>Hello Jorge!</p>'
    }

    def 'convert a template with a closure'() {
        given:
        def templates = ['hello.gtpl': "3.times { p 'Hi!' }; p 'Hello ' + name+ '!'"]
        def code = generator.generateClassCode(templates)
        code += "\nprintln Templates.applyTemplate('hello.gtpl', [name: 'Jorge'])\n"

        when:
        JsTestResult result = GrooScript.evaluateGroovyCode(code, 'grooscript-tools')

        then:
        !result.exception
        result.console == '<p>Hi!</p><p>Hi!</p><p>Hi!</p><p>Hello Jorge!</p>'
    }

    Generator generator = new Generator()
}
