package org.grooscript.gradle.files

import org.grooscript.gradle.websocket.Client

/**
 * Created by jorgefrancoleza on 18/12/14.
 */
class UpdatesActions {
    WebsocketAddress springWebsocketTo(String url) {
        new WebsocketAddress(url)
    }
}


class WebsocketAddress {
    def url

    WebsocketAddress(url) {
        this.url = url
    }

    WebsocketAddressData data(data) {
        new WebsocketAddressData(url, data)
    }
}

class WebsocketAddressData {
    def url, data

    WebsocketAddressData(url, data) {
        this.url = url
        this.data = data
    }

    def onChannel(String channel) {
        Client.connectAndSend(url, channel, data)
    }
}