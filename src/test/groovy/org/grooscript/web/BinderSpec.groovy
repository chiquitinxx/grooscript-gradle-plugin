package org.grooscript.web

import org.codehaus.groovy.runtime.MethodClosure
import org.grooscript.asts.PhantomJsTest
import spock.lang.Specification
import spock.lang.Unroll

/**
 * User: jorgefrancoleza
 * Date: 06/02/14
 */
class BinderSpec extends Specification {

    private static final SELECTOR = 'selector'

    Binder binder
    JQueryUtils jQueryUtils

    class Item {
        def name
        def group
        def buttonClick() {
            println 'Button clicked!'
        }
    }

    def setup() {
        jQueryUtils = Mock(JQueryUtils)
        binder = new Binder(jQueryUtils: jQueryUtils)
    }

    @Unroll
    def 'bind all properties'() {
        given:
        def item = new Item()

        when:
        binder.bindAllProperties(item, closure)

        then:
        1 * jQueryUtils.existsId('name') >> true
        1 * jQueryUtils.bind('#name', item, 'name', closure)
        1 * jQueryUtils.existsGroup('group') >> true
        1 * jQueryUtils.bind("input:radio[name=group]", item, 'group', closure)
        0 * jQueryUtils.bind(_)
        noExceptionThrown()

        where:
        closure << [null, { -> true}]
    }

    def 'bind all methods'() {
        given:
        def item = new Item()

        when:
        binder.bindAllMethods(item)

        then:
        1 * jQueryUtils.existsId('button') >> true
        1 * jQueryUtils.bindEvent('button', 'click', _ as MethodClosure)
        0 * jQueryUtils.bindEvent(_)
    }
}
