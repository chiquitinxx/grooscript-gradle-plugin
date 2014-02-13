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
}
