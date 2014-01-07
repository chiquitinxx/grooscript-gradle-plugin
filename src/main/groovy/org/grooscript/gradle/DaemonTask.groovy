package org.grooscript.gradle

import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import org.grooscript.daemon.ConversionDaemon
import org.grooscript.util.GsConsole

/**
 * User: jorgefrancoleza
 * Date: 15/12/13
 */
class DaemonTask extends GrooscriptTask {

    def daemon

    @TaskAction
    def launchDaemon() {
        checkProperties()
        if (!source || !destination) {
            throw new GradleException("Need define source and destination.")
        } else {
            startDaemon()
        }
    }

    private startDaemon() {
        try {
            def conversionOptions = [:]
            conversionOptions.classPath = classPath
            conversionOptions.customization = customization
            conversionOptions.convertDependencies = convertDependencies

            daemon = new ConversionDaemon()
            daemon.sourceList = source
            daemon.destinationFolder = destination
            daemon.conversionOptions = conversionOptions
            daemon.doAfter = { listFiles ->
                listFiles.each {
                    GsConsole.info('File changed: '+it)
                }
            }
            daemon.start()

            def thread = Thread.start {
                while (daemon) {
                    sleep(100)
                }
            }

            thread.run()
        } finally {
            daemon.stop()
        }
    }
}
