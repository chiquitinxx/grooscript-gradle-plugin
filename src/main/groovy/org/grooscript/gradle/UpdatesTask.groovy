package org.grooscript.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import org.grooscript.daemon.FilesDaemon
import org.grooscript.gradle.files.UpdatesActions

/**
 * User: jorgefrancoleza
 * Date: 17/12/14
 */
class UpdatesTask extends DefaultTask {

    List<String> files
    Closure onChanges

    @TaskAction
    void checkUpdates() {
        checkProperties()
        if (!files || !onChanges) {
            throw new GradleException("Need define files an action on changes.")
        } else {
            checkingUpdates()
        }
    }

    private checkProperties() {
        files = files ?: project.extensions.modifications?.files
        onChanges = onChanges ?: project.extensions.modifications?.onChanges
    }

    private void checkingUpdates() {
        new FilesDaemon(files, { listFiles ->
            onChanges.delegate = new UpdatesActions()
            onChanges(listFiles)
        }).start()
    }
}
