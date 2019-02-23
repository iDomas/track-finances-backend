package com.trackfinances.backend.trackfinancesbackend.security

class SecurityConstants (

) {
    companion object {
        const val SECRET: String = "SecretKeyToGenJWTs"
        const val EXPIRATION_TIME: Long = 86_400_000
        const val TOKEN_PREFIX: String = "Bearer "
        const val HEADER_STRING: String = "Authorization"
        const val SIGN_UP_URL: String = "/users"
        const val SEARCH_BY_USERNAME_URL: String = "/users/search-by-username"
    }
}