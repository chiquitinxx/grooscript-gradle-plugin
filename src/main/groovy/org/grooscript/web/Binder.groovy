package org.grooscript.web

import org.grooscript.asts.GsNative

/**
 * User: jorgefrancoleza
 * Date: 06/02/14
 */
class Binder {
    @GsNative
    def bind(String selector, target, String nameProperty) { /*

        var sourceDom = $(selector);
        //Create set method
        var nameSetMethod = 'set'+nameProperty.capitalize();
        target[nameSetMethod] = function(newValue) {
            this[nameProperty] = newValue;
            sourceDom.val(newValue);
        };

        if (sourceDom.is(":text")) {
            sourceDom.bind('input', function() {
                var current = $(this);
                target[nameProperty] = current.val();
            });
        };

        if (sourceDom.is('textarea')) {
            sourceDom.bind('input propertychange', function() {
                var current = $(this);
                target[nameProperty] = current.val();
            });
        };
    */}
}
