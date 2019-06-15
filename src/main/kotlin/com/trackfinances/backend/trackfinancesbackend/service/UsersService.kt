package com.trackfinances.backend.trackfinancesbackend.service

import com.trackfinances.backend.trackfinancesbackend.model.Users
import com.trackfinances.backend.trackfinancesbackend.repository.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsersService(
		@Autowired private val usersRepository: UsersRepository,
		@Autowired private val bCryptPasswordEncoder: BCryptPasswordEncoder) {

	fun getAllUsers(): List<Users> {
		return usersRepository.findAll().iterator().asSequence().toList()
	}

	fun insertUser(users: Users): Users {
		users.password = bCryptPasswordEncoder.encode(users.password)
		usersRepository.save(users)
		return users
	}

	fun getUserByUsername(username: String): Users {
		return usersRepository.findByUsername(username)
	}

}