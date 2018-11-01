package com.trackfinances.backend.trackfinancesbackend.security

class SecurityConstants (

) {
    companion object {
        const val SECRET: String = "SecretKeyToGenJWTs"
        const val EXPIRATION_TIME: Long = 864_000_00
        const val TOKEN_PREFIX: String = "Bearer "
        const val HEADER_STRING: String = "Authorization"
        const val SIGN_UP_URL: String = "/users"
    }
}