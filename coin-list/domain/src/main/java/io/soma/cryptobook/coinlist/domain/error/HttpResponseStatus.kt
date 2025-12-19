package io.soma.cryptobook.coinlist.domain.error

enum class HttpResponseStatus(val code: Int, val msg: String) {
    Ok(200, "Ok"),
    Created(201, "Created"),
    Accepted(202, "Accepted"),
    NotAuthoritative(203, "Not Authoritative"),
    NoContent(204, "No Content"),
    Reset(205, "Reset"),
    Partial(206, "Partial"),
    MultiChoice(300, "Multi Choice"),
    MovedPermanently(301, "Moved Permanently"),
    MovedTemp(302, "Moved Temp"),
    SeeOther(303, "See Other"),
    NotModified(304, "Not Modified"),
    UseProxy(305, "Use Proxy"),
    BadRequest(400, "Bad Request"),
    Unauthorized(401, "Unauthorized"),
    PaymentRequired(402, "Payment Required"),
    Forbidden(403, "Forbidden"),
    NotFound(404, "Not Found"),
    BadMethod(405, "Bad Method"),
    NotAcceptable(406, "Not Acceptable"),
    ProxyAuth(407, "Proxy Auth"),
    ClientTimeout(408, "Client Timeout"),
    Conflict(409, "Conflict"),
    Gone(410, "Gone"),
    LengthRequired(411, "Length Required"),
    PreconditionFailed(412, "Precondition Failed"),
    EntityTooLarge(413, "Entity Too Large"),
    RequestTooLong(414, "Request Too Long"),
    UnsupportedType(415, "Unsupported Type"),
    RequestTooMany(429, "Request Too Many"),
    InternalError(500, "Internal Error"),
    NotImplemented(501, "Not Implemented"),
    BadGateway(502, "Bad Gateway"),
    Unavailable(503, "Unavailable"),
    GatewayTimeout(504, "Gateway Timeout"),
    Version(505, "Version"),
    UnknownError(520, "Unknown Error"),
    Unknown(-1, "Unknown"),
    ;

    companion object {
        fun create(code: Int): HttpResponseStatus {
            return HttpResponseStatus.entries.firstOrNull { it.code == code } ?: Unknown
        }
    }

    override fun toString(): String {
        return "$code $msg"
    }
}

