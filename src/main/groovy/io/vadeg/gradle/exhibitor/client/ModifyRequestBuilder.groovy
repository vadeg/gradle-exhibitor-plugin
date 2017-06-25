package io.vadeg.gradle.exhibitor.client

class ModifyRequestBuilder {

    private String path

    private String action

    private String data

    ModifyRequestBuilder path(String path) {
        this.path = path
        return this
    }

    String path() {
        return this.path
    }

    ModifyRequestBuilder action(String action) {
        this.action = action
        return this
    }

    String action() {
        return this.action
    }

    ModifyRequestBuilder data(String data) {
        this.data = data
        return this
    }

    String data() {
        return data
    }


}
