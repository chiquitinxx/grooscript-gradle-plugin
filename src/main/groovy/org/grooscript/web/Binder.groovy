package org.grooscript.web

/**
 * User: jorgefrancoleza
 * Date: 06/02/14
 */
class Binder {

    JQueryUtils jQueryUtils

    def bindAllProperties(target, closure = null) {
        target.properties.each { name, value ->
            if (jQueryUtils.existsId(name)) {
                jQueryUtils.bind("#$name", target, name, closure)
            }
            if (jQueryUtils.existsGroup(name)) {
                jQueryUtils.bind("input:radio[name=${name}]", target, name, closure)
            }
        }
    }

    def bindAllMethods(target) {
        target.metaClass.methods.each { method ->
            if (method.name.endsWith('Click')) {
                def shortName = method.name.substring(0, method.name.length() - 5)
                if (jQueryUtils.existsId(shortName)) {
                    jQueryUtils.bindEvent(shortName, 'click', target.&"${method.name}")
                }
            }
        }
    }
}
