package org.grooscript.gradle

import org.gradle.api.tasks.TaskAction
import org.grooscript.gradle.require.RequireJsActor

/**
 * Created by jorgefrancoleza on 12/5/15.
 */
class RequireJsThreadTask extends RequireJsAbstractTask {

    boolean blockExecution = false

    private static final WAIT_TIME = 400

    @TaskAction
    void startThread() {
        checkProperties()
        if (sourceFile && destinationFolder && classPath) {
            configureAndStartDaemon()
            if (blockExecution) {
                def thread = Thread.start {
                    while (true) {
                        sleep(WAIT_TIME)
                    }
                }
                thread.join()
            }
        } else {
            errorParameters()
        }
    }

    private void configureAndStartDaemon() {
        def action = this.&convertRequireJsFile
        action.setDelegate(this)
        action.resolveStrategy = Closure.DELEGATE_ONLY
        def actor = RequireJsActor.getInstance()
        actor.convertAction = action
        actor.start()
        actor << sourceFile
    }
}