package org.grooscript.gradle

import org.grooscript.GrooScript

/**
 * User: jorgefrancoleza
 * Date: 25/09/14
 */
class TestConversions extends GroovyTestCase {

    void testConvertClassWithASTAtSemanticPhase() {
        def result = GrooScript.convert '''
    @org.grooscript.asts.GQuery
    class A {}
'''
        assert result.contains('gSobject.gQuery = GQueryImpl();')
    }
}
