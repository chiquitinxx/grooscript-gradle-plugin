package org.grooscript.gradle.template

import org.grooscript.asts.GsNotConvert
import org.grooscript.builder.HtmlBuilder

/**
 * Created by jorge on 04/10/14.
 */
class Templates {

    static Map templates

    static String applyTemplate(String name, model = [:]) {
        def cl = templates[name]
        cl.delegate = model
        cl(model)
    }
}
