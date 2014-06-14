package org.grooscript.gradle

/**
 * User: jorgefrancoleza
 * Date: 13/06/14
 */
class ThreadTask extends DaemonTask {
    ThreadTask() {
        waitInfinite = false
    }
}
