package com.trackfinances.backend.trackfinancesbackend.service

import com.trackfinances.backend.trackfinancesbackend.model.Users
import com.trackfinances.backend.trackfinancesbackend.repository.UsersRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
        val usersRepository: UsersRepository
) : UserDetailsService {


    override fun loadUserByUsername(username: String): UserDetails {
        val user: Users = usersRepository.findByUsername(username)
        if (user == null) {
            throw UsernameNotFoundException(username)
        }
        return User(user.username, user.password, emptyList())
    }

}