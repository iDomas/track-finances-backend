package com.trackfinances.backend.trackfinancesbackend.security

import com.trackfinances.backend.trackfinancesbackend.security.SecurityConstants.Companion.SIGN_UP_URL
import com.trackfinances.backend.trackfinancesbackend.service.UserDetailsServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
class WebSecurity(
        private val userDetailsServiceImpl: UserDetailsServiceImpl,
        private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity?) {
        http?.cors()?.and()?.csrf()?.disable()?.authorizeRequests()
                ?.antMatchers(HttpMethod.POST, SIGN_UP_URL)?.permitAll()
                ?.anyRequest()?.authenticated()
                ?.and()
                ?.addFilter(JWTAuthenticationFilter(authenticationManager()))
                ?.addFilter(JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                ?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userDetailsServiceImpl)?.passwordEncoder(bCryptPasswordEncoder)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val source: UrlBasedCorsConfigurationSource = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
        return source
    }

}