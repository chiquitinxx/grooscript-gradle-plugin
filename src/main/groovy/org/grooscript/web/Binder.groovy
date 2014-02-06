package org.grooscript.web

import org.grooscript.asts.GsNative

/**
 * User: jorgefrancoleza
 * Date: 06/02/14
 */
class Binder {

    @GsNative
    def bind(String selector, target, String nameProperty) { /*
        $(selector).bind('input', function() {
            var current = $(this);
            if (current.is(":text")) {
                target['set'+nameProperty.capitalize()] = function(newValue) {
                    this[nameProperty] = newValue;
                    current.val(newValue);
                };
                target[nameProperty] = current.val();
            };
        });
    */}
}
