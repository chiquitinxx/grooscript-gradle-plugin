package org.grooscript.gradle.files

import org.grooscript.gradle.websocket.Client
import spock.lang.Specification

/**
 * Created by jorgefrancoleza on 18/12/14.
 */
class UpdatesActionsSpec extends Specification {

    void 'test send via websocket'() {
        given:
        GroovySpy(Client, global: true)

        when:
        actions.websocketTo(url).data(data).via(channel)

        then:
        1 * Client.connectAndSend(url, channel, data) >> null
    }

    String url = 'ws://localhost'
    def data = 'any data'
    def channel = '/channel'
    UpdatesActions actions = new UpdatesActions()
}
