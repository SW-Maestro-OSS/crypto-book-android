package io.soma.cryptobook.core.domain.error

class HttpResponseException(
    val status: HttpResponseStatus,
    val rawCode: Int,
    val errorRequestUrl: String,
    msgForLogging: String? = null,
    cause: Throwable? = null,
) : Exception(msgForLogging, cause)
