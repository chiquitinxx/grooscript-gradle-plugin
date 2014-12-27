package org.grooscript.gradle

import org.grooscript.GrooScript

/**
 * User: jorgefrancoleza
 * Date: 25/09/14
 */
class TestConversions extends GroovyTestCase {

    void testConvertClassWithASTAtSemanticPhase() {
        def result = GrooScript.convert '''
    import org.grooscript.jquery.GQueryTrait

    class A implements GQueryTrait {}
'''
        assert result.contains('name: \'org.grooscript.jquery.GQueryTrait\'')
    }
}
