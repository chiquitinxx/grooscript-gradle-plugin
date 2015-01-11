package org.grooscript.gradle

import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import org.grooscript.daemon.ConversionDaemon
import org.grooscript.daemon.FilesDaemon
import org.grooscript.util.GsConsole

/**
 * User: jorgefrancoleza
 * Date: 15/12/13
 */
class DaemonTask extends GrooscriptTask {

    boolean waitInfinite = true

    @TaskAction
    void launchDaemon() {
        checkProperties()
        if (!source || !destination) {
            throw new GradleException("Need define source and destination.")
        } else {
            configureAndStartDaemon()
        }
    }

    private configureAndStartDaemon() {
        FilesDaemon filesDaemon
        try {
            filesDaemon = ConversionDaemon.start(source.collect { project.file(it).path },
                    project.file(destination).path, conversionProperties)
            Thread.sleep(100)
            if (waitInfinite) {
                def thread = Thread.start {
                    while (filesDaemon.actor?.isActive()) {
                        sleep(100)
                    }
                }
                thread.join()
                filesDaemon.stop()
            }
        } catch (e) {
            GsConsole.error("Error in converion daemon: ${e.message}")
            filesDaemon.stop()
        }
    }
}
