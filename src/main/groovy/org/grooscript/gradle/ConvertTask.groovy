package org.grooscript.gradle

import org.gradle.api.GradleException
import org.gradle.api.Task
import org.gradle.api.tasks.TaskAction
import org.grooscript.GrooScript

/**
 * User: jorgefrancoleza
 * Date: 14/12/13
 */
class ConvertTask extends GrooscriptTask {

    @TaskAction
    void convert() {
        checkProperties()
        if (!source || !destination) {
            throw new GradleException("Need define source and destination.")
        } else {
            doConversion()
        }
    }

    private void doConversion() {
        GrooScript.clearAllOptions()
        GrooScript.setConversionProperty('customization', customization)
        GrooScript.setConversionProperty('classPath', classPath)
        GrooScript.setConversionProperty('convertDependencies', convertDependencies)
        GrooScript.setConversionProperty('initialText', initialText)
        GrooScript.setConversionProperty('finalText', finalText)
        GrooScript.setConversionProperty('recursive', recursive)
        GrooScript.setConversionProperty('mainContextScope', mainContextScope)
        GrooScript.convert(source, destination)
    }
}
