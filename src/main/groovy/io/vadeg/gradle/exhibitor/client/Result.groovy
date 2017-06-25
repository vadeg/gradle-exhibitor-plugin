package io.vadeg.gradle.exhibitor.client

class Result {

    final def succeed

    final def message

    static def of(succeed, message) {
        new Result(succeed, message)
    }

    Result(succeed, message) {
        this.succeed = succeed
        this.message = message
    }

    @Override
    public String toString() {
        return """\
Result{
    succeed=$succeed,
    message=$message
}"""
    }
}
