package org.grooscript.web

import org.grooscript.asts.PhantomJsTest

/**
 * Created by jorge on 15/02/14.
 */
class TestHtml extends GroovyTestCase {

    static final PHANTOMJS_HOME = '/Applications/phantomjs'

    void setUp() {
        System.setProperty('PHANTOMJS_HOME', PHANTOMJS_HOME)
    }

    @PhantomJsTest(url = 'src/test/resources/index.html', info = true, waitSeconds = 1)
    void testBinder() {
        assert $('title').text() == 'Trial', "Title is ${$('title').text()}"
        assert item.text == ''
        item.setText('Hello!')
        assert $('#text').val() == 'Hello!', "Text is ${$('#text').val()}"
        assert $('#car').is(':checked') == false, "1"
        assert item.car == false, "item.car isn't false"
        item.setCar(true)
        assert item.car == true, "item.car isn't true"
        //assert $('#car').is(':checked'), "Car isn't checked"
    }
}
