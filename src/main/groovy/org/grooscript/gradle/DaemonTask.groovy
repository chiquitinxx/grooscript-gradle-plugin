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

    ConversionDaemon daemon
    boolean waitInfinite = true

    @TaskAction
    def launchDaemon() {
        checkProperties()
        if (!source || !destination) {
            throw new GradleException("Need define source and destination.")
        } else {
            configureAndStartDaemon()
            return daemon
        }
    }

    private configureAndStartDaemon() {
        try {
            def conversionOptions = [:]
            conversionOptions.classPath = classPath
            conversionOptions.customization = customization
            conversionOptions.convertDependencies = convertDependencies

            daemon = new ConversionDaemon()
            daemon.source = source
            daemon.destinationFolder = destination
            daemon.conversionOptions = conversionOptions
            daemon.doAfter = { listFiles ->
                listFiles.each {
                    GsConsole.info('File changed: '+it)
                }
            }
            startDaemon()
        } catch (e) {
            daemon.stop()
        }
    }

    private startDaemon() {
        daemon.start()
        Thread.sleep(100)
        if (waitInfinite) {
            def thread = Thread.start {
                while (daemon.convertActor?.isActive()) {
                    sleep(100)
                }
            }
            println 'Waiting...'
            thread.join()
            daemon.stop()
        }
    }
}
