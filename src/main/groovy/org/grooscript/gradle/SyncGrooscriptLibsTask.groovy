package org.grooscript.gradle

import org.gradle.api.GradleException
import org.gradle.api.Task
import org.gradle.api.tasks.TaskAction
import org.grooscript.GrooScript

/**
 * User: jorgefrancoleza
 * Date: 14/12/13
 */
class SyncGrooscriptLibsTask extends GrooscriptTask {

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
        conversionProperties.each { key, value ->
            GrooScript.setConversionProperty(key, value)
        }
        GrooScript.convert(source.collect { project.file(it) }, project.file(destination))
    }
}
