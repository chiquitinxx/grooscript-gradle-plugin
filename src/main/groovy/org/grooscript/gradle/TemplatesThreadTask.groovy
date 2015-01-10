package org.grooscript.gradle

import org.gradle.api.tasks.TaskAction
import org.grooscript.daemon.FilesDaemon
import org.grooscript.util.GsConsole

/**
 * User: jorgefrancoleza
 * Date: 16/11/14
 */
class TemplatesThreadTask extends TemplatesAbstractTask {

    @TaskAction
    void launchThread() {
        checkProperties()
        if (templatesPath && templates && destinationFile) {
            configureAndStartThread()
        } else {
            errorParameters()
        }
    }

    private configureAndStartThread() {
        FilesDaemon filesDaemon
        try {
            def files = templates.collect {
                "${templatesPath}/${it}"
            }
            def action = { listFilesChanged ->
                if (listFilesChanged) {
                    try {
                        generateTemplate()
                    } catch(e) {
                        GsConsole.exception "Exception generating templates from thread: ${e.message}"
                    }
                }
            }
            filesDaemon = new FilesDaemon(files, action, [actionOnStartup: true])
            filesDaemon.start()
        } catch (e) {
            GsConsole.error("Error in templates thread: ${e.message}")
            filesDaemon?.stop()
        }
    }
}
