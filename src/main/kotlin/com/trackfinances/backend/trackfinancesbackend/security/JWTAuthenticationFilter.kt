package com.trackfinances.backend.trackfinancesbackend.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import com.trackfinances.backend.trackfinancesbackend.model.Users
import com.trackfinances.backend.trackfinancesbackend.security.SecurityConstants.Companion.EXPIRATION_TIME
import com.trackfinances.backend.trackfinancesbackend.security.SecurityConstants.Companion.HEADER_STRING
import com.trackfinances.backend.trackfinancesbackend.security.SecurityConstants.Companion.SECRET
import com.trackfinances.backend.trackfinancesbackend.security.SecurityConstants.Companion.TOKEN_PREFIX
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter(private val authManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {

	@Throws(AuthenticationException::class)
	override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
		try {
			val creds: Users = ObjectMapper().readValue(request?.inputStream, Users::class.java)
			return authManager.authenticate(
					UsernamePasswordAuthenticationToken(
							creds.username,
							creds.password,
							ArrayList<GrantedAuthority>()
					)
			)
		} catch (e: IOException) {
			throw RuntimeException(e)
		}
	}

	@Throws(IOException::class, ServletException::class)
	override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?, authResult: Authentication?) {
		val token: String = JWT.create()
				.withSubject((authResult?.principal as User).username)
				.withExpiresAt(Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(SECRET))
		response?.addHeader(HEADER_STRING, TOKEN_PREFIX + token)

	}

}