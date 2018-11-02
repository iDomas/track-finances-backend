package com.trackfinances.backend.trackfinancesbackend.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.trackfinances.backend.trackfinancesbackend.security.SecurityConstants.Companion.HEADER_STRING
import com.trackfinances.backend.trackfinancesbackend.security.SecurityConstants.Companion.SECRET
import com.trackfinances.backend.trackfinancesbackend.security.SecurityConstants.Companion.TOKEN_PREFIX
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthorizationFilter(authManager: AuthenticationManager): BasicAuthenticationFilter(authManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header: String? = request.getHeader(HEADER_STRING)

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        val authentication: UsernamePasswordAuthenticationToken = getAuthentication(request)

        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken {
        val token: String? = request.getHeader(HEADER_STRING)
        if (token != null) {
            // parse the token
            val user: String? = JWT.require(Algorithm.HMAC512(SECRET))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .subject

            if (user != null) {
                return UsernamePasswordAuthenticationToken(user, null,  ArrayList<GrantedAuthority>())
            }

            return UsernamePasswordAuthenticationToken(null, null, null)
        }

        return UsernamePasswordAuthenticationToken(null, null, null)
    }
}