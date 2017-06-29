package com.github.vadeg.gradle.exhibitor.client

import groovy.transform.CompileStatic
import org.codehaus.groovy.runtime.EncodingGroovyMethods

@CompileStatic
class EncodingUtils {

    static def fromBinaryString(String data) {
        new String(EncodingGroovyMethods.decodeHex(data))
    }

    static def toBinaryString(data) {
        if (data == null) {
            return null
        }
        String.valueOf(data).getBytes().encodeHex().toString()
    }

}
