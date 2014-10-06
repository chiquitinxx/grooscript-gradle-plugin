package org.grooscript.gradle.template

import groovy.text.Template
import groovy.text.markup.MarkupTemplateEngine
import groovy.text.markup.TemplateConfiguration
import org.grooscript.builder.HtmlBuilder
import spock.lang.Specification

/**
 * Created by jorge on 04/10/14.
 */
class TemplatesSpec extends Specification {

    def 'convert template'() {
        expect:
        templates.applyTemplate('one') == '<p>Hello!</p>'
    }

    def 'convert template with model'() {
        expect:
        templates.applyTemplate('two', [list: [1, 2]]) == '<ul><li>1</li><li>2</li></ul>'
    }

    def 'convert template using other tamplate'() {
        expect:
        templates.applyTemplate('three', [list: [1, 1, 1]]) == '<ul><p>Hello!</p><p>Hello!</p><p>Hello!</p></ul>'
    }

    def 'converting a template'() {
        expect:
        convertTemplate('src/test/resources/one.gtpl') == '<p>Hello!</p>'
        convertTemplate('src/test/resources/three.gtpl', [list: [1, 2]]) == '<ul><p>Hello!</p><p>Hello!</p></ul>'
    }

    Templates templates = new Templates()

    def setup() {
        templates.templates = [
            one: { model = [:] ->
                HtmlBuilder.build {
                    p 'Hello!'
                }
            },
            two: { model = [:] ->
                HtmlBuilder.build {
                    ul {
                        model.list.each {
                            li it.toString()
                        }
                    }
                }
            },
            three: { model = [:] ->
                HtmlBuilder.build {
                    ul {
                        model.list.each {
                            yieldUnescaped Templates.applyTemplate('one', model)
                        }
                    }
                }
            }
        ]
    }

    def convertTemplate(pathToFile, model = null) {
        TemplateConfiguration config = new TemplateConfiguration()
        MarkupTemplateEngine engine = new MarkupTemplateEngine(config)
        Template template = engine.createTemplate(new File(pathToFile).text)
        template.make(model).toString()
    }
}
