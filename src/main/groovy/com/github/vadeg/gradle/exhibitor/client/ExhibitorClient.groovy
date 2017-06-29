package com.github.vadeg.gradle.exhibitor.client

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import org.gradle.api.logging.Logging

import static EncodingUtils.toBinaryString

class ExhibitorClient {

    final logger = Logging.getLogger(ExhibitorClient.class)

    final String url

    final HTTPBuilder httpExecutor

    def validate = { it ->
        if (it.charAt(0) == '/') {
            throw new IllegalArgumentException("Path should not start with /")
        }
    }

    ExhibitorClient(String url) {
        this.url = url
        httpExecutor = new HTTPBuilder(url)

        httpExecutor.handler.failure = { resp ->
            logger.error("Failed to execute request: [$resp.statusLine]: $resp.data")
            Result.of(false, "[$resp.statusLine] -> $resp.data")
        }
    }

    def upsert(String path, Object data = null) {
        validate.call(path)
        return httpExecutor.request(Method.PUT, ContentType.JSON) { req ->
            uri.path = "/exhibitor/v1/explorer/znode/$path"

            body = toBinaryString(data)

            response.success = { resp, json ->
                logger.debug("$path -> [$resp.statusLine] -> $resp.data")
                Result.of(json.succeeded, json.message)
            }

            response.'415' = { resp ->
                logger.error("$path -> [$resp.statusLine] -> $resp.data")
                Result.of(false, resp.statusLine)
            }

        }
    }

    def delete(String path) {
        validate.call(path)
        return httpExecutor.request(Method.DELETE, ContentType.JSON) {
            uri.path = "/exhibitor/v1/explorer/znode/$path"
            response.success = { resp, json ->
                logger.debug("$path -> [$resp.statusLine] -> $resp.data")
                Result.of(json.succeeded, json.message)
            }
        }
    }

    def get(String path) {
        validate.call(path)
        return httpExecutor.request(Method.GET, ContentType.JSON) {
            uri.path = "/exhibitor/v1/explorer/node-data"
            uri.query = ['key': "/$path"]
            response.success = { resp, json ->
                logger.debug("$path -> [$resp.statusLine] -> $resp.data")
                json.str
            }
        }
    }

}
