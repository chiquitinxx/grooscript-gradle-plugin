package org.grooscript.web

import spock.lang.Specification

/**
 * User: jorgefrancoleza
 * Date: 06/02/14
 */
class BinderSpec extends Specification {

    private static final SELECTOR = 'selector'

    Binder binder = new Binder()

    class Item {
        def name
        def buttonClick() {
            println 'Button clicked!'
        }
    }

    def 'bind selector to object property'() {
        given:
        def item = new Item()

        when:
        binder.bind(SELECTOR, item, 'name')
        binder.bind(SELECTOR, item, 'name', { -> true})

        then:
        noExceptionThrown()
    }

    def 'bind all methods'() {
        given:
        def item = new Item()

        when:
        binder.bindAllMethods(item)

        then:
        noExceptionThrown()
    }
}
