package org.grooscript.web

import org.grooscript.asts.GsNative

/**
 * User: jorgefrancoleza
 * Date: 06/02/14
 */
class Binder {
    @GsNative
    def bind(String selector, target, String nameProperty, closure) { /*

        var sourceDom = $(selector);
        //Create set method
        var nameSetMethod = 'set'+nameProperty.capitalize();

        if (sourceDom.is(":text")) {
            target[nameSetMethod] = function(newValue) {
                this[nameProperty] = newValue;
                sourceDom.val(newValue);
            };
            sourceDom.bind('input', function() {
                var current = $(this);
                target[nameProperty] = current.val();
            });
        } else if (sourceDom.is('textarea')) {
            target[nameSetMethod] = function(newValue) {
                this[nameProperty] = newValue;
                sourceDom.val(newValue);
            };
            sourceDom.bind('input propertychange', function() {
                var current = $(this);
                target[nameProperty] = current.val();
            });
        } else if (sourceDom.is(":checkbox")) {
            target[nameSetMethod] = function(newValue) {
                this[nameProperty] = newValue;
                sourceDom.prop('checked', newValue);
            };
            sourceDom.change(function() {
                var current = $(this);
                target[nameProperty] = current.is(':checked');
            });
        } else if (sourceDom.is(":radio")) {
            target[nameSetMethod] = function(newValue) {
                this[nameProperty] = newValue;
                $(selector +'[value="' + newValue + '"]').prop('checked', true);
            };
            sourceDom.change(function() {
                var current = $(this);
                target[nameProperty] = current.val();
            });
        } else {

        }
    */}
}
